package com.project.EmployeeList.dto;

import com.project.EmployeeList.entity.Employee;

import javax.validation.constraints.NotBlank;

public class TaskDTO {
    private Long id;
    @NotBlank(message = "Description can't be empty!")
    private String description;
    private boolean complete;
    private Employee employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee_id(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "TaskDTO{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", complete=" + complete +
                ", employee_id=" + employee +
                '}';
    }
}
