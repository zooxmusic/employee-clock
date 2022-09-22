package com.paychex.clock.config;
import com.paychex.clock.enums.TimeEntryEvents;
import com.paychex.clock.enums.TimeEntryStates;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

public class Listener extends StateMachineListenerAdapter<TimeEntryStates, TimeEntryEvents> {
    @Override
    public void stateChanged(State<TimeEntryStates, TimeEntryEvents> from, State<TimeEntryStates, TimeEntryEvents> to) {
        System.out.println("state changed from " + from.getId()+ " to "+ to.getId());
    }
}