package mx.com.ago.notificaciones.dao;

import mx.com.ago.notificaciones.data.TokenMovil;

public interface INotificacionesDao {

    public TokenMovil consultarMovilUsuario(String idUsuario);
} 