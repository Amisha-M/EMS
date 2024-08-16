package com.ems.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ems.util.JaxbUtil;
import jakarta.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ems.EmployeeMapper;
import com.ems.bo.EmployeeBO;
import com.ems.entity.Employee;
import com.ems.repository.EmployeeRepository; // Assuming you have a repository
import com.ems.util.Constants;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository; // Use repository for CRUD
    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public EmployeeBO createEmployee(EmployeeBO employeeBO) {
        logger.info(Constants.CREATING_EMPLOYEE_LOG, employeeBO);
        Employee employee = employeeMapper.employeeBOToEmployee(employeeBO);
        Employee createdEmployee = employeeRepository.save(employee); // Use repository to save
        EmployeeBO createdEmployeeBO = employeeMapper.employeeToEmployeeBO(createdEmployee);

        try {
            // Marshalling(write) to JSON
            String json = JaxbUtil.toJson(createdEmployeeBO);
            logger.info("Created Employee JSON: {}", json);

            // Unmarshalling(read) from JSON
            EmployeeBO unmarshalledEmployeeBO = JaxbUtil.fromJson(json, EmployeeBO.class);
            logger.info("Unmarshalled EmployeeBO: {}", unmarshalledEmployeeBO);

        } catch (JAXBException e) {
            logger.error("Error in JSON Conversion", e);
        }

        return createdEmployeeBO;
    }

    @Override
    public List<EmployeeBO> getAllEmployees() {
        logger.info(Constants.RETRIEVING_ALL_EMPLOYEES_LOG);
        List<Employee> employees = employeeRepository.findAll(); // Use repository to get all employees
        return employees.stream()
                .map(employeeMapper::employeeToEmployeeBO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<EmployeeBO> getEmployeeById(Long id) {
        logger.info(Constants.RETRIEVING_EMPLOYEE_BY_ID_LOG, id);
        Optional<Employee> employee = employeeRepository.findById(id); // Use repository to find by ID
        return employee.map(emp -> {
            EmployeeBO employeeBO = employeeMapper.employeeToEmployeeBO(emp);
            try {
                String json = JaxbUtil.toJson(employeeBO);
                logger.info("Retrieved Employee JSON: {}", json);

                // Unmarshalling(read) from JSON
                EmployeeBO unmarshalledEmployeeBO = JaxbUtil.fromJson(json, EmployeeBO.class);
                logger.info("Unmarshalled EmployeeBO: {}", unmarshalledEmployeeBO);

            } catch (JAXBException e) {
                logger.error("Error in JSON Conversion", e);
            }
            return employeeBO;
        });
    }
}
