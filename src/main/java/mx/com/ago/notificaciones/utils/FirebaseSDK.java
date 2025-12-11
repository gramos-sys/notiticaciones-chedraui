package mx.com.ago.notificaciones.utils;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;

@Component
public class FirebaseSDK {
    
    private static final Logger logger = Logger.getLogger(FirebaseSDK.class);

    @Value("${firebase.credentials.path}")
    private Resource firebaseResource;

    @PostConstruct
    public void init() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {
            GoogleCredentials credentials = GoogleCredentials.fromStream(firebaseResource.getInputStream());
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .build();

            FirebaseApp.initializeApp(options);
            logger.info("Firebase inicializado correctamente.");
        }
    }

    public ApiFuture<BatchResponse> enviarMensajesAsync(List<Message> mensajes) {
        return FirebaseMessaging.getInstance().sendEachAsync(mensajes);
    }
}
