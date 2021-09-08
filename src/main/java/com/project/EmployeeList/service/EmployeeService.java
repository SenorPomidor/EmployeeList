package com.project.EmployeeList.service;

import com.project.EmployeeList.dto.EmployeeDTO;
import com.project.EmployeeList.entity.Employee;
import com.project.EmployeeList.entity.Role;
import com.project.EmployeeList.mapper.EmployeeMapper;
import com.project.EmployeeList.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements UserDetailsService {
    private EmployeeRepository repository;

    private PasswordEncoder passwordEncoder;

    private EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeService(EmployeeRepository repository, PasswordEncoder passwordEncoder, EmployeeMapper employeeMapper) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.employeeMapper = employeeMapper;
    }

    @Transactional
    public List<Employee> getAllEmployees() {
        return repository.getAllEmployees();
    }

    @Transactional
    public Employee saveEmployee(EmployeeDTO employeeDTO, Employee director) {
        Employee employee = employeeMapper.toEntity(employeeDTO);
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employee.setRole(Collections.singleton(Role.EMPLOYEE));
        employee.setDirector(director);
        return repository.save(employee);
    }

    @Transactional
    public Employee saveDirector(EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.toEntity(employeeDTO);
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employee.setRole(Collections.singleton(Role.DIRECTOR));
        return repository.save(employee);
    }

    @Transactional
    public boolean isEmployeeExist(String login) {
        Optional<Employee> employee = repository.findByLogin(login);
        return employee.isPresent();
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
    public Optional<Employee> getEmployeeByLogin(String login) {
        return repository.findByLogin(login);
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
