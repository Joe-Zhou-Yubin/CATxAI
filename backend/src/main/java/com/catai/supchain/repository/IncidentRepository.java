package com.catai.supchain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.catai.supchain.model.Incident;

public interface IncidentRepository extends JpaRepository<Incident, Long>{

	List<Incident> findByAffstore(String storeId);

}
