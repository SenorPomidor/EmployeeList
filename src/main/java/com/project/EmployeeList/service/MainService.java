package com.project.EmployeeList.service;

import com.project.EmployeeList.dto.EmployeeDTO;
import com.project.EmployeeList.dto.TaskDTO;
import com.project.EmployeeList.entity.Employee;
import com.project.EmployeeList.entity.Role;
import com.project.EmployeeList.entity.Task;
import com.project.EmployeeList.mapper.EmployeeMapper;
import com.project.EmployeeList.mapper.TaskMapper;
import com.project.EmployeeList.repository.EmployeeRepository;
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

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MainService {
    private EmployeeService employeeService;

    private PasswordEncoder passwordEncoder;

    private EmployeeMapper employeeMapper;

    private TaskService taskService;

    private TaskMapper taskMapper;

    @Autowired
    public MainService(
            EmployeeService employeeService,
            PasswordEncoder passwordEncoder,
            EmployeeMapper employeeMapper,
            TaskService taskService,
            TaskMapper taskMapper
    ) {
        this.employeeService = employeeService;
        this.passwordEncoder = passwordEncoder;
        this.employeeMapper = employeeMapper;
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @Transactional
    public String showAllEmployees(Model model, Employee employee) {

        if (employee == null) { //ToDo: find another way
            return "redirect:/login";
        } else if (employee.getAuthorities().contains(Role.DIRECTOR)) {

            model.addAttribute("id", employee.getId());
            model.addAttribute("name", employee.getName());
            model.addAttribute("surname", employee.getSurname());

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

    @Transactional
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

    @Transactional
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

    @Transactional
    public String addNewEmployee(Model model) {
        EmployeeDTO employee = new EmployeeDTO();
        model.addAttribute("employee", employee);

        return "employee-info";
    }

    @Transactional
    public String saveEmployee(
            EmployeeDTO employeeDTO,
            BindingResult bindingResult,
            Model model,
            Employee employeeAuth
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errors);

            return "employee-info";
        }

        Optional<Employee> emp = employeeService.getEmployeeByLogin(employeeDTO.getLogin());

        if (emp.isEmpty()) {
            employeeService.saveEmployee(employeeDTO, employeeAuth);

            return "redirect:/";
        } else {
            model.addAttribute("employeeExistsError", "User with this login exists!");

            return "employee-info";
        }
    }

    @Transactional
    public String updateEmployee(
            EmployeeDTO employeeDTO,
            BindingResult bindingResult,
            Model model
    ) {

        Map<String, String> errors = ControllerUtils.getErrors(bindingResult);

        if (errors.containsKey("passwordError") && errors.containsKey("loginError")) {
            errors.remove("passwordError");
            errors.remove("loginError");
        }

        if (!errors.values().isEmpty()) {
            model.mergeAttributes(errors);

            return "employee-update";
        }

        model.addAttribute("ps", "P.S. If you updated YOUR data, you can see it after reauthorization!");

        Employee employee = employeeService.getEmployee(employeeDTO.getId());

        if (!employeeService.isEmployeeExist(employeeDTO.getLogin()) || employee.getLogin().equals(employeeDTO.getLogin())) {
            employee.setName(employeeDTO.getName());
            employee.setSurname(employeeDTO.getSurname());
            employee.setDepartment(employeeDTO.getDepartment());
            employee.setSalary(employeeDTO.getSalary());
            if (!employeeDTO.getLogin().isEmpty() && !employeeDTO.getPassword().isEmpty()) {
                employee.setLogin(employeeDTO.getLogin());
                if (!employee.getPassword().equals(employeeDTO.getPassword())) {
                    employee.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));
                }
            }
            employeeService.updateEmployee(employee);

            model.addAttribute("employeeSuccessfullySaved", "Employee data has been successfully saved!");
        } else {
            model.addAttribute("employeeExistsError", "User with this login exists!");
        }
        return "employee-update";
    }

    @Transactional
    public String updateEmployee(Model model, Long id) {
        Employee employee = employeeService.getEmployee(id);
        model.addAttribute("employee", employeeMapper.toDTO(employee));

        return "employee-update";
    }

    @Transactional
    public String showAllTasks(Long id, Model model) {
        List<Task> tasks = taskService.showAllTasks(id);

        if (tasks.isEmpty()) {
            model.addAttribute("noTasks", "Employee has no tasks!");
        }

        List<TaskDTO> dtos = tasks.stream()
                .map(t -> taskMapper.toDTO(t))
                .collect(Collectors.toList());
        model.addAttribute("allTasks", dtos);
        model.addAttribute("id", id);

        return "employee-tasks";
    }

    @Transactional
    public String addNewTask(Long id, Model model) {
        Employee employee = employeeService.getEmployee(id);
        TaskDTO task = new TaskDTO();
        model.addAttribute("task", task);
        model.addAttribute("emp", employee);

        return "add-task";
    }

    @Transactional
    public String saveNewTask(
            TaskDTO taskDTO,
            EmployeeDTO employeeDTO,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errors);

//            return "add-task";
        }

        model.addAttribute("taskSuccessfullyAdded", "Task has been successfully added!");

        Employee employee = employeeService.getEmployee(employeeDTO.getId());
        taskDTO.setEmployee_id(employee);

        Task task = taskMapper.toEntity(taskDTO);

        taskService.saveTask(task);

//        return "redirect:/addNewTask/" + employeeDTO.getId();
        return "add-task";
    }

    @Transactional
    public String employeeTasks(Model model, Employee employee) {
        List<Task> tasks = employeeService.getAllTasks(employee.getId());

        if (tasks.isEmpty()) {
            model.addAttribute("noTasks", "You don't have tasks!");
        }

        List<TaskDTO> dtos = tasks.stream()
                .map(t -> taskMapper.toDTO(t))
                .collect(Collectors.toList());
        model.addAttribute("allTasks", dtos);

        return "employee-show-tasks";
    }

    @Transactional
    public String deleteEmployee(Long id) {
        employeeService.deleteEmployee(id);

        return "redirect:/";
    }
}
