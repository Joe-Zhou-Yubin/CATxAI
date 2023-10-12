package com.catai.supchain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.catai.supchain.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
  
  Optional<User> findByUserId(String userId);
}