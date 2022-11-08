package ru.prerev.tinderclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("application.properties")
public class TinderClientApplication {
	public static void main(String[] args) {
		SpringApplication.run(TinderClientApplication.class, args);
	}
}
