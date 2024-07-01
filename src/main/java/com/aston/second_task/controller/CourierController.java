package com.aston.second_task.controller;

import com.aston.second_task.dto.incoming.CourierDTOInc;
import com.aston.second_task.dto.outgoing.CourierDTOOut;
import com.aston.second_task.entity.Courier;
import com.aston.second_task.entity.User;
import com.aston.second_task.mapper.CourierMapper;
import com.aston.second_task.service.interfaces.CourierService;
import com.aston.second_task.service.interfaces.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/couriers")
public class CourierController {
    @Inject
    private CourierService courierService;
//    @Inject
//    private UserService userService;

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourier(@PathParam("id") Integer id) {
        Courier courier = courierService.findCourierById(id);
        CourierDTOOut courierDTOOut = CourierMapper.INSTANCE.courierToCourierDTOOut(courier);
        return Response.ok(courierDTOOut).build();
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCouriers(){
        List<Courier> couriers = courierService.findAllCouriers();
        List<CourierDTOOut> courierDTOOuts = couriers.stream()
                .map(CourierMapper.INSTANCE::courierToCourierDTOOut)
                .collect(Collectors.toList());
        return Response.ok(courierDTOOuts).build();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveCourier(CourierDTOInc courierDTOInc) {
        try {
            Courier courier = CourierMapper.INSTANCE.courierDTOIncToCourier(courierDTOInc);
//            User user = courier.getUser();
//            user.setId(userService.getUserID(user));
//            courier.setUser(user);
            courierService.saveCourier(courier);
            return Response.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error saving courier: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCourier(CourierDTOInc courierDTOInc, @PathParam("id") Integer id){
        try {
            Courier courier = CourierMapper.INSTANCE.courierDTOIncToCourier(courierDTOInc);
            courierService.updateCourier(courier, id);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error updating courier: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteCourier(@PathParam("id") Integer id){
        try{
            courierService.deleteCourier(id);
            return Response.ok().build();
        }catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting courier: " + e.getMessage())
                    .build();
        }
    }
}