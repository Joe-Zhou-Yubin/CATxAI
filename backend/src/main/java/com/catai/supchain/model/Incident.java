package com.catai.supchain.model;


import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "incidents")
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date month;

    @Column(length = 2000)
    private String description;

    @ElementCollection
    @CollectionTable(name = "incident_affstores", joinColumns = @JoinColumn(name = "incident_id"))
    @Column(name = "affstore")
    private Set<String> affstore;


    public Incident() {
    }

    public Incident(Date month, String description, Set<String> affstore) {
        this.month = month;
        this.description = description;
        this.affstore = affstore;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getAffstore() {
        return affstore;
    }

    public void setAffstore(Set<String> affstore) {
        this.affstore = affstore;
    }
}
