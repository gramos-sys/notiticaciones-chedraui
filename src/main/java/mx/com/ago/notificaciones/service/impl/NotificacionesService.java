package mx.com.ago.notificaciones.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import mx.com.ago.notificaciones.dao.INotificacionesDao;
import mx.com.ago.notificaciones.data.DatosNotificacion;
import mx.com.ago.notificaciones.service.INotificacionesService;

@Service
public class NotificacionesService implements INotificacionesService{
	
	private static final Logger logger = Logger.getLogger(NotificacionesService.class);

	@Autowired 
	private INotificacionesDao notificacionesDao;	

	@Autowired
	private NotificacionMasivaService notificacionMasivaService;
	
	@Override
	public Boolean generarNotificaciones() throws Exception {

		 String claseMetodo = "Notificaciones/generarNotificaciones: ";
		 logger.info(claseMetodo + "inicio...");

		List<DatosNotificacion> notifaciones = notificacionesDao.consultarMovilUsuario();
		List<Message> mensajes = new ArrayList<>();

		for (DatosNotificacion n : notifaciones) {

			n.setIdNotificacion(notificacionesDao.registrarNotificacion(n));

			String cuerpo =
				"🟢 ➕ " + n.getProductosNuevos() + " Productos nuevos\n" +
				"🔴 ➖ " + n.getPurgados() + " Productos purgados";

			mensajes.add(
				Message.builder()
					.setToken(n.getToken())
					.setNotification(
						Notification.builder()
							.setTitle("Resumen de productos tienda " + n.getTienda())
							.setBody(cuerpo)
							.build()
					)
					.putData("vista", "NAVIGATE_TO_CATALOGOS")
					.putData("idTienda", n.getIdTienda().toString())
					.putData("idNotificacion",n.getIdNotificacion().toString())
					.build()
			);
		}

		notificacionMasivaService.enviarNotificacionesMasivas(mensajes);
		
	    return true;
	}
}
