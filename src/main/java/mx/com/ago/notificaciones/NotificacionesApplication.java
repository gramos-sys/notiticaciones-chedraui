package mx.com.ago.notificaciones;

import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import mx.com.ago.notificaciones.service.INotificacionesService;

@SpringBootApplication
public class NotificacionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificacionesApplication.class, args);
			
	}
	
	@Bean
    CommandLineRunner start(INotificacionesService notificacionesService) {
		return args -> {Stream.of(notificacionesService.generarNotificaciones());};
	}	
}
