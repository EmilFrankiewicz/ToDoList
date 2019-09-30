package pl.emilfrankiewicz.list;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoListRepository extends JpaRepository<ToDoList, Long> {

	ToDoList getByIdAndToDoListOwner_Username(Long id, String toDoListOwner);
	List<ToDoList> getAllByToDoListOwner_Username(String username);
}
