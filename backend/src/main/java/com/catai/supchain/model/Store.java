package com.catai.supchain.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "stores")  // Define the table name for the Store entity
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String storeId;
    private String postcode;
    private Long population;
    private Long thresamount;
    private Long minamount;

    public Store() {

    }

    public Store(String postcode, Long population, Long thresamount, Long minamount) {
        this.storeId = generateUnique8CharString();
        this.postcode = postcode;
        this.population = population;
        this.thresamount = thresamount;
        this.minamount = minamount;
    }

    private String generateUnique8CharString() {
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString().replace("-", "").substring(0, 8);
        return uuidStr;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public Long getThresamount() {
        return thresamount;
    }

    public void setThresamount(Long thresamount) {
        this.thresamount = thresamount;
    }

    public Long getMinamount() {
        return minamount;
    }

    public void setMinamount(Long minamount) {
        this.minamount = minamount;
    }

	@Override
	public String toString() {
		return "Store [id=" + id + ", storeId=" + storeId + ", postcode=" + postcode + ", population=" + population
				+ ", thresamount=" + thresamount + ", minamount=" + minamount + "]";
	}
	
}
