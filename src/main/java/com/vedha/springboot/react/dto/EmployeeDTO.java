package com.vedha.springboot.react.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    private String id;

    private String employeeName;

    private String employeeAddress;

    private String employeeEmail;
}
