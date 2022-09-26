package com.paychex.clock.service;


import com.paychex.clock.model.TimeEntry;
import com.paychex.clock.repository.TimeEntryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TimeEntryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TimeEntryService.class);

	private final TimeEntryRepository timeEntryRepository;

	@Autowired
	public TimeEntryService(final TimeEntryRepository timeEntryRepository) {
		this.timeEntryRepository = timeEntryRepository;
	}

	public List<TimeEntry> findByEmployeeId(final Long employeeId) {
		LOGGER.info("Finding time entries by employee id {}", employeeId);
		return this.timeEntryRepository.findByEmployeeId(employeeId);
	}

	@Cacheable("timeEntryById")
	public Optional<TimeEntry> find(final Long id) {
		LOGGER.info("Finding time entry by id {}", id);
		return this.timeEntryRepository.findById(id);
	}

	@CachePut("timeEntryById")
	public TimeEntry save(final TimeEntry timeEntry) {
		LOGGER.info("Persisting the time entry: {}", timeEntry);
		return this.timeEntryRepository.save(timeEntry);
	}

	public void remove(final Long id) {
		LOGGER.info("Removing the time entry id {}", id);
		this.timeEntryRepository.deleteById(id);
	}

}

