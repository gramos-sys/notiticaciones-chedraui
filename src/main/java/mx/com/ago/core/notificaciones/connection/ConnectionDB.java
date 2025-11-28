package mx.com.ago.core.notificaciones.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ConnectionDB {
	
	private static Logger logger = Logger.getLogger(ConnectionDB.class);

	@Autowired
    private Environment env;
	
	public Connection getConexionHist() {

		String conexionUrl = env.getProperty("datasource.url") + ";encrypt=true;trustServerCertificate=true;" +
							";username=" + env.getProperty("datasource.username") +
							";password=" + env.getProperty("datasource.password");
		try {
			logger.info("ConnectionDB.getConexionHist(): INICIANDO CONEXION");
			Connection con = DriverManager.getConnection(conexionUrl);
			logger.info("ConnectionDB.getConexionHist(): EXITO EN LA CONEXION");
			return con;
		} catch (SQLException ex) {
			logger.error("ConnectionDB.getConexionHist(): ERROR EN OBTENER LA CONEXICION :" + ex.getMessage());
			return null;
		} finally {
			logger.info("ConnectionDB.getConexionHist(): TERMINO CONEXION");
		}
	}
}
