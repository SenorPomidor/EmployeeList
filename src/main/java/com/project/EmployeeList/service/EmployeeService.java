package com.project.EmployeeList.service;

import com.project.EmployeeList.entity.Employee;
import com.project.EmployeeList.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements UserDetailsService {
    private EmployeeRepository repository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeService(EmployeeRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
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

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Employee> employee = repository.findByLogin(login);

        if (employee.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        return employee.get();
    }
}
