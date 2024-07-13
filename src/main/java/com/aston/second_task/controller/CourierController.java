package com.aston.second_task.controller;

import com.aston.second_task.dto.incoming.CourierDTOInc;
import com.aston.second_task.dto.outgoing.CourierDTOOut;
import com.aston.second_task.entity.Courier;
import com.aston.second_task.mapper.CourierMapper;
import com.aston.second_task.service.CourierService;
import com.aston.second_task.exceptions.*;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/couriers")
public class CourierController {
    private CourierService courierService;

    @Inject
    public CourierController(CourierService courierService) {
        this.courierService = courierService;
    }

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourier(@PathParam("id") Integer id) {
        try {
            Courier courier = courierService.findCourierById(id);
            CourierDTOOut courierDTOOut = CourierMapper.INSTANCE.courierToCourierDTOOut(courier);
            return Response.ok(courierDTOOut).build();
        } catch (ElementNotFoundExceptions e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Courier not found").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCouriers() {
        try {
            List<Courier> couriers = courierService.findAllCouriers();
            List<CourierDTOOut> courierDTOOuts = couriers.stream()
                    .map(CourierMapper.INSTANCE::courierToCourierDTOOut)
                    .collect(Collectors.toList());
            return Response.ok(courierDTOOuts).build();
        } catch (ElementsNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("No couriers found").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveCourier(CourierDTOInc courierDTOInc) {
        try {
            Courier courier = CourierMapper.INSTANCE.courierDTOIncToCourier(courierDTOInc);
            courierService.saveCourier(courier);
            return Response.ok().build();
        } catch (ElementNotSavedException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error saving courier").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }

    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCourier(CourierDTOInc courierDTOInc, @PathParam("id") Integer id) {
        try {
            Courier courier = CourierMapper.INSTANCE.courierDTOIncToCourier(courierDTOInc);
            courierService.updateCourier(courier, id);
            return Response.ok().build();
        } catch (ElementNotUpdatedException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error updating courier").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteCourier(@PathParam("id") Integer id) {
        try {
            courierService.deleteCourier(id);
            return Response.ok().build();
        } catch (ElementNotDeletedException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error deleting courier").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }
}