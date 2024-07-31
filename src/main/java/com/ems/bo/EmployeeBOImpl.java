package com.ems.bo;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ems.entity.Employee;
import com.ems.repository.EmployeeRepository;
import com.ems.util.Constants;

@Component
public class EmployeeBOImpl implements EmployeeBO{
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeBOImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee createEmployee(Employee employee) {
        logger.info(Constants.CREATING_EMPLOYEE_LOG, employee);
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        logger.info(Constants.RETRIEVING_ALL_EMPLOYEES_LOG);
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        logger.info(Constants.RETRIEVING_EMPLOYEE_BY_ID_LOG, id);
        return employeeRepository.findById(id);
    }

}
