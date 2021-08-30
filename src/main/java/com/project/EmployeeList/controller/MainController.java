package com.project.EmployeeList.controller;


import com.project.EmployeeList.dto.EmployeeDTO;
import com.project.EmployeeList.entity.Employee;
import com.project.EmployeeList.entity.Role;
import com.project.EmployeeList.mapper.EmployeeMapper;
import com.project.EmployeeList.service.EmployeeService;
import com.project.EmployeeList.util.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
//        if (employee.getRole().contains(Role.DIRECTOR)) {
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

    @GetMapping("/addNewEmployee")
    public String addNewEmployee(Model model) {
        EmployeeDTO employee = new EmployeeDTO();
        model.addAttribute("employee", employee);

        return "employee-info";
    }

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
        employeeService.saveEmployee(employee);

        return "redirect:/";
    }

    @PostMapping("/updateEmployee")
    public String updateEmployee(
            @ModelAttribute("employee") @Valid EmployeeDTO employeeDTO,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errors);

            return "employee-update";
        }

        Employee employee = employeeService.getEmployee(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setSurname(employeeDTO.getSurname());
        employee.setDepartment(employeeDTO.getDepartment());
        employee.setSalary(employeeDTO.getSalary());
        employeeService.updateEmployee(employee);

        return "redirect:/";
    }

    @GetMapping("/updateEmployee/{id}")
    public String updateEmployee(Model model, @PathVariable Long id) {
        Employee employee = employeeService.getEmployee(id);
        model.addAttribute("employee", employeeMapper.toDTO(employee));

        return "employee-update";
    }

    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable Long id) {
//        Employee employee = employeeService.getEmployee(id);
        employeeService.deleteEmployee(id);

        return "redirect:/";
    }
}
