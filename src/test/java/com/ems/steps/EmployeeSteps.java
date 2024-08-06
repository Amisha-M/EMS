package com.ems.steps;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.ems.vo.EmployeeVO;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmployeeSteps {

    @LocalServerPort
    private int port;
    
    private String baseUrl;

    @Autowired
    private RestTemplate restTemplate;

    private EmployeeVO employee;
    private ResponseEntity<EmployeeVO> createResponse;
    private ResponseEntity<List> retrieveResponse;
    private ResponseEntity<EmployeeVO> retrieveByIdResponse;

    @Given("employee details")
    public void employee_details() {
        log.info("Initializing employee details");
        employee = new EmployeeVO(null, "Amar Singh", "Software Engg", 50000L);
        log.info("Employee details initialized: {}", employee);
    }

    @When("create the employee")
    public void create_the_employee() {
        baseUrl = "http://localhost:" + port + "/employees";
        log.info("Base URL set to: {}", baseUrl);
        createResponse = restTemplate.postForEntity(baseUrl + "/create", employee, EmployeeVO.class);
        log.info("Create response: {}", createResponse);
    }

    @Then("employee created successfully")
    public void employee_created_successfully() {
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(createResponse.getBody().getName()).isEqualTo(employee.getName());
        assertThat(createResponse.getBody().getPosition()).isEqualTo(employee.getPosition());
        assertThat(createResponse.getBody().getSalary()).isEqualTo(employee.getSalary());
    }

    @Given("there is a list of employees in the system")
    public void there_is_a_list_of_employees_in_the_system() {
        baseUrl = "http://localhost:" + port + "/employees";
        employee = new EmployeeVO(null, "Amar Singh", "Software Engg", 60000L);
        restTemplate.postForEntity(baseUrl + "/create", employee, EmployeeVO.class);
    }

    @When("retrieve all employees")
    public void retrieve_all_employees() {
        retrieveResponse = restTemplate.getForEntity(baseUrl + "/display", List.class);
    }

    @Then("get a list of employees")
    public void get_a_list_of_employees() {
        assertThat(retrieveResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(retrieveResponse.getBody().size()).isGreaterThan(0);
    }

    @Given("an employee with ID {long} exists")
    public void an_employee_with_ID_exists(Long id) {
        baseUrl = "http://localhost:" + port + "/employees";
        employee = new EmployeeVO(id, "John Doe", "Developer", 50000L);
        restTemplate.postForEntity(baseUrl + "/create", employee, EmployeeVO.class);
    }

    @When("retrieve the employee with ID {long}")
    public void retrieve_the_employee_with_ID(Long id) {
        retrieveByIdResponse = restTemplate.getForEntity(baseUrl + "/id/" + id, EmployeeVO.class);
    }

    @Then("get the employee details")
    public void get_the_employee_details() {
        assertThat(retrieveByIdResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(retrieveByIdResponse.getBody().getName()).isEqualTo(employee.getName());
        assertThat(retrieveByIdResponse.getBody().getPosition()).isEqualTo(employee.getPosition());
        assertThat(retrieveByIdResponse.getBody().getSalary()).isEqualTo(employee.getSalary());
    }
}
