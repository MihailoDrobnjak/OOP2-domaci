package me.fit.model;

import jakarta.persistence.*;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_seq")
    @SequenceGenerator(name = "cart_seq", sequenceName = "cart_seq", allocationSize = 1)
    public Long id;

    public Double totalAmount;

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToOne(mappedBy = "cart")
    public Customer customer;

    public Cart() {}
}