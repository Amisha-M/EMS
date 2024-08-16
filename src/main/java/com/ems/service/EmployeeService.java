package com.ems.service;

import java.util.List;
import java.util.Optional;

import com.ems.bo.EmployeeBO;

public interface EmployeeService {

	EmployeeBO createEmployee(EmployeeBO employeeBO);
	List<EmployeeBO> getAllEmployees();
	Optional<EmployeeBO> getEmployeeById(Long id);
	
}
