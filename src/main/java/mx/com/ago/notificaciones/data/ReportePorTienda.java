package mx.com.ago.notificaciones.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class ReportePorTienda {

	private String region;
	private String zona;
	private Integer idTienda;
	private String tienda;
	private Double porcAvance;
}
