package com.ems.service;

import java.util.List;
import java.util.Optional;

import com.ems.vo.EmployeeVO;

public interface EmployeeService {

	EmployeeVO createEmployee(EmployeeVO employeeVO);
	List<EmployeeVO> getAllEmployees();
	Optional<EmployeeVO> getEmployeeById(Long id);
	
}
