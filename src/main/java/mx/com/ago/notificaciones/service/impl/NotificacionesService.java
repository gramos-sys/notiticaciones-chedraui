package mx.com.ago.notificaciones.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.ago.notificaciones.dao.INotifiacionesDao;
import mx.com.ago.notificaciones.data.TokenMovil;
import mx.com.ago.notificaciones.service.INotificacionesService;

@Service
public class NotificacionesService implements INotificacionesService{
	
	private static final Logger logger = Logger.getLogger(NotificacionesService.class);

	@Autowired 
	private INotifiacionesDao notificacionesDao;	
	
	@Override
	public Boolean generarNotificaciones() throws Exception {

		String claseMetodo = "Notificaciones/generarNotificaciones: ";
		logger.info(claseMetodo + "inicio...");

		System.out.println("Hola mundo...");

		TokenMovil token = notificacionesDao.consultarMovilUsuario("gramos");
		System.out.println("Token: " + token.toString());

		
	    
	    return true;
	}
}
