package com.ems.service;

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

import com.ems.EmployeeMapper;
import com.ems.bo.EmployeeBO;
import com.ems.entity.Employee;
import com.ems.repository.EmployeeRepository;

public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateEmployee() {
        EmployeeBO employeeBO = new EmployeeBO(1L, "Amar Singh", "Software Eng", 50000L);
        Employee employee = new Employee(1L, "Amar Singh", "Software Eng", 50000L);

        when(employeeMapper.employeeBOToEmployee(any(EmployeeBO.class))).thenReturn(employee);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        when(employeeMapper.employeeToEmployeeBO(any(Employee.class))).thenReturn(employeeBO);

        EmployeeBO createdEmployeeBO = employeeServiceImpl.createEmployee(employeeBO);

        assertEquals(employeeBO, createdEmployeeBO);
    }

    @Test
    void testGetAllEmployees() {
        Employee employee1 = new Employee(1L, "Amar Singh", "Software Eng", 50000L);
        Employee employee2 = new Employee(2L, "Ankit Gupta", "Sr. Software Eng", 70000L);

        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1, employee2));
        when(employeeMapper.employeeToEmployeeBO(any(Employee.class))).thenReturn(
                new EmployeeBO(1L, "Amar Singh", "Software Eng", 50000L),
                new EmployeeBO(2L, "Ankit Gupta", "Sr. Software Eng", 70000L)
        );

        List<EmployeeBO> employees = employeeServiceImpl.getAllEmployees();

        assertEquals(2, employees.size());
    }

    @Test
    void testGetEmployeeById() {
        Employee employee = new Employee(1L, "Amar Singh", "Software Eng", 50000L);
        EmployeeBO employeeBO = new EmployeeBO(1L, "Amar Singh", "Software Eng", 50000L);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeMapper.employeeToEmployeeBO(employee)).thenReturn(employeeBO);

        Optional<EmployeeBO> foundEmployeeBO = employeeServiceImpl.getEmployeeById(1L);

        assertEquals(Optional.of(employeeBO), foundEmployeeBO);
    }
}
