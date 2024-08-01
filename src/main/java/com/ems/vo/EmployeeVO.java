package com.ems.vo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeVO {
	
	private Long id;
	
	@NotBlank(message = "Name cannot be blank")
    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
    private String name;
	
	@NotBlank(message = "Position cannot be blank")
    @Size(min = 1, max = 50, message = "Role must be between 1 and 50 characters")
    private String position;
    
	@NotNull(message = "Salary cannot be null")
	@Min(value = 0, message = "Salary must be non-negative")
    @Max(value = 1000000, message = "Salary must be less than or equal to 1,000,000")
    private Long salary;

}
