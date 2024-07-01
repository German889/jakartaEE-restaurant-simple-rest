package com.aston.second_task.service.implementations;

import com.aston.second_task.entity.User;
import com.aston.second_task.repository.DAO.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(userDAO.save(user)).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertNotNull(savedUser);
        assertEquals(user, savedUser);
        verify(userDAO, times(1)).save(user);
    }

    @Test
    void findUserById() {
        Integer id = 1;
        User user = new User();
        user.setId(id);

        when(userDAO.findById(id)).thenReturn(user);

        User foundUser = userService.findUserById(id);

        assertNotNull(foundUser);
        assertEquals(id, foundUser.getId());
        verify(userDAO, times(1)).findById(id);
    }

    @Test
    void findAllUsers() {
        User user1 = new User();
        user1.setId(1);
        User user2 = new User();
        user2.setId(2);

        List<User> users = Arrays.asList(user1, user2);

        when(userDAO.findAll()).thenReturn(users);

        List<User> foundUsers = userService.findAllUsers();

        assertNotNull(foundUsers);
        assertEquals(2, foundUsers.size());
        verify(userDAO, times(1)).findAll();
    }

    @Test
    void updateUser() {
        Integer id = 1;
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(userDAO.update(user)).thenReturn(user);

        User updatedUser = userService.updateUser(user, id);

        assertNotNull(updatedUser);
        assertEquals(id, updatedUser.getId());
        verify(userDAO, times(1)).update(user);
    }

    @Test
    void deleteUser() {
        Integer id = 1;

        when(userDAO.delete(id)).thenReturn(id);

        Integer deletedId = userService.deleteUser(id);

        assertNotNull(deletedId);
        assertEquals(id, deletedId);
        verify(userDAO, times(1)).delete(id);
    }

    @Test
    void getUserID() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        Integer id = 1;

        when(userDAO.getId(user)).thenReturn(id);

        Integer foundId = userService.getUserID(user);

        assertNotNull(foundId);
        assertEquals(id, foundId);
        verify(userDAO, times(1)).getId(user);
    }
}