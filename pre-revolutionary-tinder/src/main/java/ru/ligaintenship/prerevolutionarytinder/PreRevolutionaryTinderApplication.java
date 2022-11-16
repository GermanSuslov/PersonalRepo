package ru.ligaintenship.prerevolutionarytinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PreRevolutionaryTinderApplication {

	public static void main(String[] args) {
		System.setProperty("server.port", String.valueOf(8090));
		SpringApplication.run(PreRevolutionaryTinderApplication.class, args);
	}
}
