package com.ems;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import com.ems.entity.Employee;
import com.ems.vo.EmployeeVO;

@Component
@Mapper(componentModel = "spring")
public interface EmployeeMapper {

	EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeVO employeeToEmployeeVO(Employee employee);

    Employee employeeVOToEmployee(EmployeeVO employeeVO);
}
