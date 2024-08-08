//package com.syntech.sbs.controller;
//
//import com.syntech.sbs.model.User;
//import com.syntech.sbs.service.UserService;
//import java.util.List;
//import javax.ejb.EJB;
//import javax.ws.rs.*;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
//@Path("/api/users")
//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
//public class UserController {
//
//    @EJB
//    private UserService userService;
//
//    @POST
//    public Response createUser(User user) {
//        userService.saveUser(user);
//        return Response.ok(user).build();
//    }
//
//    @GET
//    @Path("/{id}")
//    public Response getUserById(@PathParam("id") Long id) {
//        User user = userService.getUserById(id);
//        if (user == null) {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//        return Response.ok(user).build();
//    }
//
//    @PUT
//    @Path("/{id}")
//    public Response updateUser(@PathParam("id") Long id, User userDetails) {
//        User user = userService.getUserById(id);
//        if (user == null) {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//        user.setName(userDetails.getName());
//        user.setUsername(userDetails.getUsername());
//        user.setPassword(userDetails.getPassword());
//        userService.updateUser(user);
//        return Response.ok(user).build();
//    }
//
//    @DELETE
//    @Path("/{id}")
//    public Response deleteUser(@PathParam("id") Long id) {
//        User user = userService.getUserById(id);
//        if (user == null) {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//        userService.deleteUser(id);
//        return Response.noContent().build();
//    }
//
//    @GET
//    public Response getAllUsers() {
//        List<User> users = userService.getAllUsers();
//        return Response.ok(users).build();
//    }
//
//    @GET
//    @Path("/username/{username}")
//    public Response getUserByUsername(@PathParam("username") String username) {
//        User user = userService.getUserByUsername(username);
//        if (user == null) {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//        return Response.ok(user).build();
//    }
//}
