package com.aston.second_task.service.implementations;

import com.aston.second_task.dao.UserDAO;
import com.aston.second_task.entity.AppUser;
import com.aston.second_task.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppUserServiceImplTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserServiceImpl userService;


    private AppUser appUser;

    @BeforeEach
    void setUp() {
        appUser = new AppUser();
        appUser.setId(1);
        appUser.setEmail("test@example.com");
        appUser.setPassword("password");
    }

    @Test
    void saveUser() {
        when(userDAO.save(appUser)).thenReturn(appUser);

        AppUser savedUser = userService.saveUser(appUser);

        assertNotNull(savedUser);
        assertEquals(appUser, savedUser);
        verify(userDAO, times(1)).save(appUser);
    }

    @Test
    void saveUser_throwsElementNotSavedException() {
        when(userDAO.save(appUser)).thenThrow(new ElementNotSavedException("User not saved"));

        assertThrows(ElementNotSavedException.class, () -> userService.saveUser(appUser));
        verify(userDAO, times(1)).save(appUser);
    }

    @Test
    void findUserById() {
        when(userDAO.findById(1)).thenReturn(appUser);

        AppUser foundUser = userService.findUserById(1);

        assertNotNull(foundUser);
        assertEquals(appUser, foundUser);
        verify(userDAO, times(1)).findById(1);
    }

    @Test
    void findUserById_throwsElementNotFoundExceptions() {
        when(userDAO.findById(1)).thenThrow(new ElementNotFoundExceptions("User not found"));

        assertThrows(ElementNotFoundExceptions.class, () -> userService.findUserById(1));
        verify(userDAO, times(1)).findById(1);
    }

    @Test
    void findAllUsers() {
        List<AppUser> users = Arrays.asList(appUser);
        when(userDAO.findAll()).thenReturn(users);

        List<AppUser> foundUsers = userService.findAllUsers();

        assertNotNull(foundUsers);
        assertEquals(users, foundUsers);
        verify(userDAO, times(1)).findAll();
    }

    @Test
    void findAllUsers_throwsElementsNotFoundException() {
        when(userDAO.findAll()).thenThrow(new ElementsNotFoundException("Users not found"));

        assertThrows(ElementsNotFoundException.class, () -> userService.findAllUsers());
        verify(userDAO, times(1)).findAll();
    }

    @Test
    void updateUser() {
        when(userDAO.update(appUser)).thenReturn(appUser);

        AppUser updatedUser = userService.updateUser(appUser, 1);

        assertNotNull(updatedUser);
        assertEquals(appUser, updatedUser);
        verify(userDAO, times(1)).update(appUser);
    }

    @Test
    void updateUser_throwsElementNotUpdatedException() {
        when(userDAO.update(appUser)).thenThrow(new ElementNotUpdatedException("User not updated"));

        assertThrows(ElementNotUpdatedException.class, () -> userService.updateUser(appUser, 1));
        verify(userDAO, times(1)).update(appUser);
    }

    @Test
    void deleteUser() {
        when(userDAO.delete(1)).thenReturn(1);

        Integer deletedId = userService.deleteUser(1);

        assertNotNull(deletedId);
        assertEquals(1, deletedId);
        verify(userDAO, times(1)).delete(1);
    }

    @Test
    void deleteUser_throwsElementNotDeletedException() {
        when(userDAO.delete(1)).thenThrow(new ElementNotDeletedException("User not deleted"));

        assertThrows(ElementNotDeletedException.class, () -> userService.deleteUser(1));
        verify(userDAO, times(1)).delete(1);
    }

    @Test
    void getUserID() {
        when(userDAO.getId(appUser)).thenReturn(1);

        Integer userId = userService.getUserID(appUser);

        assertNotNull(userId);
        assertEquals(1, userId);
        verify(userDAO, times(1)).getId(appUser);
    }

    @Test
    void getUserID_throwsIdNotReceivedException() {
        when(userDAO.getId(appUser)).thenThrow(new IdNotReceivedException("Id not found"));

        assertThrows(IdNotReceivedException.class, () -> userService.getUserID(appUser));
        verify(userDAO, times(1)).getId(appUser);
    }
}