package com.project.EmployeeList.controller;


import com.project.EmployeeList.dto.EmployeeDTO;
import com.project.EmployeeList.entity.Employee;
import com.project.EmployeeList.entity.Role;
import com.project.EmployeeList.mapper.EmployeeMapper;
import com.project.EmployeeList.service.EmployeeService;
import com.project.EmployeeList.util.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MainController {
    private final EmployeeService employeeService;

    private final EmployeeMapper employeeMapper;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MainController(EmployeeService employeeService, EmployeeMapper employeeMapper, PasswordEncoder passwordEncoder) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String showAllEmployees(Model model, @AuthenticationPrincipal Employee employee) {
        if (employee.getAuthorities().contains(Role.DIRECTOR)) {
            List<Employee> employeeList = employeeService.getAllEmployees();
            List<EmployeeDTO> dtos = employeeList.stream()
                    .filter(e -> !e.getAuthorities().contains(Role.DIRECTOR))
                    .map(e -> employeeMapper.toDTO(e))
                    .collect(Collectors.toList());
            model.addAttribute("allEmps", dtos);

            return "all-employees";
        } else {
            return "employee-main";
        }
    }

    @PreAuthorize("hasAuthority('DIRECTOR')")
    @GetMapping("/addNewEmployee")
    public String addNewEmployee(Model model) {
        EmployeeDTO employee = new EmployeeDTO();
        model.addAttribute("employee", employee);

        return "employee-info";
    }

    @PreAuthorize("hasAuthority('DIRECTOR')")
    @PostMapping("/saveEmployee")
    public String saveEmployee(
            @ModelAttribute("employee") @Valid EmployeeDTO employeeDTO,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errors);

            return "employee-info";
        }

        Employee employee = employeeMapper.toEntity(employeeDTO);
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employee.setRole(Collections.singleton(Role.EMPLOYEE));
        employeeService.saveEmployee(employee);

        return "redirect:/";
    }

    @PreAuthorize("hasAuthority('DIRECTOR')")
    @PostMapping("/updateEmployee")
    public String updateEmployee(
            @ModelAttribute("employee") @Valid EmployeeDTO employeeDTO,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);

            System.out.println(errors);
            model.mergeAttributes(errors);

            return "employee-update";
        }

        Employee employee = employeeService.getEmployee(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setSurname(employeeDTO.getSurname());
        employee.setDepartment(employeeDTO.getDepartment());
        employee.setSalary(employeeDTO.getSalary());
        employee.setLogin(employeeDTO.getLogin());
        employee.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
        employeeService.updateEmployee(employee);

        return "redirect:/";
    }

    @PreAuthorize("hasAuthority('DIRECTOR')")
    @GetMapping("/updateEmployee/{id}")
    public String updateEmployee(Model model, @PathVariable Long id) {
        Employee employee = employeeService.getEmployee(id);
        model.addAttribute("employee", employeeMapper.toDTO(employee));

        return "employee-update";
    }

    @PreAuthorize("hasAuthority('DIRECTOR')")
    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);

        return "redirect:/";
    }
}
