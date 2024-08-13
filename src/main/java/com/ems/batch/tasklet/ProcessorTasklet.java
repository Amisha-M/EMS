package com.ems.batch.tasklet;

import com.ems.EmployeeMapper;
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
public class ProcessorTasklet implements Tasklet {

    private static final Logger logger = LoggerFactory.getLogger(ProcessorTasklet.class);

    private final EmployeeMapper employeeMapper;

    @Autowired
    public ProcessorTasklet(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        List<EmployeeVO> employeeVOs = (List<EmployeeVO>) chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("employeeVOs");

        // Example processing: filter employees with salary > 50000
//        List<EmployeeVO> processedEmployeeVOs = employeeVOs.stream()
//                .filter(vo -> vo.getSalary() > 50000)
//                .collect(Collectors.toList());
//
//        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("processedEmployeeVOs", processedEmployeeVOs);

        logger.info("Processed {} employees", employeeVOs.size());

        return RepeatStatus.FINISHED;
    }

}
