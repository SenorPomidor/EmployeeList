package com.project.EmployeeList.service;

import com.project.EmployeeList.entity.Task;
import com.project.EmployeeList.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {
    private TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public List<Task> showAllTasks(Long id) {
        return taskRepository.getAllEmployeeTasks(id);
    }

    @Transactional
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }
}
