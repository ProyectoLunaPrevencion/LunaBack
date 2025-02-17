package Luna.API;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Aplicacion {

	public static void main(String[] args) {
		SpringApplication.run(Aplicacion.class, args);
		System.out.println("API is running...");
	}

}
