package com.paychex.clock.config;

import com.paychex.clock.enums.TimeEntryEvents;
import com.paychex.clock.enums.TimeEntryStates;
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
public class StateMachineConfig extends StateMachineConfigurerAdapter<TimeEntryStates, TimeEntryEvents> {

    @Override
    public void configure(StateMachineStateConfigurer<TimeEntryStates, TimeEntryEvents> states) throws Exception {
        states.withStates()
                .initial(TimeEntryStates.NOT_WORKING)
                .states(EnumSet.allOf(TimeEntryStates.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<TimeEntryStates, TimeEntryEvents> transitions) throws Exception {
        transitions
                .withExternal()
                .source(TimeEntryStates.NOT_WORKING)
                .target(TimeEntryStates.WORKING)
                .event(TimeEntryEvents.PUNCH_IN)
                .and()
                .withExternal()
                .source(TimeEntryStates.WORKING)
                .target(TimeEntryStates.ON_BREAK)
                .event(TimeEntryEvents.TAKE_BREAK)
                .and()
                .withExternal()
                .source(TimeEntryStates.ON_BREAK)
                .target(TimeEntryStates.WORKING)
                .event(TimeEntryEvents.PUNCH_IN)
                .and()
                .withExternal()
                .source(TimeEntryStates.WORKING)
                .target(TimeEntryStates.ON_LUNCH)
                .event(TimeEntryEvents.TAKE_LUNCH)
                .and()
                .withExternal()
                .source(TimeEntryStates.ON_LUNCH)
                .target(TimeEntryStates.WORKING)
                .event(TimeEntryEvents.PUNCH_IN)
                .and()
                .withExternal()
                .source(TimeEntryStates.WORKING)
                .target(TimeEntryStates.NOT_WORKING)
                .event(TimeEntryEvents.PUNCH_OUT);

    }
//    @Override
//    public void configure(StateMachineConfigurationConfigurer<TimeEntryStates, TimeEntryEvents> config) throws Exception {
//        config.withConfiguration()
//                .autoStartup(true)
//                .listener(new Listener());
//    }
}
