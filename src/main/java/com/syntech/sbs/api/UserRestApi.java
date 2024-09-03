package com.syntech.sbs.api;

import com.syntech.sbs.model.User;
import com.syntech.sbs.repository.UserRepository;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserRestApi {

    @Inject
    private UserRepository userRepository;

    @GET
    public Response getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            return RestResponse.responseBuilder("true", "200", "Users retrieved successfully", users.toString());
        } catch (Exception e) {
            return RestResponse.responseBuilder("false", "500", "An error occurred", e.getMessage());
        }
    }

    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") Long id) {
        try {
            User user = userRepository.findById(id);
            if (user != null) {
                return RestResponse.responseBuilder("true", "200", "User found", user.toString());
            } else {
                return RestResponse.responseBuilder("false", "404", "User not found", null);
            }
        } catch (Exception e) {
            return RestResponse.responseBuilder("false", "500", "An error occurred", e.getMessage());
        }
    }

    @POST
    public Response createUser(User user) {
        try {
            userRepository.save(user);
            return RestResponse.responseBuilder("true", "201", "User created successfully", user.toString());
        } catch (Exception e) {
            return RestResponse.responseBuilder("false", "400", "Failed to create user", e.getMessage());
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") Long id, User user) {
        try {
            User existingUser = userRepository.findById(id);
            if (existingUser != null) {
                user.setId(id);
                userRepository.update(user);
                return RestResponse.responseBuilder("true", "200", "User updated successfully", user.toString());
            } else {
                return RestResponse.responseBuilder("false", "404", "User not found", null);
            }
        } catch (Exception e) {
            return RestResponse.responseBuilder("false", "500", "An error occurred", e.getMessage());
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        try {
            User user = userRepository.findById(id);
            if (user != null) {
                userRepository.delete(id);
                return RestResponse.responseBuilder("true", "204", "User deleted successfully", null);
            } else {
                return RestResponse.responseBuilder("false", "404", "User not found", null);
            }
        } catch (Exception e) {
            return RestResponse.responseBuilder("false", "500", "An error occurred", e.getMessage());
        }
    }
}
