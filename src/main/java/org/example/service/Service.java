package org.example.service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.example.model.User;

import java.util.ArrayList;
import java.util.List;

@Path("/")
public class Service {
    @GET
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "getUSer description",
            description = "getUser description")
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200",
                description = "success",
                content = {@Content(schema =
                    @Schema(implementation = User.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content
            )
    })
    public User getUser() {
        return new User("ady", "ady@ady.com");
    }

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "getUSers description",
            description = "getUsers description")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "success",
                    content = {@Content(schema =
                    @Schema(implementation = List.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content
            )
    })
    public List<User> getUsers() {
        List<User> list = new ArrayList<>();
        list.add(new User("ady", "ady@ady.com"));
        list.add(new User("ady2", "ady2@ady2.com"));
        return list;
    }
}
