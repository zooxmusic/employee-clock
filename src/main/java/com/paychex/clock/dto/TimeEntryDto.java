package com.paychex.clock.dto;

import com.paychex.clock.enums.TimeEntryStates;
import com.paychex.clock.model.TimeEntry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeEntryDto {
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Long id;
	private String date;
	private String state;
	private String description;
	private String location;
	private Long employeeId;
}
