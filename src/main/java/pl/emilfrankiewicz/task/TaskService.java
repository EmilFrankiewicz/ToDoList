package pl.emilfrankiewicz.task;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

	private TaskRepository taskRepository;

	@Autowired
	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	public Set<Task> getAllTasksOfUser(Long id, String taskOwner) {
		return taskRepository.getAllTasksOfUser(id, taskOwner);
	}

	public Task getUserTask(Long toDistId, Long taskId, String taskOwner) {
		return taskRepository.getUserTask(toDistId, taskId, taskOwner);
	}

	public Task saveTask(Task task) {
		return taskRepository.save(task);
	}

	public void deleteDatk(Task task) {
		taskRepository.delete(task);
	}
}
