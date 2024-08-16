package com.ems;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import com.ems.entity.Employee;
import com.ems.bo.EmployeeBO;

@Component
@Mapper(componentModel = "spring")
public interface EmployeeMapper {

	EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeBO employeeToEmployeeBO(Employee employee);

    Employee employeeBOToEmployee(EmployeeBO employeeBO);
}
