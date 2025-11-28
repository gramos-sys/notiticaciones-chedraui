package mx.com.ago.core.notificaciones.service;

public interface IWhatsAppService {
	
	public Boolean enviarMensaje(String telefono,
								 String idUsuario,
								 String nombreUsuario, 
								 String reporte, 
								 Integer primerPorc,
								 Integer segundoPorc,
								 Integer terceroPorc,
								 Integer cuertoPorc) throws Exception;
}