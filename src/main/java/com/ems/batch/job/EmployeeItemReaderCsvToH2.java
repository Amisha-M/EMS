package com.ems.batch.job;

import com.ems.entity.Employee;
import com.ems.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Component
public class EmployeeItemReaderCsvToH2 implements ItemReader<Employee> {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeItemReader.class);
    private static final String FILE_NAME = "src/main/resources/employees.csv";
    private BufferedReader reader;
    private String currentLine;

    public EmployeeItemReaderCsvToH2() {
        try {
            reader = new BufferedReader(new FileReader(FILE_NAME));
            // Skip the header line
            reader.readLine();
        } catch (IOException e) {
            logger.error("Error opening the CSV file: {}", FILE_NAME, e);
        }
    }

    @Override
    public Employee read() {
        try {
            if ((currentLine = reader.readLine()) != null) {
                String[] employeeData = currentLine.split(",");
                Employee employee = new Employee();
                employee.setId(Long.parseLong(employeeData[0]));
                employee.setName(employeeData[1]);
                employee.setPosition(employeeData[2]);
                employee.setSalary(Long.parseLong(employeeData[3]));
                logger.info("Reading employee: {}", employee);
                return employee;
            } else {
                reader.close();
            }
        } catch (IOException e) {
            logger.error("Error reading the CSV file", e);
        }
        return null;
    }
}