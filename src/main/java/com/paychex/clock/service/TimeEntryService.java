package com.paychex.clock.service;

import com.paychex.clock.model.TimeEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface TimeEntryService {

	/**
	 * Returns a paged list of time entries for a given employee.
	 * 
	 * @param employeeId
	 * @param pageRequest
	 * @return Page<TimeEntry>
	 */
	Page<TimeEntry> findByEmployeeId(final Long employeeId, final PageRequest pageRequest);

	/**
	 * Returns the time entry by id.
	 * 
	 * @param id
	 * @return Optional<TimeEntry>
	 */
	Optional<TimeEntry> findById(final Long id);

	/**
	 * Persists a time entry in the database.
	 * 
	 * @param timeEntry
	 * @return TimeEntry
	 */
	TimeEntry persist(final TimeEntry timeEntry);

	/**
	 * Remove a time entry from the database.
	 * 
	 * @param id
	 */
	void remove(final Long id);

}
