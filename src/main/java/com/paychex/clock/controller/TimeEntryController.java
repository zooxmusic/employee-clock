package com.paychex.clock.controller;


import com.paychex.clock.dto.CurrentStateDto;
import com.paychex.clock.factory.TimeEntryFactory;
import com.paychex.clock.model.Employee;
import com.paychex.clock.model.TimeEntry;
import com.paychex.clock.response.Response;
import com.paychex.clock.service.EmployeeService;
import com.paychex.clock.service.TimeEntryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/time-clock")
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

		if(employee.isPresent()) {
			model.addAttribute("timeEntries", timeEntries);
		} else {
			model.addAttribute("errors", "Employee not found");
		}

		return "time-clock";
	}

	/**
	 * Returns a time entry by ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<TimeEntryDto>>
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<CurrentStateDto>> listById(@PathVariable("id") final Long id) {
		LOGGER.info("Finding time entry by ID: {}", id);
		final Response<CurrentStateDto> response = Response.create();
		final Optional<TimeEntry> timeEntry = this.timeEntryService.find(id);

		if (!timeEntry.isPresent()) {
			LOGGER.info("Time Entry not found by ID: {}", id);
			response.getErrors().add("Time Entry not found by ID " + id);
			return ResponseEntity.badRequest().body(response);
		}

		//response.setData(TimeEntryFactory.toDto(timeEntry.get()));

		return ResponseEntity.ok(response);
	}

	/**
	 * Adding a new time entry.
	 * 
	 * @param currentStateDto
	 * @param result
	 * @return ResponseEntity<Response<TimeEntryDto>>
	 * @throws ParseException
	 */
	@PostMapping
	public ResponseEntity<Response<CurrentStateDto>> add(@Valid @RequestBody final CurrentStateDto currentStateDto,
														 final BindingResult result) throws ParseException {
		LOGGER.info("Adding time entry: {}", currentStateDto.toString());
		final Response<CurrentStateDto> response = Response.create();
		validateEmployee(currentStateDto, result);
		TimeEntry timeEntry = this.convertDtoToTimeEntry(currentStateDto, result);

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
	 * @param currentStateDto
	 * @param result
	 * @return ResponseEntity<Response<TimeEntryDto>>
	 * @throws ParseException
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<CurrentStateDto>> update(@PathVariable("id") final Long id,
															@Valid @RequestBody final CurrentStateDto currentStateDto, final BindingResult result) throws ParseException {
		LOGGER.info("Updating time entry: {}", currentStateDto.toString());
		final Response<CurrentStateDto> response = Response.create();
		validateEmployee(currentStateDto, result);

		TimeEntry timeEntry = this.convertDtoToTimeEntry(currentStateDto, result);

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
	 * @param currentStateDto
	 * @param result
	 */
	private void validateEmployee(final CurrentStateDto currentStateDto, final BindingResult result) {
//		if (currentStateDto.getEmployeeId() == null) {
//			result.addError(new ObjectError("employee", "Employee not informed."));
//			return;
//		}

//		LOGGER.info("Validating employee id {}: ", currentStateDto.getEmployeeId());
//		final Optional<Employee> employee = this.employeeService.find(currentStateDto.getEmployeeId());
//		if (!employee.isPresent()) {
//			result.addError(new ObjectError("employee", "Employee not found. Nonexistent ID."));
//		}
	}


	/**
	 * Converts a time entry into a DTO.
	 *
	 * @param timeEntry
	 * @return TimeEntryDto
	 */
	private CurrentStateDto convertTimeEntryToDto(final TimeEntry timeEntry) {
		return CurrentStateDto.builder()
				.date(timeEntry.getDate())
				.state(timeEntry.getState())
				.build();
	}

	/**
	 * Converts a time entry dto into a time entry.
	 *
	 * @param currentStateDto
	 * @param result
	 * @return TimeEntry
	 * @throws ParseException
	 */
	private TimeEntry convertDtoToTimeEntry(final CurrentStateDto currentStateDto, final BindingResult result)
			throws ParseException {
		TimeEntry timeEntry = new TimeEntry();

//		if (null != currentStateDto.getId()) {
//			final Optional<TimeEntry> timeEntryFound = this.timeEntryService.find(currentStateDto.getId());
//			if (timeEntryFound.isPresent()) {
//				timeEntry = timeEntryFound.get();
//			} else {
//				result.addError(new ObjectError("timeEntry", "Time Entry not found."));
//			}
//		} else {
//			timeEntry.setEmployee(new Employee());
//			timeEntry.getEmployee().setId(currentStateDto.getEmployeeId());
//		}
//
//		timeEntry.setDescription(currentStateDto.getDescription());
		timeEntry.setDate(currentStateDto.getDate());
		timeEntry.setState(currentStateDto.getState());

		return timeEntry;
	}

}
