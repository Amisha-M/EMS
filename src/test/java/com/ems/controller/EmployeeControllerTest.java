package com.ems.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.ems.bo.EmployeeBO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ems.service.EmployeeService;

public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateEmployee() {
        EmployeeBO employeeBO = new EmployeeBO(1L, "Amar Singh", "Software Eng", 50000L);

        when(employeeService.createEmployee(any(EmployeeBO.class))).thenReturn(employeeBO);

        ResponseEntity<EmployeeBO> responseEntity = employeeController.createEmployee(employeeBO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(employeeBO, responseEntity.getBody());
    }

    @Test
    void testGetAllEmployees() {
        EmployeeBO employee1 = new EmployeeBO(1L, "Amar Singh", "Software Eng", 50000L);
        EmployeeBO employee2 = new EmployeeBO(2L, "Ankit Gupta", "Sr. Software Eng", 70000L);

        when(employeeService.getAllEmployees()).thenReturn(Arrays.asList(employee1, employee2));

        ResponseEntity<List<EmployeeBO>> responseEntity = employeeController.getAllEmployees();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody().size());
        assertEquals(employee1, responseEntity.getBody().get(0));
        assertEquals(employee2, responseEntity.getBody().get(1));
    }

    @Test
    void testGetEmployeeById() {
        Long id = 1L;
        EmployeeBO employeeBO = new EmployeeBO(id, "Amar Singh", "Software Eng", 50000L);

        when(employeeService.getEmployeeById(id)).thenReturn(Optional.of(employeeBO));

        ResponseEntity<EmployeeBO> responseEntity = employeeController.getEmployeeById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(employeeBO, responseEntity.getBody());
    }

    @Test
    void testGetEmployeeById_NotFound() {
        when(employeeService.getEmployeeById(1L)).thenReturn(Optional.empty());

        ResponseEntity<EmployeeBO> responseEntity = employeeController.getEmployeeById(1L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testHealthCheck() {
        ResponseEntity<String> responseEntity = employeeController.healthCheck();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Health Check Passed", responseEntity.getBody());
    }

}
