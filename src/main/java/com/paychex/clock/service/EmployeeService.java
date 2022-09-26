package com.paychex.clock.service;

import java.util.List;
import java.util.Optional;

import com.paychex.clock.model.Employee;
import com.paychex.clock.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

	private final EmployeeRepository employeeRepository;

	@Autowired
	public EmployeeService(final EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}

	public void save(Employee employee) {
		this.employeeRepository.save(employee);
	}

	public Optional<Employee> find(long id) {
		return this.employeeRepository.findById(id);
	}

	public void delete(long id) {
		this.employeeRepository.deleteById(id);
	}

}
