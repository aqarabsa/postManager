package com.postmanager.repository.jpa;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.postmanager.model.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID>{

	Optional<UserEntity> findByUsername(String username);
}
