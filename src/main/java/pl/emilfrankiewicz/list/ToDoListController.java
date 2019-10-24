package pl.emilfrankiewicz.list;

import java.security.Principal;
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
import pl.emilfrankiewicz.exceptions.ResourceDoesNotExistException;
import pl.emilfrankiewicz.user.User;
import pl.emilfrankiewicz.user.UserService;

@RestController
public class ToDoListController {

	private ToDoListService toDoListService;
	private UserService userService;

	@Autowired
	public ToDoListController(ToDoListService toDoListService, UserService userService) {
		this.toDoListService = toDoListService;
		this.userService = userService;
	}

	@RequestMapping(value = "/api/ToDoList", method = RequestMethod.POST)
	public ResponseEntity<ToDoList> addNewList(Principal principal, @Valid @RequestBody ToDoListDTO todoListDTO,
			BindingResult result) {

		String username = principal.getName();

		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError error : errors) {
				System.out.println(error.getObjectName() + " " + error.getDefaultMessage());

			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		User user = userService.getUserByUsername(username);

		ToDoList toDoList = new ToDoList();
		toDoList.setName(todoListDTO.getName());
		user.addToDoList(toDoList);
		userService.save(user);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@RequestMapping(value = "/api/ToDoList/{listId}", method = RequestMethod.GET)
	public ToDoList getUserList(Principal principal, @PathVariable("listId") Long id)
			throws ResourceDoesNotExistException {

		String username = principal.getName();
		ToDoList toDoList = toDoListService.getByIdAndToDoListOwner(id, username);
		if (toDoList == null) {
			throw new ResourceDoesNotExistException("List of given ID not found");
		}
		return toDoList;
	}

	@RequestMapping(value = "/api/ToDoList", method = RequestMethod.GET)
	public List<ToDoList> getAllUserLists(Principal principal) {
		String username = principal.getName();
		List<ToDoList> listToGet = toDoListService.getAllByToDoListOwner(username);
		return listToGet;
	}

	@RequestMapping(value = "/api/ToDoList/{listId}", method = RequestMethod.PUT)
	public ResponseEntity<ToDoList> updateToDoList(Principal principal, @Valid @RequestBody ToDoListDTO toDoListDTO,
			BindingResult result, @PathVariable("listId") Long id) throws ResourceDoesNotExistException {

		String username = principal.getName();

		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError error : errors) {
				System.out.println(error.getObjectName() + " " + error.getDefaultMessage());
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		ToDoList toDoList = toDoListService.getByIdAndToDoListOwner(id, username);
		if (toDoList == null) {
			throw new ResourceDoesNotExistException("List of given ID not found");
		}
		String newDoDoListName = toDoListDTO.getName();
		toDoList.setName(newDoDoListName);
		toDoListService.save(toDoList);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@RequestMapping(value = "/api/ToDoList/{listId}", method = RequestMethod.DELETE)
	public ResponseEntity<ToDoList> deleteUserList(Principal principal, @PathVariable("listId") Long id)
			throws ResourceDoesNotExistException {

		String username = principal.getName();

		ToDoList toDoList = toDoListService.getByIdAndToDoListOwner(id, username);
		if (toDoList == null) {
			throw new ResourceDoesNotExistException("List of given ID not found");
		}

		toDoListService.delete(toDoList);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}