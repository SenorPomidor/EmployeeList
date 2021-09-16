package com.project.EmployeeList.service;

import com.project.EmployeeList.dto.EmployeeDTO;
import com.project.EmployeeList.dto.TaskDTO;
import com.project.EmployeeList.entity.Employee;
import com.project.EmployeeList.entity.Role;
import com.project.EmployeeList.entity.Task;
import com.project.EmployeeList.mapper.EmployeeMapper;
import com.project.EmployeeList.mapper.TaskMapper;
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

        if (employee == null) {
            return "redirect:/login";
        }

        model.addAttribute("id", employee.getId());
        model.addAttribute("name", employee.getName());
        model.addAttribute("surname", employee.getSurname());

        if (employee.getAuthorities().contains(Role.DIRECTOR)) {
            List<Employee> employeeList = employeeService.getAllEmployees(employee.getId());

            if (employeeList.isEmpty()) {
                model.addAttribute("noEmployees", "There are no employees!");
            } else {
                List<EmployeeDTO> dtos = employeeList.stream()
                        .filter(e -> !e.getAuthorities().contains(Role.DIRECTOR))
                        .filter(e -> e.getDirector().getId().equals(employee.getId()))
                        .map(e -> employeeMapper.toDTO(e))
                        .collect(Collectors.toList());
                model.addAttribute("allEmps", dtos);

                List<Integer> allCompletedTasks = new ArrayList<>();
                for (Employee emp : employeeList) {
                    int countCompletedTasks = taskService.getAllCompletedTasks(emp.getId()).size();
                    allCompletedTasks.add(countCompletedTasks);
                }

                model.addAttribute("allCompletedTasks", allCompletedTasks);

                List<Integer> allTasks = new ArrayList<>();
                for (Employee emp : employeeList) {
                    int countTasks = taskService.showAllTasks(emp.getId()).size();
                    allTasks.add(countTasks);
                }

                model.addAttribute("allTasks", allTasks);
            }

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
            Model model,
            Employee employeeAuth
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

            if (employeeAuth.getId().equals(employeeDTO.getId())) {
                model.addAttribute("successfullySaved", "Your data has been successfully saved!");
                model.addAttribute("ps", "P.S. You can see your updated data on main page after reauthorization!");
            } else {
                model.addAttribute("successfullySaved", "Data about employee has been successfully saved!");
            }
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
    public String showAllTasks(Long id, Model model, Employee employee) {
        List<Task> tasks = taskService.showAllTasks(id);

        if (tasks.isEmpty()) {
            if (employee.getAuthorities().contains(Role.DIRECTOR)) {
                model.addAttribute("noTasks", "Employee has no tasks!");

                return "employee-tasks";
            } else {
                model.addAttribute("noTasks", "You don't have tasks!");

                return "employee-show-tasks";
            }
        } else {
            List<TaskDTO> dtos = tasks.stream()
                    .map(t -> taskMapper.toDTO(t))
                    .collect(Collectors.toList());
            model.addAttribute("allTasks", dtos);

            if (employee.getAuthorities().contains(Role.DIRECTOR)) {
                model.addAttribute("name", employeeService.getEmployee(id).getName());

                model.addAttribute("id", id);

                return "employee-tasks";
            } else {
                return "employee-show-tasks";
            }
        }
    }

    @Transactional
    public String addNewTask(Long id, Model model) {
        Employee employee = employeeService.getEmployee(id);
        TaskDTO task = new TaskDTO();
        model.addAttribute("task", task);
        model.addAttribute("employee", employee);

        return "add-task";
    }

    @Transactional
    public String saveTask(
            TaskDTO taskDTO,
            BindingResult bindingResult,
            Model model,
            EmployeeDTO employeeDTO
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errors);

            return "add-task";
        }

        model.addAttribute("taskSuccessfullyAdded", "Task has been successfully added!");

        Employee employee = employeeService.getEmployee(employeeDTO.getId());
        taskDTO.setEmployee_id(employee);

        Task task = taskMapper.toEntity(taskDTO);

        taskService.saveTask(task);

        return "add-task";
    }

    @Transactional
    public String updateTask(Long id, Model model) {
        Task task = taskService.getTask(id);
        model.addAttribute("task", taskMapper.toDTO(task));
        model.addAttribute("id", task.getEmployee().getId());

        return "task-update";
    }

    @Transactional
    public String updateTask(
            TaskDTO taskDTO,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errors);

            return "task-update";
        }

        model.addAttribute("taskSuccessfullyUpdated", "Task has been successfully updated!");

        Task task = taskService.getTask(taskDTO.getId());
        task.setDescription(taskDTO.getDescription());

        taskService.saveTask(task);

        model.addAttribute("task", taskMapper.toDTO(task));
        model.addAttribute("id", task.getEmployee().getId());

        return "task-update";
    }

    @Transactional
    public String returnTask(Long id) {
        Task task = taskService.getTask(id);
        Employee employee = employeeService.getEmployee(task.getEmployee().getId());
        task.setComplete(!task.isComplete());

        taskService.saveTask(task);

        return "redirect:/tasksList/" + employee.getId();
    }

    @Transactional
    public String deleteEmployee(Long id) {
        employeeService.deleteEmployee(id);

        return "redirect:/";
    }

    @Transactional
    public String deleteTask(Long id) {
        Employee employee = employeeService.getEmployee(taskService.getTask(id).getEmployee().getId());
        taskService.deleteTask(id);

        return "redirect:/tasksList/" + employee.getId();
    }
}
