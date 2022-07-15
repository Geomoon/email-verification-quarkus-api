package com.luna.restapi.exceptions;

import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

public class ExceptionsMapper {

    @ServerExceptionMapper
    public RestResponse<String> mapNotFoundException(NotFoundException e) {
        return RestResponse.status(RestResponse.Status.NOT_FOUND, e.getMessage());
    }
}
