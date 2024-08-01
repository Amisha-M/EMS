package com.ems.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ems.service.EmployeeService;
import com.ems.util.Constants;
import com.ems.vo.EmployeeVO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/employees")
@Validated
public class EmployeeController {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

//    @Autowired
//    private EmployeeService employeeService;
	
	private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/create")
    public ResponseEntity<EmployeeVO> createEmployee(@RequestBody @Valid EmployeeVO employeeVO) {
        logger.info(Constants.CREATING_EMPLOYEE_LOG, employeeVO);
        EmployeeVO createdEmployee = employeeService.createEmployee(employeeVO);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @GetMapping("/display")
    public ResponseEntity<List<EmployeeVO>> getAllEmployees() {
        logger.info(Constants.RETRIEVING_ALL_EMPLOYEES_LOG);
        List<EmployeeVO> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<EmployeeVO> getEmployeeById(@PathVariable("id")@NotNull Long id) {
        logger.info(Constants.RETRIEVING_EMPLOYEE_BY_ID_LOG, id);
        Optional<EmployeeVO> employee = employeeService.getEmployeeById(id);
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        logger.info("GET /employees/health");
        return new ResponseEntity<>(Constants.HEALTH_CHECK_MESSAGE, HttpStatus.OK);
    }
	
}
