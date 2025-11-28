package mx.com.ago.notificaciones.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class ReportePorCategoria {
	
	private String region;
	private String zona;
	private Integer idTienda;
	private String tienda;
	private Integer idCategoria;
	private String categoria;
	private String tipoCategoria;
	private String auditado;
	private Double faltante;
	private Double cumplimiento;
}
