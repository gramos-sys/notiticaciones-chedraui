package mx.com.ago.notificaciones.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.com.ago.notificaciones.dao.INotifiacionesDao;
import mx.com.ago.notificaciones.data.TokenMovil;

@Repository("notificacionesDao")
public class NotificacionesDaoImpl implements INotifiacionesDao {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(NotificacionesDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public TokenMovil consultarMovilUsuario(String idUsuario) {
        String claseMetodo = "NotificacionesDaoImpl/consultarMovilUsuario: ";
        logger.info(claseMetodo + "inicio..."); 

        String sql = "[Notificacion].[not_spS_ConsultaTokenMovil] ?";
        List<TokenMovil> resultadosSQL = new ArrayList<>();

        logger.info("CallableStatement " + claseMetodo + sql +" " + idUsuario);
        
        // Implementación de la lógica para consultar el móvil del usuario
        resultadosSQL = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(TokenMovil.class),new Object[]{idUsuario});

        return resultadosSQL.isEmpty() ? null : resultadosSQL.get(0); 
    }
}
