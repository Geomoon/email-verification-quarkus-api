package com.luna.restapi.users;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class UserController {

    @Inject
    UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        return Response.ok(userService.findAll()).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") long id) {
        return Response.ok(userService.findById(id)).build();
    }

    @GET
    @Path("/email/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByEmail(@PathParam("email") String email) {
        return Response.ok(userService.findByEmail(email)).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteById(@PathParam("id") long id) {
        userService.deleteById(id);
        return Response.ok().build();
    }
}
