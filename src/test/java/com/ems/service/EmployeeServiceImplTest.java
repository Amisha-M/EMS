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
import com.ems.vo.EmployeeVO;

public class EmployeeServiceImplTest {
	
	@Mock
    private EmployeeBO employeeBO;

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
        EmployeeVO employeeVO = new EmployeeVO(1L, "John Doe", "Developer", 50000L);
        Employee employee = new Employee(1L, "John Doe", "Developer", 50000L);
        
        when(employeeMapper.employeeVOToEmployee(any(EmployeeVO.class))).thenReturn(employee);
        when(employeeBO.createEmployee(any(Employee.class))).thenReturn(employee);
        when(employeeMapper.employeeToEmployeeVO(any(Employee.class))).thenReturn(employeeVO);

        EmployeeVO createdEmployeeVO = employeeServiceImpl.createEmployee(employeeVO);

        assertEquals(employeeVO, createdEmployeeVO);
    }

    @Test
    void testGetAllEmployees() {
        Employee employee1 = new Employee(1L, "John Doe", "Developer", 50000L);
        Employee employee2 = new Employee(2L, "Jane Doe", "Manager", 70000L);
        
        when(employeeBO.getAllEmployees()).thenReturn(Arrays.asList(employee1, employee2));
        when(employeeMapper.employeeToEmployeeVO(any(Employee.class))).thenReturn(
            new EmployeeVO(1L, "John Doe", "Developer", 50000L),
            new EmployeeVO(2L, "Jane Doe", "Manager", 70000L)
        );

        List<EmployeeVO> employees = employeeServiceImpl.getAllEmployees();

        assertEquals(2, employees.size());
    }

    @Test
    void testGetEmployeeById() {
        Employee employee = new Employee(1L, "John Doe", "Developer", 50000L);
        EmployeeVO employeeVO = new EmployeeVO(1L, "John Doe", "Developer", 50000L);
        
        when(employeeBO.getEmployeeById(1L)).thenReturn(Optional.of(employee));
        when(employeeMapper.employeeToEmployeeVO(any(Employee.class))).thenReturn(employeeVO);

        Optional<EmployeeVO> foundEmployeeVO = employeeServiceImpl.getEmployeeById(1L);

        assertEquals(employeeVO, foundEmployeeVO.get());
    }

}
