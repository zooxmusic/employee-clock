package com.paychex.clock.config;

import com.paychex.clock.enums.TimeEntryEvent;
import com.paychex.clock.enums.TimeEntryState;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

import java.util.UUID;

@SpringBootTest
public class StateMachineConfigTest {

    @Autowired
    StateMachineFactory<TimeEntryState, TimeEntryEvent> factory;

    @Test
    void testNewStateMachine() {
        StateMachine<TimeEntryState, TimeEntryEvent> sm = factory.getStateMachine(UUID.randomUUID());

        sm.start();

        System.out.println(sm.getState().toString());
        sm.sendEvent(TimeEntryEvent.PUNCH_IN);

        System.out.println(sm.getState().toString());
        sm.sendEvent(TimeEntryEvent.START_BREAK);

        System.out.println(sm.getState().toString());
        sm.sendEvent(TimeEntryEvent.START_LUNCH);

        System.out.println(sm.getState().toString());
        sm.sendEvent(TimeEntryEvent.PUNCH_OUT);
    }
}
