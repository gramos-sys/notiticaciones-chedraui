package mx.com.ago.core.notificaciones.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import mx.com.ago.core.notificaciones.connection.ConnectionDB;
import mx.com.ago.core.notificaciones.dao.ReportesDao;
import mx.com.ago.core.notificaciones.data.ReportePorCategoria;
import mx.com.ago.core.notificaciones.data.ReportePorTienda;
import mx.com.ago.core.notificaciones.data.ReporteTotales;
import mx.com.ago.core.notificaciones.data.Usuarios;

@Repository("mueblesDao")
public class ReportesDAOImpl implements ReportesDao {
	
	private static Logger logger = Logger.getLogger(ReportesDAOImpl.class);
	
	@Autowired
	private ConnectionDB conecction;
	
//	@Autowired
//    private Environment env;

	@Override
	public String obtenerParametros(String parametro) throws Exception {
		
		String claseMetodo = "RIServiceImpl/obtenerUsuarioNotificaciones";
		logger.info("Llegando a " + claseMetodo + "............");

		String valorParametro = "";
		String sql = "EXEC [Reconocimiento].[spSObtieneParametrosWS] ?";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;

		try {
			logger.info("Ejecutando consulta: " + sql + " ");

			conn = conecction.getConexionHist();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1,parametro);
			rs = stmt.executeQuery();

			if (rs.next()) {
				
				valorParametro = rs.getString("valor");
			}

			if (!valorParametro.isEmpty()) {
				logger.info(claseMetodo + " Se obtivo el valor: " + valorParametro);
			} else {
				logger.warn("No se encontraron usuarios.");
				throw new Exception("No se encontraron resultados.");
			}
		} catch (SQLException e) {
			logger.error("ERROR en " + claseMetodo + ": " + e.getMessage(), e);
			throw new Exception("Error al consultar el servicio: ", e);
		} finally {
			if (conn != null)
				conn.close();
			if (stmt != null)
				stmt.close();
			if (rs != null)
				rs.close();
		}

		return valorParametro;
	}

	@Override
	public List<Usuarios> obtenerUsuarioNotificaciones() throws Exception {
		
		String claseMetodo = "RIServiceImpl/obtenerUsuarioNotificaciones";
		logger.info("Llegando a " + claseMetodo + "............");

		List<Usuarios> usuarios = new ArrayList<Usuarios>();
		String sql = "EXEC [cronograma].[sps_UsuariosNotificaciones] ";
		// "EXEC [cronograma].[sps_UsuariosNotificaciones] ?";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;

		try {
			logger.info("Ejecutando consulta: " + sql + " ");

			conn = conecction.getConexionHist();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();

			while (rs.next()) {

				Usuarios usuario = new Usuarios();
				usuario.setIdUsuario(rs.getString("USUARIO_ID").trim());
				usuario.setNumTelefono(rs.getString("numTelefono").trim());
				usuario.setNombreUsuario(rs.getString("nombreUsuario").trim());
				
				usuarios.add(usuario);
			}

			if (!usuarios.isEmpty()) {
				logger.info(claseMetodo + " Se encontraron [" + usuarios.size() + "] usuarios");
			} else {
				logger.warn("No se encontraron usuarios.");
				throw new Exception("No se encontraron resultados.");
			}
		} catch (SQLException e) {
			logger.error("ERROR en " + claseMetodo + ": " + e.getMessage(), e);
			throw new Exception("Error al consultar el servicio: ", e);
		} finally {
			if (conn != null)
				conn.close();
			if (stmt != null)
				stmt.close();
			if (rs != null)
				rs.close();
		}

		return usuarios;
	}
	
	@Override
	public List<ReporteTotales> reporteTotales(String idUsuario) throws Exception {
		String claseMetodo = "RIServiceImpl/reporteTotales";
		logger.info("Llegando a " + claseMetodo + "............");

		List<ReporteTotales> totales = new ArrayList<>();
		String sql = "EXEC [cronograma].[spsAvanceTotalTdasPorUsuario] ?";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;

		try {
			logger.info("Ejecutando consulta: " + sql + " " + idUsuario);
			
			conn = conecction.getConexionHist();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, idUsuario);
			rs = stmt.executeQuery();

			while (rs.next()) {

				ReporteTotales total = new ReporteTotales();
				total.setTiendas(rs.getInt("Tiendas"));
				total.setPorcentaje(rs.getString("Pocentaje"));
				total.setTotal(rs.getInt("totalTiendas"));

				totales.add(total);
			}

			if (totales.isEmpty()) {
				logger.warn("No se encontraron resultados.");
			}
		} catch (SQLException e) {
			logger.error("ERROR en " + claseMetodo + ": ",e);
		} finally {
			if (conn != null)
				conn.close();
			if (stmt != null)
				stmt.close();
			if (rs != null)
				rs.close();
		}

		return totales;
	}

	@Override
	public List<ReportePorTienda> reporteTiendas(String idUsuario) throws Exception {
		String claseMetodo = "RIServiceImpl/reporteTiendas";
		logger.info("Llegando a " + claseMetodo + "............");

		List<ReportePorTienda> tiendas = new ArrayList<>();
		String sql = "EXEC [cronograma].[spsAvanceTiendasUsuario] ?";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;

		try {
			
			

			logger.info("Ejecutando consulta: " + sql + " " + idUsuario);

			conn = conecction.getConexionHist();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, idUsuario);
			rs = stmt.executeQuery();

			while (rs.next()) {

				ReportePorTienda tienda = new ReportePorTienda();
				tienda.setRegion(rs.getString("region"));
				tienda.setZona(rs.getString("zona"));
				tienda.setIdTienda(rs.getInt("idTienda"));
				tienda.setTienda(rs.getString("tienda"));
				tienda.setPorcAvance(rs.getDouble("porcAcance"));

				tiendas.add(tienda);
			}

			if (tiendas.isEmpty()) {
				logger.warn("No se encontraron resultados.");
			}
		} catch (SQLException e) {
			logger.error("ERROR en " + claseMetodo + ": ",e);
		} finally {
			if (conn != null)
				conn.close();
			if (stmt != null)
				stmt.close();
			if (rs != null)
				rs.close();
		}

		return tiendas;
	}

	@Override
	public List<ReportePorCategoria> reporteCategorias(String idUsuario) throws Exception {
		String claseMetodo = "RIServiceImpl/reporteCategorias";
		logger.info("Llegando a " + claseMetodo + "............");

		List<ReportePorCategoria> categorias = new ArrayList<>();
		String sql = "EXEC [cronograma].[spsAvanceTiendasCategoriasUsuario] ?";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;

		try {
			logger.info("Ejecutando consulta: " + sql + " " + idUsuario);
			conn = conecction.getConexionHist();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, idUsuario);

			rs = stmt.executeQuery();

			while (rs.next()) {

				ReportePorCategoria categoria = new ReportePorCategoria();
				categoria.setRegion(rs.getString("region"));
				categoria.setZona(rs.getString("zona"));
				categoria.setIdTienda(rs.getInt("idTienda"));
				categoria.setTienda(rs.getString("tienda"));
				categoria.setIdCategoria(rs.getInt("idCategoriaPln"));
				categoria.setCategoria(rs.getString("fcCategoria"));
				categoria.setTipoCategoria(rs.getString("tipoCat"));
				categoria.setAuditado(rs.getString("auditado"));
				categoria.setFaltante(rs.getDouble("faltante"));
				categoria.setCumplimiento(rs.getDouble("cumplimiento"));
				;
				categorias.add(categoria);
			}

			if (categorias.isEmpty()) {
				logger.warn("No se encontraron resultados.");
			}
		} catch (SQLException e) {
			logger.error("ERROR en " + claseMetodo + ": ",e);
		} finally {
			if (conn != null)
				conn.close();
			if (stmt != null)
				stmt.close();
			if (rs != null)
				rs.close();
		}

		return categorias;
	}

	@Override
	public void registrarBitacoraWS(String idUsuario, 
									String telefono, 
									String tipoReporte, 
									String motivo,
									Boolean enviado) throws Exception {

		String claseMetodo = "RIServiceImpl/registrarBitacoraWS";
		logger.info("Llegando a " + claseMetodo + "............");

		Boolean registrado = false;
		;
		String sql = "EXEC [Reconocimiento].[spIRegistrarBitacoraWS] ?,?,?,?,?";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;

		try {
			logger.info("Ejecutando consulta: " + sql + " " + idUsuario + ", "
					+ telefono + ", "
					+ tipoReporte + ","
					+ motivo + ", "
					+ enviado);
			conn = conecction.getConexionHist();
			stmt = conn.prepareStatement(sql);

			stmt.setString(1, idUsuario);
			stmt.setString(2, telefono);
			stmt.setString(3, tipoReporte);
			stmt.setString(4, motivo);
			stmt.setBoolean(5, enviado);

			rs = stmt.executeQuery();

			while (rs.next()) {
				registrado = rs.getBoolean("registrado");
			}

			if (registrado) {
				logger.info(claseMetodo + "............Se registro en la Bitacora WS");

			} else {
				logger.warn(claseMetodo + "............No se registro en la Bitacora WS");
			}
		} catch (SQLException e) {
			logger.error("ERROR en " + claseMetodo + ": ",e);
		} finally {
			if (conn != null)
				conn.close();
			if (stmt != null)
				stmt.close();
			if (rs != null)
				rs.close();
		}

		return;
		
	}
}
