package mx.com.ago.notificaciones.dao;

import java.util.List;

import mx.com.ago.notificaciones.data.DatosNotificacion;

public interface INotificacionesDao {

    public List<DatosNotificacion> consultarMovilUsuario();

    public Integer registrarNotificacion(DatosNotificacion datosNotificacion);

    public void actualizarEstadoNotificacion(Integer idNotificacion, Boolean error, String observaciones);
} 