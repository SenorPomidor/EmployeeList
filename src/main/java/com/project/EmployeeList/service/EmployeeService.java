package com.project.EmployeeList.service;

import com.project.EmployeeList.entity.Employee;
import com.project.EmployeeList.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private EmployeeRepository repository;

    @Autowired
    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public List<Employee> getAllEmployees() {
        return repository.getAllEmployees();
    }

    @Transactional
    public Employee saveEmployee(Employee employee) {
        return repository.save(employee);
    }

    @Transactional
    public Employee updateEmployee(Employee employee) {
        return repository.save(employee);
    }

    @Transactional
    public Employee getEmployee(Long id) {
        Optional<Employee> employee = repository.findById(id);
        if (employee.isEmpty()) {
            throw new RuntimeException("Employee " + id + " not found!");
        } else {
            return employee.get();
        }
    }

    @Transactional
    public void deleteEmployee(Long id) {
        repository.deleteById(id);
    }
}
