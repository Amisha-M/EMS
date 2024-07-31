package com.ems.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ems.EmployeeMapper;
import com.ems.bo.EmployeeBO;
import com.ems.entity.Employee;
import com.ems.repository.EmployeeRepository;
import com.ems.util.Constants;
import com.ems.vo.EmployeeVO;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

//    @Autowired
//    private EmployeeRepository employeeRepository;

//	@Autowired
//	private EmployeeBO employeeBO;
//	
//	private final EmployeeMapper employeeMapper = EmployeeMapper.INSTANCE;
	
    
	private final EmployeeBO employeeBO;
    private final EmployeeMapper employeeMapper;

    //Constructor injection
    @Autowired
    public EmployeeServiceImpl(EmployeeBO employeeBO, EmployeeMapper employeeMapper) {
        this.employeeBO = employeeBO;
        this.employeeMapper = employeeMapper;
    }
    
	@Override
    public EmployeeVO createEmployee(EmployeeVO employeeVO) {
    	 logger.info(Constants.CREATING_EMPLOYEE_LOG, employeeVO);
         Employee employee = employeeMapper.employeeVOToEmployee(employeeVO);
         Employee createdEmployee = employeeBO.createEmployee(employee);
         return employeeMapper.employeeToEmployeeVO(createdEmployee);
     }

	@Override
    public List<EmployeeVO> getAllEmployees() {
		 logger.info(Constants.RETRIEVING_ALL_EMPLOYEES_LOG);
	        List<Employee> employees = employeeBO.getAllEmployees();
	        return employees.stream()
	                        .map(employeeMapper::employeeToEmployeeVO)
	                        .collect(Collectors.toList());
    }

	@Override
    public Optional<EmployeeVO> getEmployeeById(Long id) {
		logger.info(Constants.RETRIEVING_EMPLOYEE_BY_ID_LOG, id);
        Optional<Employee> employee = employeeBO.getEmployeeById(id);
        return employee.map(employeeMapper::employeeToEmployeeVO);
    }
	
}
