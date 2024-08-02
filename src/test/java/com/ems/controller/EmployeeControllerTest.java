package com.ems.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ems.service.EmployeeService;
import com.ems.vo.EmployeeVO;

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
        EmployeeVO employeeVO = new EmployeeVO(1L, "John Doe", "Developer", 50000L);
        
        when(employeeService.createEmployee(any(EmployeeVO.class))).thenReturn(employeeVO);

        ResponseEntity<EmployeeVO> responseEntity = employeeController.createEmployee(employeeVO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(employeeVO, responseEntity.getBody());
    }

    @Test
    void testGetAllEmployees() {
        EmployeeVO employee1 = new EmployeeVO(1L, "John Doe", "Developer", 50000L);
        EmployeeVO employee2 = new EmployeeVO(2L, "Jane Doe", "Manager", 70000L);
        
        when(employeeService.getAllEmployees()).thenReturn(Arrays.asList(employee1, employee2));

        ResponseEntity<List<EmployeeVO>> responseEntity = employeeController.getAllEmployees();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody().size());
        assertEquals(employee1, responseEntity.getBody().get(0));
        assertEquals(employee2, responseEntity.getBody().get(1));
    }

    @Test
    void testGetEmployeeById() {
        EmployeeVO employeeVO = new EmployeeVO(1L, "John Doe", "Developer", 50000L);
        
        when(employeeService.getEmployeeById(1L)).thenReturn(Optional.of(employeeVO));

        ResponseEntity<EmployeeVO> responseEntity = employeeController.getEmployeeById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(employeeVO, responseEntity.getBody());
    }

    @Test
    void testGetEmployeeById_NotFound() {
        when(employeeService.getEmployeeById(1L)).thenReturn(Optional.empty());

        ResponseEntity<EmployeeVO> responseEntity = employeeController.getEmployeeById(1L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testHealthCheck() {
        ResponseEntity<String> responseEntity = employeeController.healthCheck();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Health Check Passed", responseEntity.getBody());
    }

}
