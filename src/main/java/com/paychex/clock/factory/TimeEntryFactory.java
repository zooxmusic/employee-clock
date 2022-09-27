package com.paychex.clock.factory;

import com.paychex.clock.dto.CurrentStateDto;
import com.paychex.clock.enums.TimeEntryStates;
import com.paychex.clock.model.Employee;
import com.paychex.clock.model.TimeEntry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimeEntryFactory {
//    public static CurrentStateDto toDto(final TimeEntry timeEntry) {
//        return CurrentStateDto.builder()
//                .id(timeEntry.getId())
//                .state(timeEntry.getState())
//                .date(timeEntry.getDate())
//                .description(timeEntry.getDescription())
//                .employeeId(timeEntry.getEmployee().getId())
//                .canPunchIn(timeEntry.canPunchIn())
//                .canTakeBreak(timeEntry.canTakeBreak())
//                .canTakeLunch(timeEntry.canTakeLunch())
//                .canPunchOut(timeEntry.canPunchOut())
//                .build();
//
//    }
//
//    public static List<CurrentStateDto> mapTimeEntriesList(List<TimeEntry> models) {
//        List<CurrentStateDto> result = new ArrayList<CurrentStateDto>();
//        for(TimeEntry model : models) {
//            result.add(mapTimeEntryDto(model));
//        }
//        return result;
//    }
//    public static CurrentStateDto mapTimeEntryDto(final TimeEntry model) {
//        return CurrentStateDto.builder()
//                .id(model.getId())
//                .date(model.getDate())
//                .employeeId(model.getEmployee().getId())
//                .state(model.getState())
//                .canPunchIn(model.canPunchIn())
//                .canTakeBreak(model.canTakeBreak())
//                .canTakeLunch(model.canTakeLunch())
//                .canPunchOut(model.canPunchOut())
//                .description(model.getDescription())
//                .build();
//    }
//
//    public static TimeEntry toModel(final CurrentStateDto currentStateDto, final Employee employee) {
//        return TimeEntry.builder()
//                .id(currentStateDto.getId())
//                .state(currentStateDto.getState())
//                .date(currentStateDto.getDate())
//                .description(currentStateDto.getDescription())
//                .employee(employee)
//                .build();
//
//    }
//    public static TimeEntry createFromEmployee(final Employee employee) {
//        return TimeEntry.builder()
//                .employee(employee)
//                .state(TimeEntryStates.NOT_WORKING)
//                .date(new Date())
//                .description(TimeEntryStates.NOT_WORKING.name())
//                .build();
//    }
}
