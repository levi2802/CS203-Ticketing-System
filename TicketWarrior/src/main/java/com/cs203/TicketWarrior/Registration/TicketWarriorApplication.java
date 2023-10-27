package com.cs203.TicketWarrior.Registration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication (exclude = {DataSourceAutoConfiguration.class })
public class TicketWarriorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketWarriorApplication.class, args);
	}

}
