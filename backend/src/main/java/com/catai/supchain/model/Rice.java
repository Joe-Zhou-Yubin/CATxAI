package com.catai.supchain.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "rice")  // Define the table name for the Rice entity
public class Rice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date month;

    private String storeId;
    private Long prediction;
    private Long quantity;

    public Rice() {

    }

    public Rice(Date month, String storeId, Long prediction, Long quantity) {
        this.month = month;
        this.storeId = storeId;
        this.prediction = prediction;
        this.quantity = quantity;
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

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Long getPrediction() {
        return prediction;
    }

    public void setPrediction(Long prediction) {
        this.prediction = prediction;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Rice [id=" + id + ", month=" + month + ", storeId=" + storeId + ", prediction=" + prediction + ", quantity=" + quantity + "]";
    }
}
