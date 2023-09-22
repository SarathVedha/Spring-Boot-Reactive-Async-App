package com.vedha.springboot.react.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "employees")
public class EmployeeEntity {

    @Id
    private String id;

    private String employeeName;

    private String employeeAddress;

    private String employeeEmail;
}
