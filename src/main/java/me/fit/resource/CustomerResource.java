package me.fit.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import me.fit.model.Customer;
import me.fit.service.CustomerService;

import java.util.List;

@Path("/customer")
public class CustomerResource {

    @Inject
    private CustomerService customerService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/addCustomer")
    public String addCustomer(Customer customer) {
        customerService.createCustomer(customer);
        return "Hello RESTEasy";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAllCustomers")
    public Response getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return Response.ok().entity(customers).build();
    }
}