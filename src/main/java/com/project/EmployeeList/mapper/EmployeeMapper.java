package com.project.EmployeeList.mapper;

import com.project.EmployeeList.dto.EmployeeDTO;
import com.project.EmployeeList.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    public EmployeeDTO toDTO(Employee entity) {
        EmployeeDTO dto = new EmployeeDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setDepartment(entity.getDepartment());
        dto.setSalary(entity.getSalary());
        dto.setLogin(entity.getLogin());
        dto.setPassword(entity.getPassword());
        dto.setRole(entity.getRole());

        return dto;
    }

    public Employee toEntity(EmployeeDTO dto) {
        Employee employee = new Employee();

        employee.setId(dto.getId());
        employee.setName(dto.getName());
        employee.setSurname(dto.getSurname());
        employee.setDepartment(dto.getDepartment());
        employee.setSalary(dto.getSalary());
        employee.setLogin(dto.getLogin());
        employee.setPassword(dto.getPassword());
        employee.setRole(dto.getRole());

        return employee;
    }
}
