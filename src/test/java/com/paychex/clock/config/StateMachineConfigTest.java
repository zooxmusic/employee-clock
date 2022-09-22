package com.paychex.clock.config;

import com.paychex.clock.enums.TimeEntryEvents;
import com.paychex.clock.enums.TimeEntryStates;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

import java.util.UUID;

@SpringBootTest
public class StateMachineConfigTest {

    @Autowired
    StateMachineFactory<TimeEntryStates, TimeEntryEvents> factory;

    @Test
    void testNewStateMachine() {
        StateMachine<TimeEntryStates, TimeEntryEvents> sm = factory.getStateMachine(UUID.randomUUID());

        sm.start();

        System.out.println(sm.getState().toString());
        sm.sendEvent(TimeEntryEvents.PUNCH_IN);

        System.out.println(sm.getState().toString());
        sm.sendEvent(TimeEntryEvents.TAKE_BREAK);

        System.out.println(sm.getState().toString());
        sm.sendEvent(TimeEntryEvents.TAKE_LUNCH);

        System.out.println(sm.getState().toString());
        sm.sendEvent(TimeEntryEvents.PUNCH_OUT);
    }
}
