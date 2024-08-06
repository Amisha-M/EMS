Feature: Employee Management

  Scenario: Create new employee
    Given employee details
    When create the employee
    Then employee created successfully

  Scenario: Retrieve all the employees
    Given there is a list of employees in the system
    When retrieve all employees
    Then get a list of employees

  Scenario: Retrieve employee by ID
    Given an employee with ID 1 exists
    When retrieve the employee with ID 1
    Then get the employee details
