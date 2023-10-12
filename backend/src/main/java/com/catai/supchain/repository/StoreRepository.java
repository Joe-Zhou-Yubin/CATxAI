package com.catai.supchain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.catai.supchain.model.Store;


public interface StoreRepository extends JpaRepository<Store, Long>{
    Optional<Store> findByStoreId(String storeId);

}
