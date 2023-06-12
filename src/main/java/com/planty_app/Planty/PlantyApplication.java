package com.planty_app.Planty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//при необходимости переименовывайте классы
//я их создал для сохранения папок, как структуры
//в файле application.yml я указал порт 8081, потому
//что 8080 у меня занят, локально вы можете его поменять, если хотите,

@SpringBootApplication
public class PlantyApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlantyApplication.class, args);
	}

}
