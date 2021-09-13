package com.project.EmployeeList.mapper;

import com.project.EmployeeList.dto.TaskDTO;
import com.project.EmployeeList.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public TaskDTO toDTO(Task entity) {
        TaskDTO taskDTO = new TaskDTO();

        taskDTO.setId(entity.getId());
        taskDTO.setDescription(entity.getDescription());
        taskDTO.setComplete(entity.isComplete());
        taskDTO.setEmployee_id(entity.getEmployee());

        return taskDTO;
    }

    public Task toEntity(TaskDTO dto) {
        Task task = new Task();

        task.setId(dto.getId());
        task.setDescription(dto.getDescription());
        task.setComplete(dto.isComplete());
        task.setEmployee_id(dto.getEmployee());

        return task;
    }
}
