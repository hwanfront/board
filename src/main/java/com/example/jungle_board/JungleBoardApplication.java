package com.example.jungle_board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("file:.env")
public class JungleBoardApplication {
	public static void main(String[] args) {
		SpringApplication.run(JungleBoardApplication.class, args);
	}
}
