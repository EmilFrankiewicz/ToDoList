package pl.emilfrankiewicz.list;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import pl.emilfrankiewicz.task.Task;
import pl.emilfrankiewicz.user.User;

@Entity
public class ToDoList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private Date creationDate;

	private String name;

	@OneToMany(mappedBy = "toDoListId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Task> tasks;

	@ManyToOne
	@JoinColumn(name = "to_do_list_owner")
	private User toDoListOwner;

	public ToDoList() {
		setTasks(new HashSet<>());
		setCreationDate(new Date());
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	public User getToDoListOwner() {
		return toDoListOwner;
	}

	public void setToDoListOwner(User toDoListOwner) {
		this.toDoListOwner = toDoListOwner;
	}

}
