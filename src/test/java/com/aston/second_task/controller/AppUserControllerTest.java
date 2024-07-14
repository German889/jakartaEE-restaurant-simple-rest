package com.aston.second_task.controller;

import com.aston.second_task.dto.incoming.UserDTOInc;
import com.aston.second_task.dto.outgoing.UserDTOOut;
import com.aston.second_task.entity.AppUser;
import com.aston.second_task.exceptions.*;
import com.aston.second_task.mapper.UserMapper;
import com.aston.second_task.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppUserControllerTest {

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
        AppUser appUser = new AppUser();
        appUser.setId(userId);
        when(userService.findUserById(userId)).thenReturn(appUser);
        Response response = userController.getUser(userId);
        UserDTOOut userDTOOut = new UserDTOOut();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(userDTOOut, response.getEntity());
    }

    @Test
    void getUser_NotFound() {
        Integer userId = 1;
        when(userService.findUserById(userId)).thenThrow(new ElementNotFoundExceptions("User not found"));
        Response response = userController.getUser(userId);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals("User not found", response.getEntity());
    }

    @Test
    void getUser_InternalServerError() {
        Integer userId = 1;
        when(userService.findUserById(userId)).thenThrow(new RuntimeException("Some unexpected error"));
        Response response = userController.getUser(userId);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    @Test
    void getAllUsers() {
        List<AppUser> appUsers = new ArrayList<>();
        AppUser appUser1 = new AppUser(); appUser1.setId(1);
        AppUser appUser2 = new AppUser(); appUser2.setId(2);
        appUsers.add(appUser1);
        appUsers.add(appUser2);
        when(userService.findAllUsers()).thenReturn(appUsers);
        Response response = userController.getAllUsers();
        UserDTOOut userDTOOut1 = new UserDTOOut();
        UserDTOOut userDTOOut2 = new UserDTOOut();
        List<UserDTOOut> userDTOOuts = new ArrayList<>();
        userDTOOuts.add(userDTOOut1); userDTOOuts.add(userDTOOut2);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(userDTOOuts, response.getEntity());
    }
    @Test
    void getAllUsers_NotFound() {
        when(userService.findAllUsers()).thenThrow(new ElementsNotFoundException("No users found"));
        Response response = userController.getAllUsers();
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals("No users found", response.getEntity());
    }

    @Test
    void getAllUsers_InternalServerError() {
        when(userService.findAllUsers()).thenThrow(new RuntimeException("Some unexpected error"));
        Response response = userController.getAllUsers();
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    @Test
    void saveUser() {
        AppUser appUser = new AppUser();
        when(userService.saveUser(appUser)).thenReturn(appUser);
        UserDTOInc userDTOInc = new UserDTOInc();
        Response response = userController.saveUser(userDTOInc);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(null, response.getEntity());
    }
    @Test
    void saveUser_ElementNotSaved() {
        UserDTOInc userDTOInc = new UserDTOInc();
        AppUser appUser = UserMapper.INSTANCE.userDTOIncToUser(userDTOInc);
        when(userService.saveUser(appUser)).thenThrow(new ElementNotSavedException("User not saved"));
        Response response = userController.saveUser(userDTOInc);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals("User not saved", response.getEntity());
    }

    @Test
    void saveUser_InternalServerError() {
        UserDTOInc userDTOInc = new UserDTOInc();
        AppUser appUser = UserMapper.INSTANCE.userDTOIncToUser(userDTOInc);
        when(userService.saveUser(appUser)).thenThrow(new RuntimeException("Some unexpected error"));
        Response response = userController.saveUser(userDTOInc);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    @Test
    void updateUser() {
        Integer userId = 1;
        AppUser appUser = new AppUser();
        appUser.setId(userId);
        when(userService.updateUser(appUser, userId)).thenReturn(appUser);
        UserDTOInc userDTOInc = new UserDTOInc();
        Response response = userController.updateUser(userDTOInc, userId);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(null, response.getEntity());
    }
    @Test
    void updateUser_ElementNotUpdated() {
        Integer userId = 1;
        UserDTOInc userDTOInc = new UserDTOInc();
        AppUser appUser = UserMapper.INSTANCE.userDTOIncToUser(userDTOInc);
        when(userService.updateUser(appUser, userId)).thenThrow(new ElementNotUpdatedException("User not updated"));
        Response response = userController.updateUser(userDTOInc, userId);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals("User not updated", response.getEntity());
    }

    @Test
    void updateUser_InternalServerError() {
        Integer userId = 1;
        UserDTOInc userDTOInc = new UserDTOInc();
        AppUser appUser = UserMapper.INSTANCE.userDTOIncToUser(userDTOInc);
        when(userService.updateUser(appUser, userId)).thenThrow(new RuntimeException("Some unexpected error"));
        Response response = userController.updateUser(userDTOInc, userId);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    @Test
    void deleteUser() {
        Integer userId = 1;
        when(userService.deleteUser(userId)).thenReturn(userId);
        Response response = userController.deleteUser(userId);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(null, response.getEntity());
    }
    @Test
    void deleteUser_ElementNotDeleted() {
        Integer userId = 1;
        when(userService.deleteUser(userId)).thenThrow(new ElementNotDeletedException("User not deleted"));
        Response response = userController.deleteUser(userId);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals("User not deleted", response.getEntity());
    }

    @Test
    void deleteUser_InternalServerError() {
        Integer userId = 1;
        when(userService.deleteUser(userId)).thenThrow(new RuntimeException("Some unexpected error"));
        Response response = userController.deleteUser(userId);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }
}