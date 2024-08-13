package com.ems.batch.tasklet;


import com.ems.EmployeeMapper;
import com.ems.entity.Employee;
import com.ems.repository.EmployeeRepository;
import com.ems.vo.EmployeeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReaderTasklet implements Tasklet {

    private static final Logger logger = LoggerFactory.getLogger(ReaderTasklet.class);

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Autowired
    public ReaderTasklet(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        // Fetch all employees from the database
        List<Employee> employees = employeeRepository.findAll();

        // Check if the list is null
        if (employees == null) {
            logger.error("Employee list is null");
            throw new IllegalStateException("Employee list is null");
        }

        // Convert Employee entities to EmployeeVOs
        List<EmployeeVO> employeeVOs = employees.stream()
                .map(employeeMapper::employeeToEmployeeVO)
                .collect(Collectors.toList());

        // Store the list in the ExecutionContext
        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("employeeVOs", employeeVOs);

        logger.info("Read {} employees and stored in ExecutionContext", employeeVOs.size());

        return RepeatStatus.FINISHED;
    }
}
