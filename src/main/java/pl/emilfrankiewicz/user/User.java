package pl.emilfrankiewicz.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import pl.emilfrankiewicz.list.ToDoList;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String username;
	
	private String password;

	@JsonManagedReference
	@OneToMany(mappedBy = "toDoListOwner", cascade = CascadeType.ALL)
	private List<ToDoList> toDoList;

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private Set<UserRole> roles;

	public void addToDoList(ToDoList toDoList) {
		toDoList.setToDoListOwner(this);
		getToDoList().add(toDoList);
	}

	public User() {
		setRoles(new HashSet<>());
		setToDoList(new ArrayList<>());
	}

	public User(String username, String password) {
		this();
		this.username = username;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Set<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}

}
