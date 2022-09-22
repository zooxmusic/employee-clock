package com.paychex.clock.config;
import com.paychex.clock.enums.TimeEntryEvent;
import com.paychex.clock.enums.TimeEntryState;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

public class Listener extends StateMachineListenerAdapter<TimeEntryState, TimeEntryEvent> {
    @Override
    public void stateChanged(State<TimeEntryState, TimeEntryEvent> from,     State<TimeEntryState, TimeEntryEvent> to) {
        System.out.println("state changed from " + from.getId()+ " to "+ to.getId());
    }
}