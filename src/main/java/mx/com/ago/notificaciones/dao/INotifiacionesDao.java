package mx.com.ago.notificaciones.dao;

import mx.com.ago.notificaciones.data.TokenMovil;

public interface INotifiacionesDao {

    public TokenMovil consultarMovilUsuario(String idUsuario);
} 