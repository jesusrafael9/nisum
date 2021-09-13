package com.nisum.controller;

import com.nisum.configuration.DefaultUserDetailsService;
import com.nisum.model.User;
import com.nisum.repository.UserRepository;
import com.nisum.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private DefaultUserDetailsService defaultUserDetailsService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void when_test_GetUser() throws Exception {

        User user = new User();
        user.setPassword("password");
        user.setName("name");
        Mockito.when(userService.getUser(Mockito.any())).thenReturn(user);

        mvc.perform(get("/users/2cb1c220-14ab-11ec-82a8-0242ac130003").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("{\"id\":null,\"email\":null,\"name\":\"name\",\"password\":\"password\"," +
                        "\"created\":null,\"modified\":null,\"lastLogin\":null,\"token\"" +
                        ":null,\"phones\":[],\"active\":false}"));
    }

    @Test
    public void when_test_PostUser() throws Exception {

        User user = new User();
        user.setPassword("password");
        user.setName("name");
        user.setName("email@email.com");
        Mockito.when(userService.createUser(Mockito.any())).thenReturn(user);

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\t\"name\": \"name\",\n" +
                        "\t\"password\": \"password\",\n" +
                        "\t\"email\": \"email@email.com\"\n"+
                        "}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string("{\"id\":null,\"email\":null,\"name\":\"email@email.com\",\"password\":\"password\"," +
                        "\"created\":null,\"modified\":null,\"lastLogin\"" +
                        ":null,\"token\":null,\"phones\":[],\"active\":false}"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void when_test_Get_UserNotFound() throws Exception {
        Mockito.when(userService.getUser(Mockito.any())).thenThrow(new NoSuchElementException("The user not exists"));

        mvc.perform(get("/users/2cb1c220-14ab-11ec-82a8-0242ac130003").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}