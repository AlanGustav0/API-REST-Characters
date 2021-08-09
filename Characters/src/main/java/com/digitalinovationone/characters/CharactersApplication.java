package com.digitalinovationone.characters;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.digitalinovationone.characters.mapper.CharacterMapper"})
public class CharactersApplication {

	public static void main(String[] args) {
		SpringApplication.run(CharactersApplication.class, args);

		System.out.println("API Rodandando!");
	}

}
