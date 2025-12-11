package mx.com.ago.notificaciones.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

import mx.com.ago.notificaciones.utils.FirebaseSDK;

@Service
public class NotificacionMasivaService {

    private static final Logger logger = Logger.getLogger(NotificacionMasivaService.class);

    //* SDK de Firebase
    @Autowired
    private FirebaseSDK firebaseSDK;

    //* Maximo reintentos por batch
    private static final int MAX_REINTENTOS = 3;
    //* Tamaño de cada lote de mensajes
    private static final int BATCH_SIZE = 400;

    //* Pool de hilos para envíos concurrentes (concurrencias)
    private final ExecutorService pool = Executors.newFixedThreadPool(10);


    public void enviarNotificacionesMasivas(List<Message> mensajes) {

        List<List<Message>> batches = partirEnLotes(mensajes, BATCH_SIZE);
        CountDownLatch latch = new CountDownLatch(batches.size());

        logger.info("Total batches: " + batches.size());

        for (List<Message> batch : batches) {
            pool.submit(new Runnable() {
                @Override
                public void run() {
                    enviarBatchConReintentos(batch);
                    latch.countDown();
                }
            });
        }
        esperarFinalizacion(latch);
        shutdown();
    }

    private void enviarBatchConReintentos(List<Message> batch) {
        for (int intento = 0; intento <= MAX_REINTENTOS; intento++) {
            try {
                ApiFuture<BatchResponse> future = firebaseSDK.enviarMensajesAsync(batch);
                BatchResponse response = future.get();

                logger.info("Batch enviado. Éxitos: " + response.getSuccessCount() +
                        ", Fallidos: " + response.getFailureCount());

                int index = 0;
                for (com.google.firebase.messaging.SendResponse r : response.getResponses()) {

                    if (!r.isSuccessful()) {
                        Exception e = r.getException();

                        if (e instanceof FirebaseMessagingException) {
                            FirebaseMessagingException fme = (FirebaseMessagingException) e;

                            logger.error(
                                    "Mensaje #" + index +
                                    " falló. Código: " + fme.getErrorCode() +
                                    ", Mensaje: " + fme.getMessage() +
                                    ", Detalles: " + fme.getMessagingErrorCode());
                        } else {
                            logger.error("Mensaje # " + index + " falló con excepción genérica", e);
                        }
                    } else {
                        logger.info("Mensaje #" + index + " enviado exitosamente.");
                    }
                    index++;
                }
                return; // Concluyó exitosamente
            } catch (Exception ex) {
                if (intento < MAX_REINTENTOS) {
                    logger.warn("Falló el batch completo. Reintentando...");
                    dormir(1000);
                } else {
                    logger.error("Batch falló definitivamente después de reintentos", ex);
                }
            }
        }
    }

    private void esperarFinalizacion(CountDownLatch latch) {
        try {
            logger.info("Esperando finalización de todos los lotes...");
            latch.await();
            logger.info("Todos los lotes finalizaron.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void shutdown() {
        try {
            logger.info("Cerrando pool de hilos...");
            pool.shutdown();
            if (!pool.awaitTermination(30, TimeUnit.SECONDS)) {
                logger.warn("Forzando cierre...");
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private List<List<Message>> partirEnLotes(List<Message> lista, int size) {
        List<List<Message>> batches = new ArrayList<>();
        for (int i = 0; i < lista.size(); i += size) {
            batches.add(lista.subList(i, Math.min(i + size, lista.size())));
        }
        return batches;
    }

    private void dormir(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {}
    }
}
