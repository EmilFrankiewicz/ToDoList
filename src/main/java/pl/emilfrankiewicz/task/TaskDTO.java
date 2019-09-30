package pl.emilfrankiewicz.task;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public class TaskDTO {

	@NotEmpty
	@Length(min = 3, max = 100)
	private String description;

	@Min(value = 0)
	private int priority;

	public TaskDTO() {
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
}
