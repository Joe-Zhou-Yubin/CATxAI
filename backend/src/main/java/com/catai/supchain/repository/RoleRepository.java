package com.catai.supchain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.catai.supchain.model.ERole;
import com.catai.supchain.model.Role;



public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}