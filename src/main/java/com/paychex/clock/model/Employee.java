package com.paychex.clock.model;

import com.paychex.clock.enums.ProfileEnum;
import com.paychex.clock.utils.PasswordUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee")
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private ProfileEnum profile;

	@OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<TimeEntry> timeEntries;

	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String cpf;
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