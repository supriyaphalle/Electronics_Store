package com.bikkadIt.electronic.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Override
	public void run(String... args) throws Exception {
	System.out.println("Encoded password is " +passwordEncoder.encode("supriya"));
	}
}
