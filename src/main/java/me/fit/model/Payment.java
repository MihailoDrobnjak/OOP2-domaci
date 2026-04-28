package me.fit.model;

import jakarta.persistence.*;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_seq")
    @SequenceGenerator(name = "payment_seq", sequenceName = "payment_seq", allocationSize = 1)
    public Long id;

    public String method;

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToOne(mappedBy = "payment")
    public Order order;

    public Payment() {}
}