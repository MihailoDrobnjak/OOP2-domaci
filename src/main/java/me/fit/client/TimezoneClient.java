package me.fit.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://timeapi.io")
@Path("/api/time/current/ip")
public interface TimezoneClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    TimezoneDto getTimezone(@QueryParam("ipAddress") String ip);

}