package com.ems.util;

public final class Constants {
	
	// Health Check Message
    public static final String HEALTH_CHECK_MESSAGE = "Health Check Passed";

    // Logging messages
    public static final String CREATING_EMPLOYEE_LOG = "Creating employee: {}";
    public static final String RETRIEVING_ALL_EMPLOYEES_LOG = "Retrieving all employees";
    public static final String RETRIEVING_EMPLOYEE_BY_ID_LOG = "Retrieving employee with ID: {}";

    // Error messages
    public static final String EMPLOYEE_NOT_FOUND = "Employee not found with ID: {}";


    public static final String RUNNING_BATCH_JOB_LOG = "Running batch job";
    // Batch job success/failure messages
    public static final String BATCH_JOB_SUCCESS = "Batch job completed successfully.";
    public static final String BATCH_JOB_FAILURE = "Batch job failed.";

    // Prevent instantiation
    private Constants() {
        throw new UnsupportedOperationException("Cannot instantiate utility class.");
    }

}
