package com.vedha.springboot.react;

import com.vedha.springboot.react.dto.EmployeeDTO;
import com.vedha.springboot.react.service.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private EmployeeService employeeService;

    @BeforeEach
    public void beforeTest() {

        employeeService.deleteAllEmployee();
    }

    @Test
    public void testEmployeeSave() {

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeName("Test");
        employeeDTO.setEmployeeAddress("TRT");
        employeeDTO.setEmployeeEmail("test@gmail.com");

        webTestClient.post().uri("/api/employees/save")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(employeeDTO), EmployeeDTO.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.employeeName").isEqualTo(employeeDTO.getEmployeeName())
                .jsonPath("$.employeeAddress").isEqualTo(employeeDTO.getEmployeeAddress())
                .jsonPath("$.employeeEmail").isEqualTo(employeeDTO.getEmployeeEmail());
    }

    @Test
    public void testEmployeeGet() {

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeName("Test1");
        employeeDTO.setEmployeeAddress("TRT1");
        employeeDTO.setEmployeeEmail("test1@gmail.com");

        EmployeeDTO savedEmployee = employeeService.saveEmployee(employeeDTO).block();

        assert savedEmployee != null;
        webTestClient.get().uri("/api/employees/{employeeId}", Collections.singletonMap("employeeId", savedEmployee.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.id").isEqualTo(savedEmployee.getId())
                .jsonPath("$.employeeName").isEqualTo(employeeDTO.getEmployeeName())
                .jsonPath("$.employeeAddress").isEqualTo(employeeDTO.getEmployeeAddress())
                .jsonPath("$.employeeEmail").isEqualTo(employeeDTO.getEmployeeEmail());
    }

    @Test
    public void testEmployeeGetAll() {

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeName("Test");
        employeeDTO.setEmployeeAddress("TRT");
        employeeDTO.setEmployeeEmail("test@gmail.com");

        employeeService.saveEmployee(employeeDTO).block();

        EmployeeDTO employeeDTO1 = new EmployeeDTO();
        employeeDTO1.setEmployeeName("Test1");
        employeeDTO1.setEmployeeAddress("TRT1");
        employeeDTO1.setEmployeeEmail("test1@gmail.com");

        employeeService.saveEmployee(employeeDTO1).block();

        webTestClient.get().uri("/api/employees")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    public void testEmployeeUpdate() {

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeName("Test");
        employeeDTO.setEmployeeAddress("TRT");
        employeeDTO.setEmployeeEmail("test@gmail.com");

        EmployeeDTO savedEmployeeDTO = employeeService.saveEmployee(employeeDTO).block();
        //System.out.println(savedEmployeeDTO);

        EmployeeDTO updateEmployeeDTO = new EmployeeDTO();
        updateEmployeeDTO.setEmployeeName("Test-Updated");
        updateEmployeeDTO.setEmployeeAddress("TRT-Updated");
        updateEmployeeDTO.setEmployeeEmail("test-updated@gmail.com");

        assert savedEmployeeDTO != null;
        webTestClient.put().uri("/api/employees/update/{employeeId}", Collections.singletonMap("employeeId", savedEmployeeDTO.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(updateEmployeeDTO), EmployeeDTO.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.id").isEqualTo(savedEmployeeDTO.getId())
                .jsonPath("$.employeeName").isEqualTo(updateEmployeeDTO.getEmployeeName())
                .jsonPath("$.employeeAddress").isEqualTo(updateEmployeeDTO.getEmployeeAddress())
                .jsonPath("$.employeeEmail").isEqualTo(updateEmployeeDTO.getEmployeeEmail());

    }

    @Test
    public void testEmployeeDelete() {

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeName("Test");
        employeeDTO.setEmployeeAddress("TRT");
        employeeDTO.setEmployeeEmail("test@gmail.com");

        EmployeeDTO savedEmployeeDTO = employeeService.saveEmployee(employeeDTO).block();

        assert savedEmployeeDTO != null;
        webTestClient.delete().uri("/api/employees/delete/{employeeId}", Collections.singletonMap("employeeId", savedEmployeeDTO.getId()))
                .exchange()
                .expectStatus().isNoContent()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @AfterEach
    public void after() {

        employeeService.deleteAllEmployee();
    }
}
