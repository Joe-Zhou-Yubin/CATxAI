package com.catai.supchain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.catai.supchain.model.Rice;


public interface RiceRepository extends JpaRepository<Rice, Long>{

	List<Rice> findByStoreId(String storeId);
	 
}
