package com.paychex.clock.controller;

import java.util.List;
import java.util.Optional;

import com.paychex.clock.dto.CurrentStateDto;
import com.paychex.clock.enums.TimeEntryStates;
import com.paychex.clock.model.Employee;
import com.paychex.clock.model.TimeEntry;
import com.paychex.clock.service.TimeEntryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.paychex.clock.service.EmployeeService;

@Controller
public class EmployeeController {
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

	private final BCryptPasswordEncoder passwordEncoder;
	private final EmployeeService employeeService;
	private final TimeEntryService timeEntryService;

	@Autowired
	public EmployeeController(final BCryptPasswordEncoder passwordEncoder, final EmployeeService employeeService, final TimeEntryService timeEntryService) {
		this.passwordEncoder = passwordEncoder;
		this.employeeService = employeeService;
		this.timeEntryService = timeEntryService;
	}

	// display list of employees
	@GetMapping("/")
	public String viewHomePage(Model model) {
		LOGGER.info("PASSWORD");
		LOGGER.info(passwordEncoder.encode("admin"));
		final List<Employee> employees = employeeService.findAll();
		model.addAttribute("listEmployees", employees);
		return "index";
	}
	
	@GetMapping("/showNewEmployeeForm")
	public String showNewEmployeeForm(Model model) {
		// create model attribute to bind form data
		Employee employee = new Employee();
		employee.setCurrentState(TimeEntryStates.NOT_WORKING);
		model.addAttribute("employee", employee);
		return "new-employee";
	}

	@GetMapping("/showTimeEntries/{id}")
	public String showTimeEntries(@PathVariable ( value = "id") long id, Model model) {
		LOGGER.info("showTimeClock...");

		final List<TimeEntry> timeEntries = this.timeEntryService.findByEmployeeId(id);
		model.addAttribute("timeEntries", timeEntries);

		return "time-entries";
	}

	@GetMapping("/showTimeClock/{id}")
	public String showTimeClock(@PathVariable ( value = "id") long id, Model model) {
		LOGGER.info("showTimeClock...");

		final Optional<Employee> optionalEmployee = this.employeeService.find(id);

		if(optionalEmployee.isPresent()) {
			Employee employee = optionalEmployee.get();
			CurrentStateDto currentStateDto = CurrentStateDto.create(employee);
			final List<TimeEntry> timeEntries = this.timeEntryService.findByEmployeeId(employee.getId());


			this.timeEntryService.save(TimeEntry.fromDto(currentStateDto));

			model.addAttribute("employee", employee);
			model.addAttribute("currentState", currentStateDto);
			model.addAttribute("timeEntries", timeEntries);
		} else {
			model.addAttribute("errors", "Employee not found");
		}

		return "time-clock";
	}

	@GetMapping("/punchIn/{id}")
	public String punchIn(@PathVariable ( value = "id") long id, Model model) throws Exception {
		LOGGER.info("PUNCH IN");
		employeeService.punchIn(id);
		return "redirect:/showTimeClock/" + id;
	}

	@GetMapping("/punchOut/{id}")
	public String punchOut(@PathVariable ( value = "id") long id, Model model) throws Exception {
		LOGGER.info("PUNCH OUT");
		employeeService.punchOut(id);
		return "redirect:/showTimeClock/" + id;
	}
	@GetMapping("/takeBreak/{id}")
	public String takeBreak(@PathVariable ( value = "id") long id, Model model) throws Exception {
		LOGGER.info("TAKE BREAK");
		employeeService.takeBreak(id);
		return "redirect:/showTimeClock/" + id;
	}

	@GetMapping("/takeLunch/{id}")
	public String takeLunch(@PathVariable ( value = "id") long id, Model model) throws Exception {
		LOGGER.info("TAKE LUNCH");
		employeeService.takeLunch(id);
		return "redirect:/showTimeClock/" + id;
	}

	@PostMapping("/save")
	public String save(@ModelAttribute("employee") Employee employee) {
		employeeService.save(employee);
		return "redirect:/";
	}
	
	@GetMapping("/update/{id}")
	public String update(@PathVariable ( value = "id") long id, Model model) {
		
		Optional<Employee> employee = employeeService.find(id);
		
		model.addAttribute("employee", employee.get());
		return "update-employee";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable (value = "id") long id) {

		this.employeeService.delete(id);
		return "redirect:/";
	}
}
