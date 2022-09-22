package com.paychex.clock.model;


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
@Table(name = "time_entry")
public class TimeEntry implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date", nullable = false)
	private Date date;

	@Enumerated(EnumType.STRING)
	private TimeEntryStates state;

	@ManyToOne(fetch = FetchType.EAGER)
	private Employee employee;

	private String description;
	private String location;
	private Date createdAt;
	private Date updatedAt;

	@PreUpdate
	public void preUpdate() {
		updatedAt = new Date();
	}

	@PrePersist
	public void prePersist() {
		final Date date = new Date();
		createdAt = date;
		updatedAt = date;
	}

}
