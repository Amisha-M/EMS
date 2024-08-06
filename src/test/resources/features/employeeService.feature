Feature: Employee Service

  Scenario: Create an employee
    Given I have employee data
    When I create the employee
    Then the employee should be created successfully

  Scenario: Retrieve all employees
    Given there are employees in the system
    When I retrieve all employees
    Then I should get a list of all employees

  Scenario: Retrieve an employee by ID
    Given there is an employee with ID 1
    When I retrieve the employee by ID 1
    Then I should get the employee details

  Scenario: Retrieve an employee by non-existent ID
    When I retrieve the employee by non-existent ID 999
    Then I should receive a not found response
