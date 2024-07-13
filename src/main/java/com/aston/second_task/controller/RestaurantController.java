package com.aston.second_task.controller;

import com.aston.second_task.dto.incoming.RestaurantDTOInc;
import com.aston.second_task.dto.outgoing.RestaurantDTOOut;
import com.aston.second_task.entity.Restaurant;
import com.aston.second_task.mapper.RestaurantMapper;
import com.aston.second_task.service.RestaurantService;
import com.aston.second_task.exceptions.*;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/restaurants")
public class RestaurantController {

    private RestaurantService restaurantService;

    @Inject
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRestaurant(@PathParam("id") Integer id) {
        try {
            Restaurant restaurant = restaurantService.findRestaurantById(id);
            RestaurantDTOOut restaurantDTOOut = RestaurantMapper.INSTANCE.restaurantToRestaurantDTOOut(restaurant);
            return Response.ok(restaurantDTOOut).build();
        } catch (ElementNotFoundExceptions e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Restaurant not found").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRestaurants() {
        try {
            List<Restaurant> restaurants = restaurantService.findAllRestaurants();
            List<RestaurantDTOOut> restaurantDTOOuts = restaurants.stream()
                    .map(RestaurantMapper.INSTANCE::restaurantToRestaurantDTOOut)
                    .collect(Collectors.toList());
            return Response.ok(restaurantDTOOuts).build();
        } catch (ElementsNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("No restaurants found").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveRestaurant(RestaurantDTOInc restaurantDTOInc) {
        try {
            Restaurant restaurant = RestaurantMapper.INSTANCE.restaurantDTOIncToRestaurant(restaurantDTOInc);
            restaurantService.saveRestaurant(restaurant);
            return Response.ok().build();
        } catch (ElementNotSavedException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error saving restaurant").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }

    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateRestaurant(RestaurantDTOInc restaurantDTOInc, @PathParam("id") Integer id) {
        try {
            Restaurant restaurant = RestaurantMapper.INSTANCE.restaurantDTOIncToRestaurant(restaurantDTOInc);
            restaurantService.updateRestaurant(restaurant, id);
            return Response.ok().build();
        } catch (ElementNotUpdatedException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error updating restaurant").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteRestaurant(@PathParam("id") Integer id) {
        try {
            restaurantService.deleteRestaurant(id);
            return Response.ok().build();
        } catch (ElementNotDeletedException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error deleting restaurant").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }
}