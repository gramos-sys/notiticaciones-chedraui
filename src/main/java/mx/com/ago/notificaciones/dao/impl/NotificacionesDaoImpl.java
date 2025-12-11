package mx.com.ago.notificaciones.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.com.ago.notificaciones.dao.INotificacionesDao;
import mx.com.ago.notificaciones.data.DatosNotificacion;

@Repository
public class NotificacionesDaoImpl implements INotificacionesDao {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(NotificacionesDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<DatosNotificacion> consultarMovilUsuario() {
        String claseMetodo = "NotificacionesDaoImpl/consultarMovilUsuario: ";
        logger.info(claseMetodo + "inicio..."); 

        String sql = "[Notificacion].[not_spS_ObtenerUsuariosNotificacion]";
        List<DatosNotificacion> resultadosSQL = new ArrayList<>();


        logger.info("CallableStatement " + claseMetodo + sql);
        resultadosSQL = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(DatosNotificacion.class));

        return resultadosSQL;
    }

    @Override
    public Integer registrarNotificacion(DatosNotificacion datosNotificacion) {
        String claseMetodo = "NotificacionesDaoImpl/registrarNotificacion: ";
        logger.info(claseMetodo + "inicio..."); 

        String sql = "[Notificacion].[not_spI_MensajeNotificacion] ?, ?";

        Integer idNotificacion;

        logger.info("CallableStatement " + claseMetodo + sql + "' " + datosNotificacion.getIdUsuario() 
                                                             + ", " + datosNotificacion.getIdTienda());

        idNotificacion = jdbcTemplate.queryForObject(
            sql,
            Integer.class,
            datosNotificacion.getIdUsuario(),
            datosNotificacion.getIdTienda()
        );

        return idNotificacion;
    }

    @Override
    public void actualizarEstadoNotificacion(Integer idNotificacion, Boolean error, String observaciones) {
        
         String claseMetodo = "NotificacionesDaoImpl/actualizarEstadoNotificacion: ";
        logger.info(claseMetodo + "inicio..."); 

        String sql = "[Notificacion].[not_spU_ReportarNotificacion] ?, ?, ?";
        
        logger.info("CallableStatement " + claseMetodo + sql + " " + idNotificacion
                                                             + ", " + error  
                                                             + ", '" + observaciones + "'");

        jdbcTemplate.queryForObject(
            sql,
            Integer.class,
            idNotificacion,
            error,
            observaciones
        );

        return;
    }
}
