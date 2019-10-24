package pl.emilfrankiewicz.task;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.assertj.core.api.Assertions.assertThat;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import pl.emilfrankiewicz.list.ToDoList;
import pl.emilfrankiewicz.list.ToDoListService;
import pl.emilfrankiewicz.user.UserService;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTask {

	@Mock
	private TaskService taskService;

	@InjectMocks
	private TaskController taskController;

	private MockMvc mockMvc;

	@Mock
	private ToDoListService todoListService;

	@Mock
	private Principal principal;

	private TaskDTO taskDTO;

	private Task task;
	@Mock
	private UserService userService;

	private static final String API_URL = "/api/ToDoList/1/tasks/";

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
		taskDTO = new TaskDTO();
		task = new Task();
	}

	@Test
	public void taskCanBeSavedWhenDescriptionLengthAndPriorityIsValidAndToDoListExist() throws Exception {

		// given
		ToDoList todoList = new ToDoList();
		todoList.setId(1L);
		todoList.setName("Shopping");

		taskDTO.setDescription("2x onion");
		taskDTO.setPriority(4);

		given(principal.getName()).willReturn("user123");
		given(todoListService.getByIdAndToDoListOwner(1L, "user123")).willReturn(todoList);

		// when
		MockHttpServletResponse response = mockMvc.perform(post(API_URL).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(taskDTO)).accept(MediaType.APPLICATION_JSON)
				.principal(principal)).andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
	}

	@Test
	public void getAllTasksFromToDoLists() throws Exception {

		// given
		task.setId(1L);
		task.setDescription("2x onion");
		task.setPriority(4);

		Task task2 = new Task();
		task2.setId(2L);
		task2.setDescription("5x onion rings chips");
		task2.setPriority(4);

		Set<Task> tasks = new HashSet<>();
		tasks.add(task);
		tasks.add(task2);

		given(principal.getName()).willReturn("user123");
		given(taskService.getAllTasksOfUser(1L, "user123")).willReturn(tasks);

		// when
		MockHttpServletResponse response = mockMvc
				.perform(get(API_URL).principal(principal).accept(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	public void taskCantBeAddedToToDoListIfDescriptionLengthIsInvalid() throws Exception {

		// given
		taskDTO.setDescription("xd");
		given(principal.getName()).willReturn("user123");

		// when
		MockHttpServletResponse response = mockMvc.perform(post(API_URL).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(taskDTO)).accept(MediaType.APPLICATION_JSON)
				.principal(principal)).andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

	}

	@Test
	public void taskDescriptionCantBeChangedToNullValue() throws Exception {

		// given
		taskDTO.setDescription(null);
		taskDTO.setPriority(1);

		given(principal.getName()).willReturn("user123");

		// when
		MockHttpServletResponse response = mockMvc
				.perform(put(API_URL + "1").principal(principal).contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(taskDTO)).accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void taskPriorityCantBeChangedToNegativeNumber() throws Exception {

		// given
		taskDTO.setDescription("2x onion");
		taskDTO.setPriority(-1);

		given(principal.getName()).willReturn("user123");

		// when
		MockHttpServletResponse response = mockMvc
				.perform(put(API_URL + "1").principal(principal).contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(taskDTO)).accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void taskCanBeDeleted() throws Exception {

		// given
		task.setId(1L);
		task.setDescription("2x onion");

		given(principal.getName()).willReturn("test_user");
		given(taskService.getUserTask(1L, 1L, "test_user")).willReturn(task);
		given(taskService.delete(task)).willReturn(true);

		// when
		MockHttpServletResponse response = mockMvc
				.perform(delete(API_URL + "1").principal(principal).accept(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
	}

}
