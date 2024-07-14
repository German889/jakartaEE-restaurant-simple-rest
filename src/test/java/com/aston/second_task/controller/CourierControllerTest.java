package com.aston.second_task.controller;

import com.aston.second_task.dto.incoming.CourierDTOInc;
import com.aston.second_task.dto.outgoing.CourierDTOOut;
import com.aston.second_task.dto.outgoing.UserDTOOut;
import com.aston.second_task.entity.AppUser;
import com.aston.second_task.entity.Courier;
import com.aston.second_task.exceptions.*;
import com.aston.second_task.mapper.CourierMapper;
import com.aston.second_task.service.CourierService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.ws.rs.core.Response;

import java.util.Arrays;
import java.util.HashSet;
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
        AppUser appUser = new AppUser();
        appUser.setId(1);
        courier.setUser(appUser);

        CourierDTOOut courierDTOOut = new CourierDTOOut();
        courierDTOOut.setUser(new UserDTOOut());

        when(courierService.findCourierById(id)).thenReturn(courier);

        Response response = courierController.getCourier(id);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(courierDTOOut, response.getEntity());
    }
    @Test
    void getCourier_NotFound() {
        Integer courierId = 1;
        when(courierService.findCourierById(courierId)).thenThrow(new ElementNotFoundExceptions("Courier not found"));
        Response response = courierController.getCourier(courierId);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals("Courier not found", response.getEntity());
    }

    @Test
    void getCourier_InternalServerError() {
        Integer courierId = 1;
        when(courierService.findCourierById(courierId)).thenThrow(new RuntimeException("Some unexpected error"));
        Response response = courierController.getCourier(courierId);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        assertEquals("Internal server error", response.getEntity());
    }

    @Test
    void getAllCouriers() {
        AppUser appUser1 = new AppUser.Builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("1234567890")
                .password("password")
                .address("123 Main St")
                .role("courier")
                .reviewedRestaurants(new HashSet<>())
                .build();

        AppUser appUser2 = new AppUser.Builder()
                .id(2)
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@example.com")
                .phone("0987654321")
                .password("password")
                .address("456 Elm St")
                .role("courier")
                .reviewedRestaurants(new HashSet<>())
                .build();

        Courier courier1 = new Courier();
        courier1.setId(1);
        courier1.setUser(appUser1);
        courier1.setVehicleModel("lada");

        Courier courier2 = new Courier();
        courier2.setId(2);
        courier2.setUser(appUser2);
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
    void getAllCouriers_NotFound() {
        when(courierService.findAllCouriers()).thenThrow(new ElementsNotFoundException("No couriers found"));
        Response response = courierController.getAllCouriers();
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals("No couriers found", response.getEntity());
    }

    @Test
    void getAllCouriers_InternalServerError() {
        when(courierService.findAllCouriers()).thenThrow(new RuntimeException("Some unexpected error"));
        Response response = courierController.getAllCouriers();
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        assertEquals("Internal server error", response.getEntity());
    }

    @Test
    void saveCourier() {
        CourierDTOInc courierDTOInc = new CourierDTOInc();
        courierDTOInc.setVehicleModel("ford");
        AppUser appUser = new AppUser();
        appUser.setId(1);
        courierDTOInc.setUser(appUser);

        Courier courier = new Courier();
        courier.setVehicleModel("ford");
        courier.setUser(appUser);

        Response response = courierController.saveCourier(courierDTOInc);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(courierService, times(1)).saveCourier(courier);
    }
    @Test
    void saveCourier_ElementNotSaved() {
        CourierDTOInc courierDTOInc = new CourierDTOInc();
        Courier courier = CourierMapper.INSTANCE.courierDTOIncToCourier(courierDTOInc);
        doThrow(new ElementNotSavedException("Error saving courier")).when(courierService).saveCourier(courier);
        Response response = courierController.saveCourier(courierDTOInc);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals("Error saving courier", response.getEntity());
    }

    @Test
    void saveCourier_InternalServerError() {
        CourierDTOInc courierDTOInc = new CourierDTOInc();
        Courier courier = CourierMapper.INSTANCE.courierDTOIncToCourier(courierDTOInc);
        doThrow(new RuntimeException("Some unexpected error")).when(courierService).saveCourier(courier);
        Response response = courierController.saveCourier(courierDTOInc);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        assertEquals("Internal server error", response.getEntity());
    }

    @Test
    void updateCourier() {
        Integer id = 1;
        CourierDTOInc courierDTOInc = new CourierDTOInc();
        courierDTOInc.setVehicleModel("mazda");
        AppUser appUser = new AppUser();
        appUser.setId(1);
        courierDTOInc.setUser(appUser);

        Courier courier = new Courier();
        courier.setVehicleModel("mazda");
        courier.setUser(appUser);

        Response response = courierController.updateCourier(courierDTOInc, id);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(courierService, times(1)).updateCourier(courier, id);
    }
    @Test
    void updateCourier_ElementNotUpdated() {
        Integer courierId = 1;
        CourierDTOInc courierDTOInc = new CourierDTOInc();
        Courier courier = CourierMapper.INSTANCE.courierDTOIncToCourier(courierDTOInc);
        doThrow(new ElementNotUpdatedException("Error updating courier")).when(courierService).updateCourier(courier, courierId);
        Response response = courierController.updateCourier(courierDTOInc, courierId);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals("Error updating courier", response.getEntity());
    }

    @Test
    void updateCourier_InternalServerError() {
        Integer courierId = 1;
        CourierDTOInc courierDTOInc = new CourierDTOInc();
        Courier courier = CourierMapper.INSTANCE.courierDTOIncToCourier(courierDTOInc);
        doThrow(new RuntimeException("Some unexpected error")).when(courierService).updateCourier(courier, courierId);
        Response response = courierController.updateCourier(courierDTOInc, courierId);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        assertEquals("Internal server error", response.getEntity());
    }

    @Test
    void deleteCourier() {
        Integer id = 1;

        Response response = courierController.deleteCourier(id);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(courierService, times(1)).deleteCourier(id);
    }
    @Test
    void deleteCourier_ElementNotDeleted() {
        Integer courierId = 1;
        doThrow(new ElementNotDeletedException("Error deleting courier")).when(courierService).deleteCourier(courierId);
        Response response = courierController.deleteCourier(courierId);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals("Error deleting courier", response.getEntity());
    }

    @Test
    void deleteCourier_InternalServerError() {
        Integer courierId = 1;
        doThrow(new RuntimeException("Some unexpected error")).when(courierService).deleteCourier(courierId);
        Response response = courierController.deleteCourier(courierId);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        assertEquals("Internal server error", response.getEntity());
    }
}