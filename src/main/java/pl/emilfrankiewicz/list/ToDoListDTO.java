package pl.emilfrankiewicz.list;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class ToDoListDTO {

	@NotEmpty // a moze nutnull?
	@Length(min = 3, max = 50)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
