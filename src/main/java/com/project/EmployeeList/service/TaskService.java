package com.project.EmployeeList.service;

import com.project.EmployeeList.entity.Task;
import com.project.EmployeeList.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public Task getTask(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            return task.get();
        } else {
            throw new RuntimeException("Task " + id + " not found!");
        }
    }

    @Transactional
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    @Transactional
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Transactional
    public List<Task> getAllCompletedTasks(Long id) {
        return taskRepository.getAllCompletedTasks(id);
    }
}
