package fr.terisse.api.notifsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NotifsApiApplication {

	public static void main(String[] args) {

		SpringApplication.run(NotifsApiApplication.class, args);
	}

}
