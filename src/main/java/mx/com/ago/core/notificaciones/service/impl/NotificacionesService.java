package mx.com.ago.core.notificaciones.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import mx.com.ago.core.notificaciones.service.INotificacionesService;

@Service
public class NotificacionesService implements INotificacionesService{
	
	private static final Logger logger = Logger.getLogger(NotificacionesService.class);
	//@Autowired private IWhatsAppService whatsapp;	
	
	@Override
	public Boolean generarNotificaciones() throws Exception {

		String claseMetodo = "Notificaciones/generarNotificaciones: ";
		logger.info(claseMetodo + "inicio...");

		System.out.println("Hola mundo...");

		
	    
	    return true;
	}
}
