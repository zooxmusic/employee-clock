package com.paychex.clock.config;

import com.paychex.clock.enums.TimeEntryEvent;
import com.paychex.clock.enums.TimeEntryState;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Slf4j
@EnableStateMachine
@Configuration
public class StateMachineConfig extends StateMachineConfigurerAdapter<TimeEntryState, TimeEntryEvent> {

    @Override
    public void configure(StateMachineStateConfigurer<TimeEntryState, TimeEntryEvent> states) throws Exception {
        states.withStates()
                .initial(TimeEntryState.PUNCHED_OUT)
                .states(EnumSet.allOf(TimeEntryState.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<TimeEntryState, TimeEntryEvent> transitions) throws Exception {
        transitions
                .withExternal()
                .source(TimeEntryState.PUNCHED_OUT)
                .target(TimeEntryState.PUNCHED_IN)
                .event(TimeEntryEvent.PUNCH_IN)
                .and()
                .withExternal()
                .source(TimeEntryState.PUNCHED_IN)
                .target(TimeEntryState.START_BREAK)
                .event(TimeEntryEvent.START_BREAK)
                .and()
                .withExternal()
                .source(TimeEntryState.START_BREAK)
                .target(TimeEntryState.END_BREAK)
                .event(TimeEntryEvent.END_BREAK)
                .and()
                .withExternal()
                .source(TimeEntryState.PUNCHED_IN)
                .target(TimeEntryState.START_LUNCH)
                .event(TimeEntryEvent.START_LUNCH)
                .and()
                .withExternal()
                .source(TimeEntryState.START_LUNCH)
                .target(TimeEntryState.END_LUNCH)
                .event(TimeEntryEvent.END_LUNCH)
                .and()
                .withExternal()
                .source(TimeEntryState.PUNCHED_IN)
                .target(TimeEntryState.PUNCHED_OUT)
                .event(TimeEntryEvent.PUNCH_OUT);

    }
    @Override
    public void configure(StateMachineConfigurationConfigurer<TimeEntryState, TimeEntryEvent> config) throws Exception {
        config.withConfiguration()
                .autoStartup(true)
                .listener(new Listener());
    }
}
