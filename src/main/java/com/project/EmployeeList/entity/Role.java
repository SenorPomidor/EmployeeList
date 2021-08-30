package com.project.EmployeeList.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    DIRECTOR, EMPLOYEE;

    @Override
    public String getAuthority() {
        return null;
    }
}
