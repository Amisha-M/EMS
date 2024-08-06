package com.ems.steps;

import static org.junit.Assert.*;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;


import com.ems.service.EmployeeService;
import com.ems.vo.EmployeeVO;

import io.cucumber.java.en.*;


public class EmployeeServiceSteps {

    @Autowired
    private EmployeeService employeeService;

    private EmployeeVO employee;
    private EmployeeVO createdEmployee;
    private List<EmployeeVO> employees;
    private Optional<EmployeeVO> employeeById;
    private Long employeeId;

    @Given("I have employee data")
    public void i_have_employee_data() {
        employee = new EmployeeVO(null, "John Doe", "Developer", 20000L);
    }

    @When("I create the employee")
    public void i_create_the_employee() {
        createdEmployee = employeeService.createEmployee(employee);
    }

    @Then("the employee should be created successfully")
    public void the_employee_should_be_created_successfully() {
        assertNotNull(createdEmployee.getId());
        assertEquals(employee.getName(), createdEmployee.getName());
    }

    @Given("there are employees in the system")
    public void there_are_employees_in_the_system() {
        employeeService.createEmployee(new EmployeeVO(null, "Jane Smith", "Manager", 30000L));
        employeeService.createEmployee(new EmployeeVO(null, "Bob Brown", "Analyst", 25000L));
    }

    @When("I retrieve all employees")
    public void i_retrieve_all_employees() {
        employees = employeeService.getAllEmployees();
    }

    @Then("I should get a list of all employees")
    public void i_should_get_a_list_of_all_employees() {
        assertFalse(employees.isEmpty());
    }

    @Given("there is an employee with ID {long}")
    public void there_is_an_employee_with_id(Long id) {
        // Create and save an employee with the given ID
        employee = new EmployeeVO(id, "Alice Green", "Designer", 28000L);
        createdEmployee = employeeService.createEmployee(employee);
        employeeId = createdEmployee.getId();
    }

    @When("I retrieve the employee by ID {long}")
    public void i_retrieve_the_employee_by_id(Long id) {
        // Retrieve employee by the given ID
        employeeById = employeeService.getEmployeeById(id);
    }

    @Then("I should get the employee details")
    public void i_should_get_the_employee_details() {
        assertTrue(employeeById.isPresent());
        assertEquals(employee.getName(), employeeById.get().getName());
    }

    @When("I retrieve the employee by non-existent ID {long}")
    public void i_retrieve_the_employee_by_non_existent_id(Long id) {
        // Retrieve employee by non-existent ID
        employeeById = employeeService.getEmployeeById(id);
    }

    @Then("I should receive a not found response")
    public void i_should_receive_a_not_found_response() {
        assertFalse(employeeById.isPresent());
    }
}
