package com.project.EmployeeList.controller;


import com.project.EmployeeList.dto.EmployeeDTO;
import com.project.EmployeeList.entity.Employee;
import com.project.EmployeeList.mapper.EmployeeMapper;
import com.project.EmployeeList.service.EmployeeService;
import com.project.EmployeeList.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class MainController {

    private final MainService mainService;

    private final EmployeeService employeeService;

    private final EmployeeMapper employeeMapper;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MainController(EmployeeService employeeService, EmployeeMapper employeeMapper, PasswordEncoder passwordEncoder, MainService mainService) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
        this.passwordEncoder = passwordEncoder;
        this.mainService = mainService;
    }

    @GetMapping("/")
    public String showAllEmployees(Model model, @AuthenticationPrincipal Employee employee) {
        return mainService.showAllEmployees(model, employee);
    }

    @GetMapping("/login")
    public String login() { return "login"; }

    @PostMapping("/login")
    public String authorization(
            @ModelAttribute("employee") EmployeeDTO employeeDTO,
            Model model
    ) {
        return mainService.authorization(employeeDTO, model);
    }

    @GetMapping("/registration")
    public String reg() { return "registration"; }

    @PostMapping("/registration")
    public String registration(
            @ModelAttribute("employee") @Valid EmployeeDTO employeeDTO,
            BindingResult bindingResult,
            Model model
    ) {
        return mainService.registration(employeeDTO, bindingResult, model);
    }

    @PreAuthorize("hasAuthority('DIRECTOR')")
    @GetMapping("/addNewEmployee")
    public String addNewEmployee(Model model) {
        return mainService.addNewEmployee(model);
    }

    @PreAuthorize("hasAuthority('DIRECTOR')")
    @PostMapping("/saveEmployee")
    public String saveEmployee(
            @ModelAttribute("employee") @Valid EmployeeDTO employeeDTO,
            @AuthenticationPrincipal Employee employeeAuth,
            BindingResult bindingResult,
            Model model
    ) {
        return mainService.saveEmployee(employeeDTO, employeeAuth, bindingResult, model);
    }

    @PreAuthorize("hasAuthority('DIRECTOR')")
    @PostMapping("/updateEmployee")
    public String updateEmployee(
            @ModelAttribute("employee") @Valid EmployeeDTO employeeDTO,
            BindingResult bindingResult,
            Model model
    ) {
        return mainService.updateEmployee(employeeDTO, bindingResult, model);
    }

    @PreAuthorize("hasAuthority('DIRECTOR')")
    @GetMapping("/updateEmployee/{id}")
    public String updateEmployee(Model model, @PathVariable Long id) {
        return mainService.updateEmployee(model, id);
    }

    @PreAuthorize("hasAuthority('DIRECTOR')")
    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        return mainService.deleteEmployee(id);
    }
}
