package mx.com.ago.notificaciones.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DatosNotificacion {
    
    private Integer idNotificacion;
    private String idUsuario;
    private Integer idTienda;
    private String tienda;
    private String fechaActualizacion;
    private String productosNuevos;
    private String productosResurtibles;
    private String productosNoResurtibles;
    private String purgados;
    private String fechaFormato;
    private String token;
    private String observaciones;
}
