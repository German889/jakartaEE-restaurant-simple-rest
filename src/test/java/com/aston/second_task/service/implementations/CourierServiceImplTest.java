package com.aston.second_task.service.implementations;

import com.aston.second_task.entity.Courier;
import com.aston.second_task.repository.DAO.CourierDAO;
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
    void updateCourier() {
        Integer id = 1;
        Courier courier = new Courier();
        courier.setVehicleModel("toyota");

        courierService.updateCourier(courier, id);

        assertEquals(id, courier.getId());
        verify(courierDAO, times(1)).update(courier);
    }

    @Test
    void deleteCourier() {
        Integer id = 1;

        courierService.deleteCourier(id);

        verify(courierDAO, times(1)).remove(id);
    }
}