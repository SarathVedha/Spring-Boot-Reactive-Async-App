package com.vedha.springboot.react.service;

import com.vedha.springboot.react.dto.EmployeeDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeService {

    Mono<EmployeeDTO> saveEmployee(EmployeeDTO employeeDTO);

    Mono<EmployeeDTO> getEmployee(String employeeId);

    Flux<EmployeeDTO> getAllEmployee();

    Mono<EmployeeDTO> updateEmployee(EmployeeDTO employeeDTO, String employeeId);

    Mono<Void> deleteEmployee(String employeeId);

    void deleteAllEmployee();

}
