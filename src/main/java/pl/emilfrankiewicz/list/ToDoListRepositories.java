package pl.emilfrankiewicz.list;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoListRepositories extends JpaRepository<ToDoList, Long> {

}
