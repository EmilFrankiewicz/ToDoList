package pl.emilfrankiewicz.task;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	@Query("Select t from Task t, User u, ToDoList tdl  " + 
			"            where t.toDoListId=tdl.id " + 
			"            and tdl.toDoListOwner=u.id " + 
			"            and tdl.id=:tdId" + 
			"			 and u.username=:username")
	Set<Task> getAllTasksOfUser(@Param("tdId") Long tdId,
            @Param("username") String username);
	
	
	
	
	@Query("Select t from Task t, User u, ToDoList tdl  " + 
			"            where t.toDoListId.id=:tdId " + 
			"            and tdl.toDoListOwner=u.id " + 
			"            and t.id=:taskId " + 
			"			 and u.username=:username")
	Task getUserTask(@Param("tdId") Long toDistId, @Param("taskId") Long taskId, @Param("username") String taskOwner);
}
