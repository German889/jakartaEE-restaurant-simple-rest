package com.aston.second_task.controller;

import com.aston.second_task.dto.incoming.UserDTOInc;
import com.aston.second_task.dto.outgoing.UserDTOOut;
import com.aston.second_task.entity.AppUser;
import com.aston.second_task.mapper.UserMapper;
import com.aston.second_task.service.interfaces.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/users")
public class UserController {
    @Inject
    private UserService userService;

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") Integer id) {
        AppUser appUser = userService.findUserById(id);
        UserDTOOut userDTOOut = UserMapper.INSTANCE.userToUserDTOOut(appUser);
        return Response.ok(userDTOOut).build();
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers(){
        List<AppUser> appUsers = userService.findAllUsers();
        List<UserDTOOut> userDTOOuts = appUsers.stream()
                .map(UserMapper.INSTANCE::userToUserDTOOut)
                .collect(Collectors.toList());
        return Response.ok(userDTOOuts).build();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveUser(UserDTOInc userDTOInc) {
        try {
            AppUser appUser = UserMapper.INSTANCE.userDTOIncToUser(userDTOInc);
            userService.saveUser(appUser);
            return Response.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error saving user: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(UserDTOInc userDTOInc, @PathParam("id") Integer id){
        try {
            AppUser appUser = UserMapper.INSTANCE.userDTOIncToUser(userDTOInc);
            userService.updateUser(appUser, id);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error updating user: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteUser(@PathParam("id") Integer id){
        try{
            userService.deleteUser(id);
            return Response.ok().build();
        }catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting user: " + e.getMessage())
                    .build();
        }
    }

}