package me.fit.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


@RegisterRestClient(baseUri = "https://api.ipify.org")
@Path("/")
public interface IpClient {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    String getIp();

}