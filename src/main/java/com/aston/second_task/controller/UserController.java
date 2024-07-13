package com.aston.second_task.controller;

import com.aston.second_task.dto.incoming.UserDTOInc;
import com.aston.second_task.dto.outgoing.UserDTOOut;
import com.aston.second_task.entity.AppUser;
import com.aston.second_task.mapper.UserMapper;
import com.aston.second_task.service.UserService;
import com.aston.second_task.exceptions.*;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/users")
public class UserController {
    private UserService userService;

    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") Integer id) {
        try {
            AppUser appUser = userService.findUserById(id);
            UserDTOOut userDTOOut = UserMapper.INSTANCE.userToUserDTOOut(appUser);
            return Response.ok(userDTOOut).build();
        } catch (ElementNotFoundExceptions e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        try {
            List<AppUser> appUsers = userService.findAllUsers();
            List<UserDTOOut> userDTOOuts = appUsers.stream()
                    .map(UserMapper.INSTANCE::userToUserDTOOut)
                    .collect(Collectors.toList());
            return Response.ok(userDTOOuts).build();
        } catch (ElementsNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveUser(UserDTOInc userDTOInc) {
        try {
            AppUser appUser = UserMapper.INSTANCE.userDTOIncToUser(userDTOInc);
            userService.saveUser(appUser);
            return Response.ok().build();
        } catch (ElementNotSavedException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(UserDTOInc userDTOInc, @PathParam("id") Integer id) {
        try {
            AppUser appUser = UserMapper.INSTANCE.userDTOIncToUser(userDTOInc);
            userService.updateUser(appUser, id);
            return Response.ok().build();
        } catch (ElementNotUpdatedException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteUser(@PathParam("id") Integer id) {
        try {
            userService.deleteUser(id);
            return Response.ok().build();
        } catch (ElementNotDeletedException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}