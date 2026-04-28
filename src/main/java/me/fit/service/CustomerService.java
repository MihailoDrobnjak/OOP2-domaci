package me.fit.service;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import me.fit.model.Customer;
import me.fit.model.Order;
import me.fit.model.Product;

import java.util.List;

@Dependent
public class CustomerService {

    @Inject
    private EntityManager em;

    @Transactional
    public Customer createCustomer(Customer customer){
        return em.merge(customer);
    }

    @Transactional
    public List<Customer> getAllCustomers(){
        return em.createNamedQuery(Customer.GET_ALL_CUSTOMERS, Customer.class).getResultList();
    }

    @Transactional
    public Customer createCustomerWithCollections(Customer customer) {

        if (customer.orders != null) {
            for (Order order : customer.orders) {
                order.customer = customer;
            }
        }

        em.persist(customer);
        return customer;
    }

    public List<Customer> findByName(String ime) {
        return em.createQuery("SELECT c FROM Customer c WHERE c.ime = :ime", Customer.class)
                .setParameter("ime", ime)
                .getResultList();
    }

    public List<Order> getOrdersByCustomerId(Long customerId) {
        return em.createQuery("SELECT o FROM Order o WHERE o.customer.id = :customerId", Order.class)
                .setParameter("customerId", customerId)
                .getResultList();
    }

    public List<Product> getProductsByCategoryId(Long categoryId) {
        return em.createQuery("SELECT p FROM Product p WHERE p.category.id = :categoryId", Product.class)
                .setParameter("categoryId", categoryId)
                .getResultList();
    }
}