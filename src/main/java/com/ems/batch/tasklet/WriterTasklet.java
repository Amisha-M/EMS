package com.ems.batch.tasklet;

import com.ems.EmployeeMapper;
import com.ems.entity.Employee;
import com.ems.repository.EmployeeRepository;
import com.ems.vo.EmployeeVO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WriterTasklet implements Tasklet {

    private static final Logger logger = LoggerFactory.getLogger(WriterTasklet.class);
    private static final String FILE_NAME = "src/main/resources/employeesTasklet.csv";

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Autowired
    public WriterTasklet(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        List<Employee> employees = employeeRepository.findAll();

        if (employees == null || employees.isEmpty()) {
            logger.warn("No employees found to write.");
            return RepeatStatus.FINISHED;
        }

        List<EmployeeVO> employeeVOs = employees.stream()
                .map(employeeMapper::employeeToEmployeeVO)
                .collect(Collectors.toList());

        try (CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(FILE_NAME), CSVFormat.DEFAULT.withHeader("ID", "Name", "Position", "Salary"))) {
            for (EmployeeVO employeeVO : employeeVOs) {
                csvPrinter.printRecord(employeeVO.getId(), employeeVO.getName(), employeeVO.getPosition(), employeeVO.getSalary());
                logger.info("Writing employee to CSV: {}", employeeVO);
            }
        } catch (IOException e) {
            logger.error("Error writing to CSV file: {}", FILE_NAME, e);
            throw new RuntimeException("Failed to write employees to CSV", e);
        }

        return RepeatStatus.FINISHED;
    }

}
