package com.project.EmployeeList.service;

import com.project.EmployeeList.dto.EmployeeDTO;
import com.project.EmployeeList.entity.Employee;
import com.project.EmployeeList.entity.Role;
import com.project.EmployeeList.mapper.EmployeeMapper;
import com.project.EmployeeList.util.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MainService {
    private EmployeeService employeeService;

    private PasswordEncoder passwordEncoder;

    private EmployeeMapper employeeMapper;

    @Autowired
    public MainService(
            EmployeeService employeeService,
            PasswordEncoder passwordEncoder,
            EmployeeMapper employeeMapper
    ) {
        this.employeeService = employeeService;
        this.passwordEncoder = passwordEncoder;
        this.employeeMapper = employeeMapper;
    }

    @Transactional
    public String showAllEmployees(Model model, Employee employee) {

        if (employee == null) { //ToDo: find another way
            return "redirect:/login";
        } else if (employee.getAuthorities().contains(Role.DIRECTOR)) {

            model.addAttribute("id", employee.getId());
            model.addAttribute("name", employee.getName());
            model.addAttribute("surname", employee.getSurname());

            model.addAttribute("director");

            List<Employee> employeeList = employeeService.getAllEmployees();
            List<EmployeeDTO> dtos = employeeList.stream()
                    .filter(e -> !e.getAuthorities().contains(Role.DIRECTOR))
                    .filter(e -> e.getDirector().getId().equals(employee.getId()))
                    .map(e -> employeeMapper.toDTO(e))
                    .collect(Collectors.toList());
            model.addAttribute("allEmps", dtos);

            //ToDo: add pagination

            return "all-employees";
        } else {
            return "employee-main";
        }
    }

    public String authorization(
            EmployeeDTO employeeDTO,
            Model model
    ) {

        Optional<Employee> employee = employeeService.getEmployeeByLogin(employeeDTO.getLogin());

        if (employeeDTO.getPassword().equals("") || employeeDTO.getLogin().equals("")) {
            model.addAttribute("dataError", "Login and password can't be empty!");
            return "login";
        }

        if (employee.isPresent() && passwordEncoder.matches(employeeDTO.getPassword(), employee.get().getPassword())) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    employee.get(),
                    null,
                    Collections.singleton(Role.DIRECTOR)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return "redirect:/";
        } else {
            model.addAttribute("data2Error", "Invalid login or password!");
            return "login";
        }
    }

    public String registration(
            EmployeeDTO employeeDTO,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errors);

            return "registration";
        }

        Optional<Employee> emp = employeeService.getEmployeeByLogin(employeeDTO.getLogin());

        if (emp.isEmpty()) {
            employeeService.saveDirector(employeeDTO);
        } else {
            model.addAttribute("employeeExistsError", "User with this login exists!");
            return "registration";
        }

        return "redirect:/";
    }

    public String addNewEmployee(Model model) {
        EmployeeDTO employee = new EmployeeDTO();
        model.addAttribute("employee", employee);

        return "employee-info";
    }

    public String saveEmployee(
            EmployeeDTO employeeDTO,
            Employee employeeAuth,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errors);

            return "employee-update";
        }

        employeeService.saveEmployee(employeeDTO, employeeAuth);

        return "redirect:/";
    }
    public String updateEmployee(
            EmployeeDTO employeeDTO,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errors);

            return "employee-update";
        }

        Optional<Employee> emp = employeeService.getEmployeeByLogin(employeeDTO.getLogin());

        if (emp.isEmpty()) {
            Employee employee = employeeService.getEmployee(employeeDTO.getId());
            employee.setName(employeeDTO.getName());
            employee.setSurname(employeeDTO.getSurname());
            employee.setDepartment(employeeDTO.getDepartment());
            employee.setSalary(employeeDTO.getSalary());
            employee.setLogin(employeeDTO.getLogin());
            employee.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
            employeeService.updateEmployee(employee);

            return "redirect:/";
        } else {
            model.addAttribute("employeeExistsError", "User with this login exists!");
            return "employee-update";
        }
    }

    public String updateEmployee(Model model, Long id) {
        Employee employee = employeeService.getEmployee(id);
        model.addAttribute("employee", employeeMapper.toDTO(employee));

        return "employee-update";
    }

    public String deleteEmployee(Long id) {
        employeeService.deleteEmployee(id);

        return "redirect:/";
    }
}
