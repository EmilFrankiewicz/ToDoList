package pl.emilfrankiewicz.list;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ToDoListService {

	private ToDoListRepository toDoListRepository;

	@Autowired
	public ToDoListService(ToDoListRepository toDoListRepository) {
		this.toDoListRepository = toDoListRepository;
	}

	public ToDoList getByIdAndToDoListOwner(Long id, String toDoListOwner) {
		return toDoListRepository.getByIdAndToDoListOwner_Username(id, toDoListOwner);
	}

	public ToDoList save(ToDoList toDoList) {
		return toDoListRepository.save(toDoList);
	}

	public List<ToDoList> getAllByToDoListOwner(String toDoListOwner) {
		return toDoListRepository.getAllByToDoListOwner_Username(toDoListOwner);
	}

	public Boolean delete(ToDoList toDoList) {
		Boolean verification;
		Long id = toDoList.getId();
		toDoListRepository.delete(toDoList);
		verification = !toDoListRepository.existsById(id);
		return verification;
	}
}
