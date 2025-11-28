package mx.com.ago.notificaciones;

import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import mx.com.ago.notificaciones.service.impl.NotificacionesService;

@SpringBootApplication
public class NotifacionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotifacionesApplication.class, args);
			
	}

//    @Bean
//    CommandLineRunner start(MueblesService mueblesService) {
//		return args -> {Stream.of(mueblesService.clasificarMuebles());};
//	}	
	
	@Bean
    CommandLineRunner start(NotificacionesService notificacionesService) {
		return args -> {Stream.of(notificacionesService.generarNotificaciones());};
	}	
}
