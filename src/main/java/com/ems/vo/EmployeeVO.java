package com.ems.vo;

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
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;
	
	@NotBlank(message = "Position cannot be blank")
    @Size(min = 2, max = 100, message = "Position must be between 2 and 100 characters")
    private String position;
    
	@NotNull(message = "Salary cannot be null")
    @Min(value = 15000, message = "Salary must be above 15000")
    private Long salary;

}
