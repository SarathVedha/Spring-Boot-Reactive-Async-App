package com.vedha.springboot.react.service.impl;

import com.vedha.springboot.react.dto.EmployeeDTO;
import com.vedha.springboot.react.entity.EmployeeEntity;
import com.vedha.springboot.react.repository.EmployeeRepository;
import com.vedha.springboot.react.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    private ModelMapper modelMapper;

    @Override
    public Mono<EmployeeDTO> saveEmployee(EmployeeDTO employeeDTO) {

        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);

        Mono<EmployeeEntity> save = employeeRepository.save(employeeEntity);

        return save.map(savedEmployeeEntity -> modelMapper.map(savedEmployeeEntity, EmployeeDTO.class));
    }

    @Override
    public Mono<EmployeeDTO> getEmployee(String employeeId) {

        Mono<EmployeeEntity> byId = employeeRepository.findById(employeeId);

        return byId.map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class)).switchIfEmpty(Mono.empty());
    }

    @Override
    public Flux<EmployeeDTO> getAllEmployee() {

        Flux<EmployeeEntity> all = employeeRepository.findAll();

        return all.map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class)).switchIfEmpty(Flux.empty());
    }

    @Override
    public Mono<EmployeeDTO> updateEmployee(EmployeeDTO employeeDTO, String employeeId) {

        Mono<EmployeeEntity> byId = employeeRepository.findById(employeeId);

        Mono<EmployeeEntity> updatedEmployeeEntityMono = byId.flatMap(employeeEntity -> {
            employeeEntity.setEmployeeName(employeeDTO.getEmployeeName());
            employeeEntity.setEmployeeAddress(employeeDTO.getEmployeeAddress());
            employeeEntity.setEmployeeEmail(employeeDTO.getEmployeeEmail());

            return employeeRepository.save(employeeEntity);
        });

        return updatedEmployeeEntityMono.map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class));
    }

    @Override
    public Mono<Void> deleteEmployee(String employeeId) {

        return employeeRepository.deleteById(employeeId);
    }

    @Override
    public void deleteAllEmployee() {

        employeeRepository.deleteAll().subscribe();
    }

}
