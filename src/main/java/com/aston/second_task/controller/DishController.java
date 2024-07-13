package com.aston.second_task.controller;

import com.aston.second_task.dto.incoming.DishDTOInc;
import com.aston.second_task.dto.outgoing.DishDTOOut;
import com.aston.second_task.entity.Dish;
import com.aston.second_task.entity.Restaurant;
import com.aston.second_task.mapper.DishMapper;
import com.aston.second_task.service.DishService;
import com.aston.second_task.service.RestaurantService;
import com.aston.second_task.exceptions.*;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/dishes")
public class DishController {
    private DishService dishService;
    private RestaurantService restaurantService;

    @Inject
    public DishController(DishService dishService, RestaurantService restaurantService) {
        this.dishService = dishService;
        this.restaurantService = restaurantService;
    }

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDish(@PathParam("id") Integer id) {
        try {
            Dish dish = dishService.findDishById(id);
            DishDTOOut dishDTOOut = DishMapper.INSTANCE.dishToDishDTOOut(dish);
            return Response.ok(dishDTOOut).build();
        } catch (ElementNotFoundExceptions e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Dish not found").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDishes() {
        try {
            List<Dish> dishes = dishService.findAllDishes();
            List<DishDTOOut> dishDTOOuts = dishes.stream()
                    .map(DishMapper.INSTANCE::dishToDishDTOOut)
                    .collect(Collectors.toList());
            return Response.ok(dishDTOOuts).build();
        } catch (ElementsNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("No dishes found").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveDish(DishDTOInc dishDTOInc) {
        try {
            Dish dish = DishMapper.INSTANCE.dishDTOIncToDish(dishDTOInc);
            Restaurant restaurant = dish.getRestaurant();
            restaurant.setId(restaurantService.getRestaurantID(restaurant));
            dish.setRestaurant(restaurant);
            dishService.saveDish(dish);
            return Response.ok().build();
        } catch (ElementNotSavedException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error saving dish").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }

    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDish(DishDTOInc dishDTOInc, @PathParam("id") Integer id) {
        try {
            Dish dish = DishMapper.INSTANCE.dishDTOIncToDish(dishDTOInc);
            Restaurant restaurant = dish.getRestaurant();
            restaurant.setId(restaurantService.getRestaurantID(restaurant));
            dish.setRestaurant(restaurant);
            dishService.updateDish(dish, id);
            return Response.ok().build();
        } catch (ElementNotUpdatedException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error updating dish").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteDish(@PathParam("id") Integer id) {
        try {
            dishService.deleteDish(id);
            return Response.ok().build();
        } catch (ElementNotDeletedException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error deleting dish").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }
}