package pl.emilfrankiewicz.list;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.emilfrankiewicz.user.User;
import pl.emilfrankiewicz.user.UserService;

@RestController
public class ToDoListController {

	private ToDoListRepositories toDoListRepositories;
	private UserService userService;

	@Autowired
	public ToDoListController(ToDoListRepositories toDoListRepositories, UserService userService) {
		this.toDoListRepositories = toDoListRepositories;
		this.userService = userService;
	}

	@RequestMapping(value = "/api/toDoList", method = RequestMethod.POST)
	public ResponseEntity addNewList(Principal principal, @Valid @RequestBody ToDoListDTO todoListDTO,
			BindingResult result) {

		String username = principal.getName();

		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError error : errors) {
				System.out.println(error.getObjectName() + " omg blad- " + error.getDefaultMessage());

			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		User user = userService.getUserRepository().findByUsername(username);

		ToDoList toDoList = new ToDoList();
		toDoList.setName(todoListDTO.getName());
		user.addToDoList(toDoList);
		userService.getUserRepository().save(user);

		return ResponseEntity.status(HttpStatus.CREATED).build();

	}

}
