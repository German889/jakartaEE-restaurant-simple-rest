package com.aston.second_task.controller;

import com.aston.second_task.dto.incoming.RestaurantDTOInc;
import com.aston.second_task.dto.outgoing.RestaurantDTOOut;
import com.aston.second_task.entity.Restaurant;
import com.aston.second_task.mapper.RestaurantMapper;
import com.aston.second_task.service.interfaces.RestaurantService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/restaurants")
public class RestaurantController {
    @Inject
    private RestaurantService restaurantService;

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRestaurant(@PathParam("id") Integer id) {
        Restaurant restaurant = restaurantService.findRestaurantById(id);
        RestaurantDTOOut restaurantDTOOut = RestaurantMapper.INSTANCE.restaurantToRestaurantDTOOut(restaurant);
        return Response.ok(restaurantDTOOut).build();
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRestaurants(){
        List<Restaurant> restaurants = restaurantService.findAllRestaurants();
        List<RestaurantDTOOut> restaurantDTOOuts = restaurants.stream()
                .map(RestaurantMapper.INSTANCE::restaurantToRestaurantDTOOut)
                .collect(Collectors.toList());
        return Response.ok(restaurantDTOOuts).build();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveRestaurant(RestaurantDTOInc restaurantDTOInc) {
        try {
            Restaurant restaurant = RestaurantMapper.INSTANCE.restaurantDTOIncToRestaurant(restaurantDTOInc);
            restaurantService.saveRestaurant(restaurant);
            return Response.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error saving restaurant: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateRestaurant(RestaurantDTOInc restaurantDTOInc, @PathParam("id") Integer id){
        try {
            Restaurant restaurant = RestaurantMapper.INSTANCE.restaurantDTOIncToRestaurant(restaurantDTOInc);
            restaurantService.updateRestaurant(restaurant, id);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error updating restaurant: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteRestaurant(@PathParam("id") Integer id){
        try{
            restaurantService.deleteRestaurant(id);
            return Response.ok().build();
        }catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting restaurant: " + e.getMessage())
                    .build();
        }
    }
}