package com.paychex.clock.controller;

import java.util.List;
import java.util.Optional;

import com.paychex.clock.model.Employee;
import com.paychex.clock.model.TimeEntry;
import com.paychex.clock.service.TimeEntryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
		model.addAttribute("employee", employee);
		return "new-employee";
	}

	@GetMapping("/showTimeClock/{id}")
	public String showTimeClock(@PathVariable ( value = "id") long id, Model model) {
		LOGGER.info("showTimeClock...");
		Optional<Employee> employee = employeeService.find(id);

		LOGGER.info("Employee: ",  employee);

		List<TimeEntry> timeEntries = timeEntryService.findByEmployeeId(id);
		// set employee as a model attribute to pre-populate the form
		model.addAttribute("employee", employee.get());
		model.addAttribute("timeEntries", timeEntries);

		return "time-entry";
	}
	@PostMapping("/punchIn")
	public String punchIn(@ModelAttribute("employee") Employee employee) {
		// save employee to database

		System.out.println(employee);
		System.out.println("PUNCH IN");

		return "time-clock";
	}

	@PostMapping("/punchOut")
	public String punchOut(@ModelAttribute("employee") Employee employee) {
		// save employee to database

		System.out.println(employee);
		System.out.println("PUNCH OUT");

		return "time-clock";
	}

	@PostMapping("/takeBreak")
	public String takeBreak(@ModelAttribute("employee") Employee employee) {
		// save employee to database

		System.out.println(employee);
		System.out.println("TAKE BREAK");

		return "time-clock";
	}

	@PostMapping("/takeLunch")
	public String takeLunch(@ModelAttribute("employee") Employee employee) {
		// save employee to database

		System.out.println(employee);
		System.out.println("TAKE BREAK");

		return "time-clock";
	}


	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) {
		// save employee to database
		employeeService.save(employee);
		return "redirect:/";
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
		
		// get employee from the service
		Optional<Employee> employee = employeeService.find(id);
		
		// set employee as a model attribute to pre-populate the form
		model.addAttribute("employee", employee.get());
		return "update_employee";
	}
	
	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable (value = "id") long id) {
		
		// call delete employee method 
		this.employeeService.delete(id);
		return "redirect:/";
	}
	
//
//	@GetMapping("/page/{pageNo}")
//	public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
//			@RequestParam("sortField") String sortField,
//			@RequestParam("sortDir") String sortDir,
//			Model model) {
//		int pageSize = 5;
//
//		Page<Employee> page = employeeService.findPaginated(pageNo, pageSize, sortField, sortDir);
//		List<Employee> listEmployees = page.getContent();
//
//		model.addAttribute("currentPage", pageNo);
//		model.addAttribute("totalPages", page.getTotalPages());
//		model.addAttribute("totalItems", page.getTotalElements());
//
//		model.addAttribute("sortField", sortField);
//		model.addAttribute("sortDir", sortDir);
//		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
//
//		model.addAttribute("listEmployees", listEmployees);
//		return "index";
//	}
}
