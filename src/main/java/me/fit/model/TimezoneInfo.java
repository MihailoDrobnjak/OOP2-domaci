package me.fit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class TimezoneInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tz_seq")
    @SequenceGenerator(name = "tz_seq", sequenceName = "tz_seq", allocationSize = 1)
    public Long id;

    public String timeZone;
    public String currentLocalTime;

    @com.fasterxml.jackson.annotation.JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    public Customer customer;

    public TimezoneInfo() {}
}