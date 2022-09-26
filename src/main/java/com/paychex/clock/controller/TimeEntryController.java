package com.paychex.clock.controller;


import com.paychex.clock.dto.TimeEntryDto;
import com.paychex.clock.enums.TimeEntryStates;
import com.paychex.clock.model.Employee;
import com.paychex.clock.model.TimeEntry;
import com.paychex.clock.response.Response;
import com.paychex.clock.service.EmployeeService;
import com.paychex.clock.service.TimeEntryService;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/time-entries")
@CrossOrigin(origins = "*")
public class TimeEntryController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TimeEntryController.class);
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private final TimeEntryService timeEntryService;
	private final EmployeeService employeeService;

	@Autowired
	public TimeEntryController(final TimeEntryService timeEntryService, final EmployeeService employeeService) {
		this.timeEntryService = timeEntryService;
		this.employeeService = employeeService;
	}

	/**
	 * Returns the time entries of an employee.
	 * 
	 * @param employeeId
	 * @return ResponseEntity<Response<Page<TimeEntryDto>>>
	 */
	@GetMapping(value = "/employee/{employeeId}")
	public String listByEmployeeId(@PathVariable("employeeId") final Long employeeId, Model model) {
		LOGGER.info("Finding time entries by employee id: {}, page: {}", employeeId);

		final Optional<Employee> employee = this.employeeService.find(employeeId);
		final List<TimeEntry> timeEntries = this.timeEntryService.findByEmployeeId(employeeId);

		model.addAttribute("employee", employee.get());
		model.addAttribute("timeEntries", timeEntries);

		return "time-entries";
	}

	/**
	 * Returns a time entry by ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<TimeEntryDto>>
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<TimeEntryDto>> listById(@PathVariable("id") final Long id) {
		LOGGER.info("Finding time entry by ID: {}", id);
		final Response<TimeEntryDto> response = Response.create();
		final Optional<TimeEntry> timeEntry = this.timeEntryService.find(id);

		if (!timeEntry.isPresent()) {
			LOGGER.info("Time Entry not found by ID: {}", id);
			response.getErrors().add("Time Entry not found by ID " + id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(timeEntry.get().toDto());

		return ResponseEntity.ok(response);
	}

	/**
	 * Adding a new time entry.
	 * 
	 * @param timeEntryDto
	 * @param result
	 * @return ResponseEntity<Response<TimeEntryDto>>
	 * @throws ParseException
	 */
	@PostMapping
	public ResponseEntity<Response<TimeEntryDto>> add(@Valid @RequestBody final TimeEntryDto timeEntryDto,
			final BindingResult result) throws ParseException {
		LOGGER.info("Adding time entry: {}", timeEntryDto.toString());
		final Response<TimeEntryDto> response = Response.create();
		validateEmployee(timeEntryDto, result);
		TimeEntry timeEntry = this.convertDtoToTimeEntry(timeEntryDto, result);

		if (result.hasErrors()) {
			LOGGER.error("Error validating time entry: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		timeEntry = this.timeEntryService.save(timeEntry);
		response.setData(convertTimeEntryToDto(timeEntry));
		return ResponseEntity.ok(response);
	}

	/**
	 * Update time entry data.
	 * 
	 * @param id
	 * @param timeEntryDto
	 * @param result
	 * @return ResponseEntity<Response<TimeEntryDto>>
	 * @throws ParseException
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<TimeEntryDto>> update(@PathVariable("id") final Long id,
			@Valid @RequestBody final TimeEntryDto timeEntryDto, final BindingResult result) throws ParseException {
		LOGGER.info("Updating time entry: {}", timeEntryDto.toString());
		final Response<TimeEntryDto> response = Response.create();
		validateEmployee(timeEntryDto, result);
		timeEntryDto.setId(id);
		TimeEntry timeEntry = this.convertDtoToTimeEntry(timeEntryDto, result);

		if (result.hasErrors()) {
			LOGGER.error("Error validating time entry: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		timeEntry = this.timeEntryService.save(timeEntry);
		response.setData(convertTimeEntryToDto(timeEntry));
		return ResponseEntity.ok(response);
	}

	/**
	 * Removes a time entry by its ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<String>>
	 */
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<String>> remove(@PathVariable("id") final Long id) {
		LOGGER.info("Removing time entry: {}", id);
		final Response<String> response = Response.create();
		final Optional<TimeEntry> timeEntry = this.timeEntryService.find(id);

		if (!timeEntry.isPresent()) {
			LOGGER.info("Error removing time entry. Register not found for the id {}.", id);
			response.getErrors().add("Error removing time entry. Register not found for the id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.timeEntryService.remove(id);
		return ResponseEntity.ok(Response.create());
	}

	/**
	 * Validate a employee, verifying if it exists and if its valid.
	 * 
	 * @param timeEntryDto
	 * @param result
	 */
	private void validateEmployee(final TimeEntryDto timeEntryDto, final BindingResult result) {
		if (timeEntryDto.getEmployeeId() == null) {
			result.addError(new ObjectError("employee", "Employee not informed."));
			return;
		}

		LOGGER.info("Validating employee id {}: ", timeEntryDto.getEmployeeId());
		final Optional<Employee> employee = this.employeeService.find(timeEntryDto.getEmployeeId());
		if (!employee.isPresent()) {
			result.addError(new ObjectError("employee", "Employee not found. Nonexistent ID."));
		}
	}


	/**
	 * Converts a time entry into a DTO.
	 *
	 * @param timeEntry
	 * @return TimeEntryDto
	 */
	private TimeEntryDto convertTimeEntryToDto(final TimeEntry timeEntry) {
		return TimeEntryDto.builder()
				.id(timeEntry.getId())
				.date(this.dateFormat.format(timeEntry.getDate()))
				.state(timeEntry.getState().name())
				.description(timeEntry.getDescription())
				.location(timeEntry.getLocation())
				.employeeId(timeEntry.getEmployee().getId())
				.build();
	}

	/**
	 * Converts a time entry dto into a time entry.
	 *
	 * @param timeEntryDto
	 * @param result
	 * @return TimeEntry
	 * @throws ParseException
	 */
	private TimeEntry convertDtoToTimeEntry(final TimeEntryDto timeEntryDto, final BindingResult result)
			throws ParseException {
		TimeEntry timeEntry = new TimeEntry();

		if (null != timeEntryDto.getId()) {
			final Optional<TimeEntry> timeEntryFound = this.timeEntryService.find(timeEntryDto.getId());
			if (timeEntryFound.isPresent()) {
				timeEntry = timeEntryFound.get();
			} else {
				result.addError(new ObjectError("timeEntry", "Time Entry not found."));
			}
		} else {
			timeEntry.setEmployee(new Employee());
			timeEntry.getEmployee().setId(timeEntryDto.getEmployeeId());
		}

		timeEntry.setDescription(timeEntryDto.getDescription());
		timeEntry.setLocation(timeEntryDto.getLocation());
		timeEntry.setDate(this.dateFormat.parse(timeEntryDto.getDate()));

		if (EnumUtils.isValidEnum(TimeEntryStates.class, timeEntryDto.getState())) {
			timeEntry.setState(TimeEntryStates.valueOf(timeEntryDto.getState()));
		} else {
			result.addError(new ObjectError("state", "Invalid state."));
		}

		return timeEntry;
	}

}
