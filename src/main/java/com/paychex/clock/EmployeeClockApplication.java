package com.paychex.clock;

import com.paychex.clock.enums.TimeEntryEvent;
import com.paychex.clock.enums.TimeEntryState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;

@EnableStateMachine
@SpringBootApplication
public class EmployeeClockApplication implements CommandLineRunner {

	private final StateMachine<TimeEntryState, TimeEntryEvent> stateMachine;

	@Autowired
	public EmployeeClockApplication(StateMachine<TimeEntryState, TimeEntryEvent> stateMachine) {
		this.stateMachine = stateMachine;
	}

	public static void main(String[] args) {
		SpringApplication.run(EmployeeClockApplication.class, args);
	}

	@Override
	public void run(String... args) {
		stateMachine.start();
		stateMachine.sendEvent(TimeEntryEvent.PUNCH_IN);
//        stateMachine.sendEvent(BlogEvents.PUBLISH_BLOG);
		stateMachine.stop();
	}
}
