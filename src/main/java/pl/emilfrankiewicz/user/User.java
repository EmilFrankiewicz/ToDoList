package pl.emilfrankiewicz.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import pl.emilfrankiewicz.list.ToDoList;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	private String password;

	@OneToMany(mappedBy = "toDoListOwner", cascade = CascadeType.ALL)
	private List<ToDoList> toDoList;

	public User() {
		setToDoList(new ArrayList<>());
	}

	public User(String username, String password) {
		this();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<ToDoList> getToDoList() {
		return toDoList;
	}

	public void setToDoList(List<ToDoList> toDoList) {
		this.toDoList = toDoList;
	}

}
