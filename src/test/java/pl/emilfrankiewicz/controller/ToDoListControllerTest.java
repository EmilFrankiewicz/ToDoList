package pl.emilfrankiewicz.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.emilfrankiewicz.list.ToDoList;
import pl.emilfrankiewicz.list.ToDoListController;
import pl.emilfrankiewicz.list.ToDoListDTO;
import pl.emilfrankiewicz.list.ToDoListService;
import pl.emilfrankiewicz.user.User;
import pl.emilfrankiewicz.user.UserService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@ExtendWith(MockitoExtension.class)
public class ToDoListControllerTest {

	@Mock
	private ToDoListService toDoListService;

	@InjectMocks
	private ToDoListController toDoListController;

	private MockMvc mockMvc;

	@Mock
	private Principal principal;

	private ToDoListDTO toDoListDTO;

	private ToDoList toDoList;
	@Mock
	private UserService userService;

	private static final String API_URL = "/api/ToDoList/";

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(toDoListController).build();
		toDoListDTO = new ToDoListDTO();
		toDoList = new ToDoList();
	}

	@Test
	public void toDoListCanBeSavedWhenNameLengthIsValid() throws Exception {

		// given
		toDoListDTO.setName("Shopping");
		given(principal.getName()).willReturn("user123");
		given(userService.getUserByUsername(("user123"))).willReturn(new User("user123", "qwerty012345"));

		// when
		MockHttpServletResponse response = mockMvc
				.perform(post(API_URL).principal(principal).contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(toDoListDTO)).accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
	}

	@Test
	public void toDoListCantBeSavedWhenNameLengthIsInvalid() throws Exception {

		// given
		toDoListDTO.setName("xd");
		given(principal.getName()).willReturn("user123");

		// when
		MockHttpServletResponse response = mockMvc
				.perform(post(API_URL).principal(principal).contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(toDoListDTO)).accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

	}

	@Test
	public void getAllToDoLists() throws Exception {

		// given
		toDoList.setId(1);
		toDoList.setName("Shopping");

		ToDoList toDoList2 = new ToDoList();
		toDoList2.setId(2);
		toDoList2.setName("Cleaning");

		List<ToDoList> todoLists = Arrays.asList(toDoList, toDoList2);
		given(principal.getName()).willReturn("user123");
		given(toDoListService.getAllByToDoListOwner(("user123"))).willReturn(todoLists);

		// when
		MockHttpServletResponse response = mockMvc
				.perform(get(API_URL).principal(principal).accept(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(new ObjectMapper().writeValueAsString(todoLists));

	}

	@Test
	public void getSpecificTodoList() throws Exception {

		// given
		toDoList.setId(2);
		toDoList.setName("Cleaning");

		given(principal.getName()).willReturn("user123");
		given(toDoListService.getByIdAndToDoListOwner(2L, ("user123"))).willReturn(toDoList);

		// when
		MockHttpServletResponse response = mockMvc
				.perform(get(API_URL + "2").principal(principal).accept(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(new ObjectMapper().writeValueAsString(toDoList));

	}

	@Test
	public void toDoListNameCanBeChangedToNewCorrectName() throws Exception {

		// given
		toDoList.setId(2);
		toDoList.setName("eaning");

		toDoListDTO.setName("Cleaning");

		given(principal.getName()).willReturn("user123");
		given(toDoListService.getByIdAndToDoListOwner(2L, ("user123"))).willReturn(toDoList);

		// when
		MockHttpServletResponse response = mockMvc
				.perform(put(API_URL + "2").principal(principal).contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(toDoListDTO)).accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
	}

	@Test
	public void toDoListNameCantBeChangedToNullValue() throws Exception {

		// given
		toDoListDTO.setName(null);

		given(principal.getName()).willReturn("user123");

		// when
		MockHttpServletResponse response = mockMvc
				.perform(put(API_URL + "1").principal(principal).contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(toDoListDTO)).accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void todoListCanBeDeleted() throws Exception {

		// given
		toDoList.setId(1);
		toDoList.setName("Shopping");

		given(principal.getName()).willReturn("user123");
		given(toDoListService.getByIdAndToDoListOwner(1L, "user123")).willReturn(toDoList);
		given(toDoListService.delete(toDoList)).willReturn(true);

		// when
		MockHttpServletResponse response = mockMvc
				.perform(delete(API_URL + "1").principal(principal).accept(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
	}
}
