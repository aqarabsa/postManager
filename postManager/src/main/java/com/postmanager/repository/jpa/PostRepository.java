package com.postmanager.repository.jpa;

import com.postmanager.model.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<PostEntity, UUID> {

    List<PostEntity> findByVisibleTrue();
    List<PostEntity> findByVisibleTrueAndUser_Username(String username);
}
