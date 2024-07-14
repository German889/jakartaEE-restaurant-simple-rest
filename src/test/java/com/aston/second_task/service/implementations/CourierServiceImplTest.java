package com.aston.second_task.service.implementations;

import com.aston.second_task.entity.AppUser;
import com.aston.second_task.entity.Courier;
import com.aston.second_task.dao.CourierDAO;
import com.aston.second_task.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourierServiceImplTest {

    @Mock
    private CourierDAO courierDAO;

    @InjectMocks
    private CourierServiceImpl courierService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveCourier() {
        Courier courier = new Courier();
        courier.setVehicleModel("ford");

        courierService.saveCourier(courier);

        verify(courierDAO, times(1)).save(courier);
    }
    @Test
    public void testSaveCourier_ElementNotSavedException() {
        // Создаем объект AppUser и Courier, которые будем сохранять
        AppUser appUser = new AppUser();
        appUser.setId(1);
        appUser.setFirstName("John");
        appUser.setLastName("Doe");
        Courier courier = new Courier();
        courier.setId(1);
        courier.setUser(appUser);
        doThrow(new ElementNotSavedException("Error saving courier")).when(courierDAO).save(courier);
        Exception exception = assertThrows(ElementNotSavedException.class, () -> {
            courierService.saveCourier(courier);
        });
        assertEquals("Courier not saved", exception.getMessage());
        verify(courierDAO, times(1)).save(courier);
    }

    @Test
    void findCourierById() {
        Integer id = 1;
        Courier courier = new Courier();
        courier.setId(id);
        courier.setVehicleRegistrationNumber("567");

        when(courierDAO.findById(id)).thenReturn(courier);

        Courier foundCourier = courierService.findCourierById(id);

        assertNotNull(foundCourier);
        assertEquals(id, foundCourier.getId());
        verify(courierDAO, times(1)).findById(id);
    }
    @Test
    public void testFindCourierById_ElementNotFoundExceptions() {
        Integer id = 1;
        when(courierDAO.findById(id)).thenThrow(new ElementNotFoundExceptions("Courier not found"));
        Exception exception = assertThrows(ElementNotFoundExceptions.class, () -> {
            courierService.findCourierById(id);
        });
        assertEquals("Courier with id " + id + " not found", exception.getMessage());
        verify(courierDAO, times(1)).findById(id);
    }

    @Test
    void findAllCouriers() {
        Courier courier1 = new Courier();
        courier1.setId(1);
        courier1.setVehicleModel("mazda");

        Courier courier2 = new Courier();
        courier2.setId(2);
        courier2.setVehicleModel("jac");

        List<Courier> couriers = Arrays.asList(courier1, courier2);

        when(courierDAO.findAll()).thenReturn(couriers);

        List<Courier> foundCouriers = courierService.findAllCouriers();

        assertNotNull(foundCouriers);
        assertEquals(2, foundCouriers.size());
        verify(courierDAO, times(1)).findAll();
    }
    @Test
    public void testFindAllCouriers_ElementsNotFoundException() {
        when(courierDAO.findAll()).thenThrow(new ElementsNotFoundException("No couriers found"));
        Exception exception = assertThrows(ElementsNotFoundException.class, () -> {
            courierService.findAllCouriers();
        });
        assertEquals("No couriers found", exception.getMessage());
        verify(courierDAO, times(1)).findAll();
    }

    @Test
    void updateCourier() {
        Integer id = 1;
        Courier courier = new Courier();
        courier.setVehicleModel("toyota");

        courierService.updateCourier(courier, id);

        assertEquals(id, courier.getId());
        verify(courierDAO, times(1)).update(courier);
    }
    @Test
    public void testUpdateCourier_ElementNotUpdatedException() {
        Courier courier = new Courier();
        Integer id = 1;
        doThrow(new ElementNotUpdatedException("Error updating courier")).when(courierDAO).update(courier);
        Exception exception = assertThrows(ElementNotUpdatedException.class, () -> {
            courierService.updateCourier(courier, id);
        });
        assertEquals("Courier not updated", exception.getMessage());
        verify(courierDAO, times(1)).update(courier);
    }

    @Test
    void deleteCourier() {
        Integer id = 1;

        courierService.deleteCourier(id);

        verify(courierDAO, times(1)).remove(id);
    }
    @Test
    public void testDeleteCourier_ElementNotDeletedException() {
        Integer id = 1;
        doThrow(new ElementNotDeletedException("Error deleting courier")).when(courierDAO).remove(id);
        Exception exception = assertThrows(ElementNotDeletedException.class, () -> {
            courierService.deleteCourier(id);
        });
        assertEquals("Courier with id " + id + " not deleted", exception.getMessage());
        verify(courierDAO, times(1)).remove(id);
    }
}