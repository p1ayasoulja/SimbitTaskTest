package com.example.mytaskmanagersimbirsofr.service;

import com.example.mytaskmanagersimbirsofr.entity.Project;
import com.example.mytaskmanagersimbirsofr.entity.Task;
import com.example.mytaskmanagersimbirsofr.repository.TaskRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TaskService {
    private final TaskRepo taskRepo;

    public TaskService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }
    /**
     *Процедура добавления новой задачи
     * @param title - описание задачи
     * @param author - автор задачи
     * @param performer - исполнитель задачи
     * @param id - идентификатор проекта задачи
     **/
    public void addTask(String title, String author, String performer, Project id) {
        Task task = new Task(title, author, performer, id);
        taskRepo.save(task);
        log.info("IN addTask - task: {} successfully added", task.getId());
    }
    /**
     *Процедура показа задач проекта
     * @param id - идентификатор проекта
     * @return список задач на проекте
     **/
    public List<Task> showTaskList(Project id) {

            return taskRepo.findByDashboard(id);

    }
    /**
     *Процедура сохранения проекта
     * @param task - сущность проекта
     **/
    public void saveTask(Task task) {
        taskRepo.save(task);
        log.info("IN taskEditSave - task: {} successfully saved", task.getId());
    }
    /**
     *Процедура получения задачи по ее идентификатору
     * @param id - идентификатор задачи
     * @return сущность задачи
     **/
    public Task getTaskById(Long id) {
        log.info("IN getTaskById - task: {} successfully found", id);
        return taskRepo.findById(id).get();
    }
    /**
     *Процедура удаления задачи по идентификатору
     * @param id - идентификатор задачи
     **/
    public void deleteTask(Long id) {
        log.info("IN deleteTask - task: {} successfully deleted", id);
        taskRepo.deleteById(id);
    }
    /**
     *Процедура сохранения измененной задачи
     * @param title - описание задачи
     * @param performer - идентификатор проекта
     * @param releaseVersion - версия релиза
     * @param id - индентификатор задачи
     **/
    public void taskEditSave(String title, String performer, String releaseVersion, Long id) {
        Task task = taskRepo.getById(id);
        if (!title.isEmpty()) {
            task.setTitle(title);
        }
        if (!performer.isEmpty()) {
            task.setPerformer(performer);
        }
        if (!releaseVersion.isEmpty()) {
            task.setReleaseVersion(releaseVersion);
        }
        saveTask(task);
    }
    /**
     *Процедура получения идентификатора проекта по идентификатору задачи
     * @param id - идентификатор задачи
     * @return идентификатор проекта
     **/
    public Long getTaskProjectId(Long id) {
        Task task = taskRepo.getById(id);
        Project project = task.getDashboard();
        log.info("IN getTaskProjectId - task project Id : {} successfully found", project.getId());
        return project.getId();

    }

}