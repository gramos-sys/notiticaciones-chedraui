package mx.com.ago.core.notificaciones.data;

import lombok.Data;
import lombok.ToString;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ToString
public class Usuarios {
	private String idUsuario;
	private String numTelefono;
	private String nombreUsuario;
}
