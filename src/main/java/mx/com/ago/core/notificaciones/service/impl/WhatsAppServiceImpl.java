package mx.com.ago.core.notificaciones.service.impl;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import mx.com.ago.core.notificaciones.dao.impl.ReportesDAOImpl;
import mx.com.ago.core.notificaciones.service.IWhatsAppService;

@Service
public class WhatsAppServiceImpl implements IWhatsAppService  {
	
	private static final Logger logger = Logger.getLogger(IWhatsAppService.class);
	
	@Autowired
	private Environment env;
	
	@Autowired
	private ReportesDAOImpl reportesDAO;
	
	@Override
	public Boolean enviarMensaje(String telefono,
								 String idUsuario,
								 String nombreUsuario, 
								 String reporte, 
								 Integer primerPorc,
								 Integer segundoPorc,
								 Integer terceroPorc,
								 Integer cuartoPorc) throws Exception {
		
		String claseMetodo = "WhatsAppAPI/enviarMensaje";
		logger.info("Llegando a " + claseMetodo + "............");
		
		String message;
		
		String token = env.getProperty("ws.token");
		String idNumero = env.getProperty("ws.idtelefono");
		URL url = new URL("https://graph.facebook.com/" + env.getProperty("ws.version") + "/" + idNumero + "/messages");
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			
		String urlReporte = env.getProperty("ws.url") + idUsuario + "/" + Paths.get(reporte).getFileName().toString();
		String nombreReporte =  Paths.get(reporte).getFileName().toString();
		
		String json;
		
		httpCon.setRequestMethod("POST");
		httpCon.setRequestProperty("Authorization", "Bearer " + token);
		httpCon.setRequestProperty("Content-Type","application/json; application/xwww-form-urlencoded; charset=UTF-8");
		httpCon.setDoOutput(true);

		OutputStreamWriter write = new OutputStreamWriter(httpCon.getOutputStream());
		
		json = "{ " +
				"\"messaging_product\": \"whatsapp\", " + 
				"\"to\": \"52" + telefono + "\", " +
				"\"type\": \"template\", " +
				"\"template\": " +
				"{ \"name\": \"rpt_avance_auditoria\", " + 
				"  \"language\": { \"code\": \"es_MX\" }," +
				"\"components\":[\r\n"
				+ "         {\r\n"
				+ "            \"type\":\"header\",\r\n"
				+ "            \"parameters\":[\r\n"
				+ "               {\r\n"
				+ "                  \"type\":\"document\",\r\n"
				+ "                  \"document\":{\r\n"
				+ "                     \"link\":\"" + urlReporte + "\",\r\n"
				+ "                     \"filename\":\"" + nombreReporte + "\"\r\n"
				+ "                  }\r\n"
				+ "               }\r\n"
				+ "            ]\r\n"
				+ "         },\r\n"
				+ "         {\r\n"
				+ "            \"type\":\"body\",\r\n"
				+ "\"parameters\": [\r\n"
				+ "          {\r\n"
				+ "            \"type\": \"text\",\r\n"
				+ "            \"text\": \"" + nombreUsuario + "\"\r\n"
				+ "          },\r\n"
				+ "          {\r\n"
				+ "            \"type\": \"text\",\r\n"
				+ "            \"text\": \"" + primerPorc + "\"\r\n"
				+ "          },"
				+ "          {\r\n"
				+ "            \"type\": \"text\",\r\n"
				+ "            \"text\": \"" + segundoPorc + "\"\r\n"
				+ "          },"
				+ "          {\r\n"
				+ "            \"type\": \"text\",\r\n"
				+ "            \"text\": \"" + terceroPorc + "\"\r\n"
				+ "          },"
				+ "          {\r\n"
				+ "            \"type\": \"text\",\r\n"
				+ "            \"text\": \"" + cuartoPorc + "\"\r\n"
				+ "          }"
				+ "        ]"
				+ "         }\r\n"
				+ "      ]" +
				"} " + 
				" }";
		
		write.write(json);
		write.flush();
		write.close();
		httpCon.getOutputStream().close();

		InputStream responseStream = httpCon.getResponseCode() / 100 == 2
				? httpCon.getInputStream() : httpCon.getErrorStream();
		
		try (Scanner s = new Scanner(responseStream).useDelimiter("\\A")) {
			String respuesta = s.hasNext() ? s.next() : "";
			 
			if(respuesta.contains("error")) {
				ObjectMapper jsonRespuesta = new ObjectMapper();
			    JsonNode rootNode = jsonRespuesta.readTree(respuesta);
			
			    // Extraer valores del JSON
			    message = rootNode.path("error").path("message").asText();
			    String type = rootNode.path("error").path("type").asText();
			    int code = rootNode.path("error").path("code").asInt();
//			    int subcode = rootNode.path("error").path("error_subcode").asInt();
//			    String fbtraceId = rootNode.path("error").path("fbtrace_id").asText();
			    String mensajeError = rootNode.path("error").path("error_data").path("details").asText();
			
			    // Imprimir resultados
			    logger.error(claseMetodo + "............ Error al enviar WhatsApp");
			    logger.error("Mensaje: " + message);
			    logger.error("Tipo: " + type);
			    logger.error("C�digo: " + code);
//			    logger.error("Subc�digo: " + subcode);
//			    logger.error("fbtrace_id: " + fbtraceId);
			    logger.error("mensajeError: " + mensajeError);
			} else {
				message = "Mensaje enviado";
				logger.info(claseMetodo + "............Se envio correctamente WhatsApp: " + telefono);
			}
			reportesDAO.registrarBitacoraWS(idUsuario, telefono, "Reporte de Auditorias", message, false);
		}
		
		return true;
	}	
}
