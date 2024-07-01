package com.aston.second_task.controller;

import com.aston.second_task.dto.incoming.CourierDTOInc;
import com.aston.second_task.dto.outgoing.CourierDTOOut;
import com.aston.second_task.dto.outgoing.UserDTOOut;
import com.aston.second_task.entity.Courier;
import com.aston.second_task.entity.User;
import com.aston.second_task.service.interfaces.CourierService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.ws.rs.core.Response;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CourierControllerTest {

    @Mock
    private CourierService courierService;

    @InjectMocks
    private CourierController courierController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCourier() {
        Integer id = 1;
        Courier courier = new Courier();
        courier.setId(id);
        User user = new User();
        user.setId(1);
        courier.setUser(user);

        CourierDTOOut courierDTOOut = new CourierDTOOut();
        courierDTOOut.setUser(new UserDTOOut());

        when(courierService.findCourierById(id)).thenReturn(courier);

        Response response = courierController.getCourier(id);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(courierDTOOut, response.getEntity());
    }

    @Test
    void getAllCouriers() {
        User user1 = new User(1, "John", "Doe", "john.doe@example.com", "1234567890", "password", "123 Main St", "courier");
        User user2 = new User(2, "Jane", "Doe", "jane.doe@example.com", "0987654321", "password", "456 Elm St", "courier");

        Courier courier1 = new Courier();
        courier1.setId(1);
        courier1.setUser(user1);
        courier1.setVehicleModel("lada");

        Courier courier2 = new Courier();
        courier2.setId(2);
        courier2.setUser(user2);
        courier2.setVehicleModel("mazda");

        List<Courier> couriers = Arrays.asList(courier1, courier2);

        UserDTOOut userDTOOut1 = new UserDTOOut("John", "Doe", "john.doe@example.com");
        UserDTOOut userDTOOut2 = new UserDTOOut("Jane", "Doe", "jane.doe@example.com");

        CourierDTOOut courierDTOOut1 = new CourierDTOOut();
        courierDTOOut1.setVehicleModel("lada");
        courierDTOOut1.setUser(userDTOOut1);

        CourierDTOOut courierDTOOut2 = new CourierDTOOut();
        courierDTOOut2.setVehicleModel("mazda");
        courierDTOOut2.setUser(userDTOOut2);

        List<CourierDTOOut> courierDTOOuts = Arrays.asList(courierDTOOut1, courierDTOOut2);

        when(courierService.findAllCouriers()).thenReturn(couriers);

        Response response = courierController.getAllCouriers();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(courierDTOOuts, response.getEntity());
    }

    @Test
    void saveCourier() {
        CourierDTOInc courierDTOInc = new CourierDTOInc();
        courierDTOInc.setVehicleModel("ford");
        User user = new User();
        user.setId(1);
        courierDTOInc.setUser(user);

        Courier courier = new Courier();
        courier.setVehicleModel("ford");
        courier.setUser(user);

        Response response = courierController.saveCourier(courierDTOInc);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(courierService, times(1)).saveCourier(courier);
    }

    @Test
    void updateCourier() {
        Integer id = 1;
        CourierDTOInc courierDTOInc = new CourierDTOInc();
        courierDTOInc.setVehicleModel("mazda");
        User user = new User();
        user.setId(1);
        courierDTOInc.setUser(user);

        Courier courier = new Courier();
        courier.setVehicleModel("mazda");
        courier.setUser(user);

        Response response = courierController.updateCourier(courierDTOInc, id);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(courierService, times(1)).updateCourier(courier, id);
    }

    @Test
    void deleteCourier() {
        Integer id = 1;

        Response response = courierController.deleteCourier(id);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(courierService, times(1)).deleteCourier(id);
    }
}