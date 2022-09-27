package com.paychex.clock.dto;

import com.paychex.clock.enums.TimeEntryStates;
import com.paychex.clock.model.Employee;
import com.paychex.clock.model.TimeEntry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrentStateDto {
	private TimeEntryStates state;
	private Date date;
	private Employee employee;

	private boolean canPunchIn;
	private boolean canTakeBreak;
	private boolean canTakeLunch;
	private boolean canPunchOut;

	public void setState(final TimeEntryStates state) {
		this.state = state;
		this.setStates();
	}
	private void setStates() {
		this.canPunchIn = this.canPunchIn();
		this.canTakeBreak = this.canTakeBreak();
		this.canTakeLunch = this.canTakeLunch();
		this.canPunchOut = this.canPunchOut();
	}

	public boolean canPunchIn() {
		return isNotWorking() ||
				isOnBreak() ||
				isOnLunch();
	}
	public boolean canTakeBreak() {
		return isWorking();
	}
	public boolean canTakeLunch() {
		return isWorking();
	}
	public boolean canPunchOut() {
		return isWorking();
	}

	private boolean isNotWorking() {
		return state.equals(TimeEntryStates.NOT_WORKING);
	}
	private boolean isWorking() {
		return state.equals(TimeEntryStates.WORKING);
	}
	private boolean isOnBreak() {
		return state.equals(TimeEntryStates.ON_BREAK);
	}
	private boolean isOnLunch() {
		return state.equals(TimeEntryStates.ON_LUNCH);
	}

	public static CurrentStateDto create(final Employee employee) {
		CurrentStateDto dto = CurrentStateDto.builder()
				.employee(employee)
				.date(new Date())
				.build();
		dto.setState(employee.getCurrentState());
		return dto;

	}

}
