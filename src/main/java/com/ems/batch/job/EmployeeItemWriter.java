package com.ems.batch.job;

import com.ems.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class EmployeeItemWriter implements ItemWriter<Employee> {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeItemWriter.class);
    private static final String FILE_HEADER = "ID,Name,Position,Salary";
    private static final String FILE_NAME = "src/main/resources/employeesH2ToCsv.csv";

    @Override
    public void write(Chunk<? extends Employee> employees) throws IOException {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            if (new java.io.File(FILE_NAME).length() == 0) {
                writer.write(FILE_HEADER + "\n");
            }
            for (Employee employee : employees) {
                writer.write(String.format("%d,%s,%s,%d\n", employee.getId(), employee.getName(), employee.getPosition(), employee.getSalary()));
                logger.info("Writing employee: {}", employee);
            }
        }
    }
}
