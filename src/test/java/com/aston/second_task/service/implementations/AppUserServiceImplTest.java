package com.aston.second_task.service.implementations;

import com.aston.second_task.entity.AppUser;
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

class AppUserServiceImplTest {

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
        AppUser appUser = new AppUser();
        appUser.setEmail("test@example.com");
        appUser.setPassword("password");

        when(userDAO.save(appUser)).thenReturn(appUser);

        AppUser savedAppUser = userService.saveUser(appUser);

        assertNotNull(savedAppUser);
        assertEquals(appUser, savedAppUser);
        verify(userDAO, times(1)).save(appUser);
    }

    @Test
    void findUserById() {
        Integer id = 1;
        AppUser appUser = new AppUser();
        appUser.setId(id);

        when(userDAO.findById(id)).thenReturn(appUser);

        AppUser foundAppUser = userService.findUserById(id);

        assertNotNull(foundAppUser);
        assertEquals(id, foundAppUser.getId());
        verify(userDAO, times(1)).findById(id);
    }

    @Test
    void findAllUsers() {
        AppUser appUser1 = new AppUser();
        appUser1.setId(1);
        AppUser appUser2 = new AppUser();
        appUser2.setId(2);

        List<AppUser> appUsers = Arrays.asList(appUser1, appUser2);

        when(userDAO.findAll()).thenReturn(appUsers);

        List<AppUser> foundAppUsers = userService.findAllUsers();

        assertNotNull(foundAppUsers);
        assertEquals(2, foundAppUsers.size());
        verify(userDAO, times(1)).findAll();
    }

    @Test
    void updateUser() {
        Integer id = 1;
        AppUser appUser = new AppUser();
        appUser.setEmail("test@example.com");
        appUser.setPassword("password");

        when(userDAO.update(appUser)).thenReturn(appUser);

        AppUser updatedAppUser = userService.updateUser(appUser, id);

        assertNotNull(updatedAppUser);
        assertEquals(id, updatedAppUser.getId());
        verify(userDAO, times(1)).update(appUser);
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
        AppUser appUser = new AppUser();
        appUser.setEmail("test@example.com");
        appUser.setPassword("password");
        Integer id = 1;

        when(userDAO.getId(appUser)).thenReturn(id);

        Integer foundId = userService.getUserID(appUser);

        assertNotNull(foundId);
        assertEquals(id, foundId);
        verify(userDAO, times(1)).getId(appUser);
    }
}