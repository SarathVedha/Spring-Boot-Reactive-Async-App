package com.vedha.springboot.react.controller;

import com.vedha.springboot.react.dto.EmployeeDTO;
import com.vedha.springboot.react.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/employees")
@AllArgsConstructor
public class EmployeeController {

    private EmployeeService employeeService;

    // http://localhost:8080/api/employees
    // Reactive REST API
    // Mono -> Returns Single or Empty(Void) Value
    // Flux -> Returns Multiple Value
    @PostMapping("/save")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<EmployeeDTO> saveEmployee(@RequestBody EmployeeDTO employeeDTO) {

        return employeeService.saveEmployee(employeeDTO);
    }

    @GetMapping("{employeeId}")
    public Mono<EmployeeDTO> getEmployee(@PathVariable("employeeId") String employeeId) {

        return employeeService.getEmployee(employeeId);
    }

    @GetMapping()
    public Flux<EmployeeDTO> getAllEmployee() {

        return employeeService.getAllEmployee();
    }

    @PutMapping("/update/{employeeId}")
    public Mono<EmployeeDTO> updateEmployee(@PathVariable("employeeId") String employeeId, @RequestBody EmployeeDTO employeeDTO) {

        return employeeService.updateEmployee(employeeDTO, employeeId);
    }

    @DeleteMapping("/delete/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteEmployee(@PathVariable("employeeId") String employeeId) {

        return employeeService.deleteEmployee(employeeId);
    }

}
