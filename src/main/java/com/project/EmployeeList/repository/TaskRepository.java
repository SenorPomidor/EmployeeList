package com.project.EmployeeList.repository;

import com.project.EmployeeList.entity.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {
    @Query("from Task t join Employee e on e.id = :employeeId where e.id = t.employee.id order by :employeeId")
    List<Task> getAllEmployeeTasks(Long employeeId);
}
