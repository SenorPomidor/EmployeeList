package com.project.EmployeeList.repository;

import com.project.EmployeeList.entity.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    @Query("from Employee order by id")
    List<Employee> getAllEmployees();

    Optional<Employee> findByLogin(String login);
}
