package com.ems.batch.job;

import com.ems.entity.Employee;
import com.ems.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
public class EmployeeItemReader implements ItemReader<Employee> {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeItemReader.class);

    private final EmployeeRepository employeeRepository;
    private Iterator<Employee> employeeIterator;

    @Autowired
    public EmployeeItemReader(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee read() {
        if (employeeIterator == null) {
            employeeIterator = employeeRepository.findAll().iterator();
        }
        if (employeeIterator.hasNext()) {
            Employee employee = employeeIterator.next();
            logger.info("Reading employee: {}", employee);
            return employee;
        } else {
            return null;
        }
    }
}
