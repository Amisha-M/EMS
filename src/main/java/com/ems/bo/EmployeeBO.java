package com.ems.bo;

import java.util.List;
import java.util.Optional;

import com.ems.entity.Employee;

public interface EmployeeBO {
	
	Employee createEmployee(Employee employee);
    List<Employee> getAllEmployees();
    Optional<Employee> getEmployeeById(Long id);

}
