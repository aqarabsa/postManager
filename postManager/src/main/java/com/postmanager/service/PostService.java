package com.postmanager.service;

import com.postmanager.model.entity.CommentEntity;
import com.postmanager.model.entity.PostEntity;
import com.postmanager.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class PostService {

    private PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostEntity> getAllPosts() {
        return this.postRepository.findAll();
    }

    public List<PostEntity> getOwnPosts() {

        return this.postRepository.findAll();
    }

    @Transactional
    public PostEntity addCommentToPost(UUID postId, CommentEntity commentEntity) {
        PostEntity postEntity = this.postRepository.findById(postId).get();
        postEntity.getCommentList().add(commentEntity);
        commentEntity.setPost(postEntity);
        return this.postRepository.save(postEntity);
    }
}
