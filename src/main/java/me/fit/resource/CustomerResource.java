package me.fit.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import me.fit.model.Customer;
import me.fit.model.Order;
import me.fit.model.Product;
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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/addFullCustomer")
    public Response createFullCustomer(Customer customer) {
        Customer savedCustomer = customerService.createCustomerWithCollections(customer);
        return Response.status(Response.Status.CREATED).entity(savedCustomer).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/search")
    public Response searchCustomers(@QueryParam("ime") String ime) {
        if (ime == null || ime.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Ime is required").build();
        }

        List<Customer> foundCustomers = customerService.findByName(ime);
        return Response.ok(foundCustomers).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/orders")
    public Response getCustomerOrders(@PathParam("id") Long customerId) {
        List<Order> orders = customerService.getOrdersByCustomerId(customerId);
        if (orders == null || orders.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No orders found for this customer").build();
        }
        return Response.ok(orders).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/category/{id}/products")
    public Response getCategoryProducts(@PathParam("id") Long categoryId) {
        List<Product> products = customerService.getProductsByCategoryId(categoryId);
        if (products == null || products.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Nema proizvoda za ovu kategoriju").build();
        }
        return Response.ok(products).build();
    }
}