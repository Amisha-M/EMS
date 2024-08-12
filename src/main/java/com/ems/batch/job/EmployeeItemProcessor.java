package com.ems.batch.job;

import com.ems.entity.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class EmployeeItemProcessor implements ItemProcessor<Employee, Employee> {

    @Override
    public Employee process(Employee employee) {
        // Example: Modify the employee data if needed
        return employee;
    }
}