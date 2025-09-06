package com.wilker.agendador_tarefas_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AgendadorTarefasApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgendadorTarefasApiApplication.class, args);
	}

}
