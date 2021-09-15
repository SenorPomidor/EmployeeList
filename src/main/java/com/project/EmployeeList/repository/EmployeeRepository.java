package com.project.EmployeeList.repository;

import com.project.EmployeeList.entity.Employee;
import com.project.EmployeeList.entity.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    @Query("from Employee order by id")
    List<Employee> getAllEmployees();

    @Query("from Task t join Employee e on e.id = :employeeId where e.id = t.employee.id order by t.id")
    List<Task> getEmployeeTasks(Long employeeId);

    Optional<Employee> findByLogin(String login);
}
