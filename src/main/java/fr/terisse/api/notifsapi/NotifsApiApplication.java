package fr.terisse.api.notifsapi;

import fr.terisse.api.notifsapi.utils.AudioUtils;
import fr.terisse.api.notifsapi.utils.LCDUtils;
import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Log
public class NotifsApiApplication {

	public static void main(String[] args) {

		SpringApplication.run(NotifsApiApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup()  {
		LCDUtils.test();
		AudioUtils.test();
	}

}
