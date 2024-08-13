package com.ems.controller;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Employee Controller", description = "API for managing employees")  /*http://localhost:8080/v3/api-docs || http://localhost:8080/swagger-ui.html*/
public class EmployeeController {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(summary = "Create a new employee", description = "This API is used to create a new employee in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeVO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/create")
    public ResponseEntity<EmployeeVO> createEmployee(@RequestBody @Valid EmployeeVO employeeVO) {
        logger.info(Constants.CREATING_EMPLOYEE_LOG, employeeVO);
        EmployeeVO createdEmployee = employeeService.createEmployee(employeeVO);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @Operation(summary = "Retrieve all employees", description = "This API retrieves all employees from the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeVO.class))),
            @ApiResponse(responseCode = "404", description = "No employees found",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/display")
    public ResponseEntity<List<EmployeeVO>> getAllEmployees() {
        logger.info(Constants.RETRIEVING_ALL_EMPLOYEES_LOG);
        List<EmployeeVO> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @Operation(summary = "Retrieve an employee by ID", description = "This API retrieves a specific employee by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved employee",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmployeeVO.class))),
            @ApiResponse(responseCode = "404", description = "Employee not found",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<EmployeeVO> getEmployeeById(@Parameter(description = "ID of the employee to be retrieved", required = true) @PathVariable("id")@NotNull Long id) {
        logger.info(Constants.RETRIEVING_EMPLOYEE_BY_ID_LOG, id);
        Optional<EmployeeVO> employee = employeeService.getEmployeeById(id);
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Health check", description = "This API provides a basic health check for the service.")
    @ApiResponse(responseCode = "200", description = "Health check successful",
            content = @Content(mediaType = "text/plain"))
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        logger.info("GET /employees/health");
        return new ResponseEntity<>(Constants.HEALTH_CHECK_MESSAGE, HttpStatus.OK);
    }
	
}
