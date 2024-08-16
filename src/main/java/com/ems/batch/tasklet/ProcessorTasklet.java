package com.ems.batch.tasklet;

import com.ems.EmployeeMapper;
import com.ems.bo.EmployeeBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProcessorTasklet implements Tasklet {

    private static final Logger logger = LoggerFactory.getLogger(ProcessorTasklet.class);

    private final EmployeeMapper employeeMapper;

    @Autowired
    public ProcessorTasklet(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        List<EmployeeBO> employeeBOs = (List<EmployeeBO>) chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("employeeBOs");

//        if (employeeBOs != null) {
//            // Example processing: filter employees with salary > 50000
//            List<EmployeeBO> processedEmployeeBOs = employeeBOs.stream()
//                    .filter(bo -> bo.getSalary() > 50000)
//                    .toList();
//
//            chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("processedEmployeeBOs", processedEmployeeBOs);
//
//            logger.info("Processed {} employees", processedEmployeeBOs.size());
//        } else {
//            logger.warn("No employeeBOs found in execution context");
//        }

        return RepeatStatus.FINISHED;
    }

}
