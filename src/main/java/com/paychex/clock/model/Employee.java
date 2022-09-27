package com.paychex.clock.model;

import com.paychex.clock.enums.ProfileEnum;
import com.paychex.clock.enums.TimeEntryStates;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee")
public class Employee implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private ProfileEnum profile;

	@Enumerated(EnumType.STRING)
	private TimeEntryStates currentState;

	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Date createdAt;
	private Date updatedAt;

	@PreUpdate
	public void preUpdate() {
		updatedAt = new Date();
	}

	@PrePersist
	public void prePersist() {
		final Date date = new Date();
		currentState = TimeEntryStates.NOT_WORKING;
		createdAt = date;
		updatedAt = date;
	}

}