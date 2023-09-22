package com.vedha.springboot.react.repository;

import com.vedha.springboot.react.entity.EmployeeEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EmployeeRepository extends ReactiveCrudRepository<EmployeeEntity, String> {
}
