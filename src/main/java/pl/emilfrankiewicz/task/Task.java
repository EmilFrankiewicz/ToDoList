package pl.emilfrankiewicz.task;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pl.emilfrankiewicz.list.ToDoList;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Date creationDate;

	private String description;

	private Boolean isActive;

	private int priority;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "to_do_list_id")
	private ToDoList toDoListId;

	public Task() {
		setCreationDate(new Date());
		setIsActive(true);
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public ToDoList getToDoListId() {
		return toDoListId;
	}

	public void setToDoListId(ToDoList toDoListId) {
		this.toDoListId = toDoListId;
	}
}
