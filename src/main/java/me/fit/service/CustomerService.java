package me.fit.service;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import me.fit.model.Customer;

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
}