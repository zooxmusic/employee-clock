package com.paychex.clock;

import com.paychex.clock.enums.TimeEntryEvents;
import com.paychex.clock.enums.TimeEntryStates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;

@EnableStateMachine
@SpringBootApplication
public class EmployeeClockApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeClockApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(EmployeeClockApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void afterStartup() {

	}



}
