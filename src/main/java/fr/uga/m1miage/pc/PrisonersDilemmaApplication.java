package fr.uga.m1miage.pc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"fr.uga.m1miage.pc"})
public class PrisonersDilemmaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrisonersDilemmaApplication.class, args);
	}

}
