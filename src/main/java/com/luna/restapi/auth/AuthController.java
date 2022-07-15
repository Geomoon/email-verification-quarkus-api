package com.luna.restapi.auth;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @POST
    @Path("/signup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signup(@Context UriInfo uriInfo, SignupRequest request) {
        authService.signup(request);
        return Response.created(uriInfo.getRequestUri()).build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest request) {
        var res = authService.login(request);
        if (res == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();

        return Response.ok(res).build();
    }

    @GET
    @Path("/validate/{email}/{code}")
    public Response validateCode(
            @PathParam("email") String email,
            @PathParam("code") String code) {

        var valid = authService.validateAccount(email, code);
        if (valid) return Response.ok("Account validated").build();

        return Response.status(Response.Status.FORBIDDEN).build();
    }

}
