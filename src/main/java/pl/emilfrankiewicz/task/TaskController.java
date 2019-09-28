package pl.emilfrankiewicz.task;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {

	private TaskRepository taskRepository;

	@Autowired
	public TaskController(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	@RequestMapping(value = "/api/ToDoList", method = RequestMethod.POST)
	public ResponseEntity<Task> saveTask(@Valid @RequestBody Task task, BindingResult result) {
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError error : errors) {
				System.out.println(error.getObjectName() + " omg blad- " + error.getDefaultMessage());

			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		taskRepository.save(task);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@RequestMapping(value = "/api/ToDoList/tasks", method = RequestMethod.GET)
	public List<Task> getAllTask() {
		List<Task> setToGet = taskRepository.findAll();
		return setToGet;
	}

	@RequestMapping(value = "/api/ToDoList/tasks/{taskId}", method = RequestMethod.GET)
	public ResponseEntity<Task> getTask(@PathVariable("taskId") Long id) {
		if (taskRepository.getOne(id) == null) {
			return ResponseEntity.notFound().build();
		} else {
			Task taskToGet = taskRepository.getOne(id);

			return ResponseEntity.ok(taskToGet);
		}
	}
}
