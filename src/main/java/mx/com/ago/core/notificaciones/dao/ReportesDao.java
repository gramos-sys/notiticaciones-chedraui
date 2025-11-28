package mx.com.ago.core.notificaciones.dao;

import java.util.List;

import mx.com.ago.core.notificaciones.data.ReportePorCategoria;
import mx.com.ago.core.notificaciones.data.ReportePorTienda;
import mx.com.ago.core.notificaciones.data.ReporteTotales;
import mx.com.ago.core.notificaciones.data.Usuarios;

public interface ReportesDao {	
	
	public String obtenerParametros(String pathReporte) throws Exception;
	public List<Usuarios> obtenerUsuarioNotificaciones() throws Exception;
	public List<ReporteTotales> reporteTotales(String idUsuario) throws Exception;
	public List<ReportePorTienda> reporteTiendas(String idUsuario) throws Exception;
	public List<ReportePorCategoria> reporteCategorias(String idUsuario) throws Exception;
	public void registrarBitacoraWS(String idUsuario, 
									String telefono, 
									String tipoReporte, 
									String motivo,
									Boolean enviado) throws Exception;
}
