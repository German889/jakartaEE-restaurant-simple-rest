package com.aston.second_task.controller;

import com.aston.second_task.dto.incoming.UserDTOInc;
import com.aston.second_task.dto.outgoing.UserDTOOut;
import com.aston.second_task.entity.User;

import com.aston.second_task.service.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUser() {
        Integer userId = 1;
        User user = new User();
        user.setId(userId);
        when(userService.findUserById(userId)).thenReturn(user);
        Response response = userController.getUser(userId);
        UserDTOOut userDTOOut = new UserDTOOut();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(userDTOOut, response.getEntity());
    }

    @Test
    void getAllUsers() {
        List<User> users = new ArrayList<>();
        User user1 = new User(); user1.setId(1);
        User user2 = new User(); user2.setId(2);
        users.add(user1);
        users.add(user2);
        when(userService.findAllUsers()).thenReturn(users);
        Response response = userController.getAllUsers();
        UserDTOOut userDTOOut1 = new UserDTOOut();
        UserDTOOut userDTOOut2 = new UserDTOOut();
        List<UserDTOOut> userDTOOuts = new ArrayList<>();
        userDTOOuts.add(userDTOOut1); userDTOOuts.add(userDTOOut2);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(userDTOOuts, response.getEntity());
    }

    @Test
    void saveUser() {
        User user = new User();
        when(userService.saveUser(user)).thenReturn(user);
        UserDTOInc userDTOInc = new UserDTOInc();
        Response response = userController.saveUser(userDTOInc);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(null, response.getEntity());
    }

    @Test
    void updateUser() {
        Integer userId = 1;
        User user = new User();
        user.setId(userId);
        when(userService.updateUser(user, userId)).thenReturn(user);
        UserDTOInc userDTOInc = new UserDTOInc();
        Response response = userController.updateUser(userDTOInc, userId);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(null, response.getEntity());
    }

    @Test
    void deleteUser() {
        Integer userId = 1;
        when(userService.deleteUser(userId)).thenReturn(userId);
        Response response = userController.deleteUser(userId);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(null, response.getEntity());
    }
}