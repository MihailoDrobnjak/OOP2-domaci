package me.fit.resource;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import me.fit.client.IpClient;
import me.fit.client.TimezoneClient;
import me.fit.client.TimezoneDto;
import me.fit.model.Customer;
import me.fit.model.TimezoneInfo;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/getTimezoneByIP")
public class TimezoneResource {

    @Inject
    @RestClient
    IpClient ipClient;

    @Inject
    @RestClient
    TimezoneClient timezoneClient;

    @Inject
    EntityManager em;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response getTimezone(@QueryParam("userId") Long userId) {

        Customer customer = em.find(Customer.class, userId);

        if (customer == null) {
            throw new WebApplicationException("Korisnik sa ID-jem " + userId + " ne postoji.", Response.Status.NOT_FOUND);
        }

        String myIp = ipClient.getIp();
        TimezoneDto tzDto = timezoneClient.getTimezone(myIp);

        TimezoneInfo newTzInfo = new TimezoneInfo();
        newTzInfo.timeZone = tzDto.timeZone;
        newTzInfo.currentLocalTime = tzDto.currentLocalTime;
        newTzInfo.customer = customer;

        customer.timezones.add(newTzInfo);
        em.persist(newTzInfo);

        return Response.ok(customer).build();
    }
}