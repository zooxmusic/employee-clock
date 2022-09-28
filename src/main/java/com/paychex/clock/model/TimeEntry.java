package com.paychex.clock.model;


import com.paychex.clock.dto.CurrentStateDto;
import com.paychex.clock.enums.TimeEntryStates;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "time_entry")
public class TimeEntry implements Serializable {
	//private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private TimeEntryStates state;

	@ManyToOne(fetch = FetchType.EAGER)
	private Employee employee;

	private Date date;

	private Date createdAt;
	private Date updatedAt;

	public static TimeEntry fromDto(CurrentStateDto dto) {
		return TimeEntry.builder()
				.date(dto.getDate())
				.state(dto.getState())
				.employee(dto.getEmployee())
				.build();
	}
	@PreUpdate
	public void preUpdate() {
		updatedAt = new Date();
	}

	@PrePersist
	public void prePersist() {
		final Date date = new Date();
		this.createdAt = date;
		this.updatedAt = date;
	}

}
