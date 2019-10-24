package pl.emilfrankiewicz.task;

import java.security.Principal;
import java.util.List;
import java.util.Set;
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
import pl.emilfrankiewicz.exceptions.ResourceDoesNotExistException;
import pl.emilfrankiewicz.list.ToDoList;
import pl.emilfrankiewicz.list.ToDoListService;

@RestController
public class TaskController {

	private TaskService taskService;
	private ToDoListService todoListService;

	@Autowired
	public TaskController(TaskService taskService, ToDoListService todoListService) {
		this.taskService = taskService;
		this.todoListService = todoListService;
	}

	@RequestMapping(value = "/api/ToDoList/{listId}/tasks", method = RequestMethod.POST)
	public ResponseEntity<Task> addTaskToList(Principal principal, @PathVariable("listId") Long id,
			@Valid @RequestBody TaskDTO taskDTO, BindingResult result) {

		String username = principal.getName();

		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError error : errors) {
				System.out.println(error.getObjectName() + " " + error.getDefaultMessage());

			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		ToDoList toDoList = todoListService.getByIdAndToDoListOwner(id, username);

		if (toDoList == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		Task newTask = new Task();
		newTask.setDescription(taskDTO.getDescription());
		newTask.setPriority(taskDTO.getPriority());

		toDoList.addTask(newTask);
		todoListService.save(toDoList);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@RequestMapping(value = "/api/ToDoList/{listId}/tasks", method = RequestMethod.GET)
	public Set<Task> getAllTaskFromUserList(Principal principal, @PathVariable("listId") Long id) {
		String username = principal.getName();
		Set<Task> tasksToGet = taskService.getAllTasksOfUser(id, username);
		return tasksToGet;
	}

	@RequestMapping(value = "/api/ToDoList/{listId}/tasks/{taskId}", method = RequestMethod.GET)
	public Task getUserTask(Principal principal, @PathVariable("listId") Long listId,
			@PathVariable("taskId") Long taskId) throws ResourceDoesNotExistException {
		String username = principal.getName();
		Task taskToGet = taskService.getUserTask(listId, taskId, username);
		if (taskToGet == null) {
			throw new ResourceDoesNotExistException("Task of given ID not found");
		}
		return taskToGet;
	}

	@RequestMapping(value = "/api/ToDoList/{listId}/tasks/{taskId}", method = RequestMethod.PUT)
	public ResponseEntity<Task> updateTask(Principal principal, @PathVariable("listId") Long listId,
			@PathVariable("taskId") Long taskId, @Valid @RequestBody TaskDTO taskDTO, BindingResult result) {

		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError error : errors) {
				System.out.println(error.getObjectName() + " " + error.getDefaultMessage());

			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		String username = principal.getName();
		Task taskToGet = taskService.getUserTask(listId, taskId, username);
		if (taskToGet == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		taskToGet.setDescription(taskDTO.getDescription());
		taskToGet.setPriority(taskDTO.getPriority());
		taskService.saveTask(taskToGet);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@RequestMapping(value = "/api/ToDoList/{listId}/tasks/{taskId}", method = RequestMethod.DELETE)
	public ResponseEntity<Task> deleteTask(Principal principal, @PathVariable("listId") Long listId,
			@PathVariable("taskId") Long taskId) throws ResourceDoesNotExistException {
		String username = principal.getName();
		Task taskToDelete = taskService.getUserTask(listId, taskId, username);
		if (taskToDelete == null) {
			throw new ResourceDoesNotExistException("Task of given ID not found");
		}
		taskService.delete(taskToDelete);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
