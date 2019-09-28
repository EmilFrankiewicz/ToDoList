package pl.emilfrankiewicz.user;

import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public class UserDTO {

	@NotEmpty
	@Length(min = 2, max = 15)
	private String username;

	@NotEmpty
	@Length(min = 6)
	private String password;

	public UserDTO() {
	}

	public UserDTO(String username, String password) {
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

}
