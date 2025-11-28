package mx.com.ago.core.notificaciones;

import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import mx.com.ago.core.notificaciones.service.impl.NotificacionesService;

@SpringBootApplication
public class NotifacionesCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotifacionesCoreApplication.class, args);
			
	}

//    @Bean
//    CommandLineRunner start(MueblesService mueblesService) {
//		return args -> {Stream.of(mueblesService.clasificarMuebles());};
//	}	
	
	@Bean
    CommandLineRunner start(NotificacionesService whatsApp) {
		return args -> {Stream.of(whatsApp.generarNotificaciones());};
	}	
}
