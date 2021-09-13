package com.project.EmployeeList.dto;

import com.project.EmployeeList.entity.Employee;
import com.project.EmployeeList.entity.Role;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class EmployeeDTO {
    private Long id;
    @NotBlank(message = "Name can't be empty!")
    private String name;
    @NotBlank(message = "Surname can't be empty!")
    private String surname;
    @NotBlank(message = "Department can't be empty!")
    private String department;
    @NotNull(message = "Salary can't be empty!")
    @Min(value = 100, message = "Salary should be more than 100!")
    private Integer salary;
    @NotBlank(message = "Login can't be empty!")
    private String login;
    @NotBlank(message = "Password can't be empty!")
    private String password;
    private Set<Role> role;
    private Set<Employee> employee;
    private Employee director;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) { this.salary = salary; }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRole() {
        return role;
    }

    public void setRole(Set<Role> role) {
        this.role = role;
    }

    public Set<Employee> getEmployee() {
        return employee;
    }

    public void setEmployee(Set<Employee> employee) {
        this.employee = employee;
    }

    public Employee getDirector() {
        return director;
    }

    public void setDirector(Employee director) {
        this.director = director;
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", department='" + department + '\'' +
                ", salary=" + salary +
                '}';
    }
}
