package me.fit.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQuery(name = Customer.GET_ALL_CUSTOMERS, query = "Select c from Customer c")
public class Customer {

    public static final String GET_ALL_CUSTOMERS = "GetAllCustomers";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    @SequenceGenerator(name = "customer_seq", sequenceName = "customer_seq", allocationSize = 1)
    public Long id;

    public String ime;
    public String prezime;

    // Requirement 3: List without relational annotations!
    // We add @Transient so Quarkus doesn't crash on startup.
    @Transient
    public List<Object> orders;

    public Customer() { }

    public void setId(Long id) { this.id = id; }
    public void setIme(String ime) { this.ime = ime; }
    public void setPrezime(String prezime) { this.prezime = prezime; }

    public Long getId() { return id; }
    public String getIme() { return ime; }
    public String getPrezime() { return prezime; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(ime, customer.ime) && Objects.equals(prezime, customer.prezime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ime, prezime);
    }
}