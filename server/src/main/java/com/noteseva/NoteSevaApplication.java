package com.noteseva;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class NoteSevaApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoteSevaApplication.class, args);
	}

}
