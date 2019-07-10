package com.postmanager.service;

import com.postmanager.controller.exception.ObjectNotExistException;
import com.postmanager.controller.exception.UnauthorizedAccessException;
import com.postmanager.model.entity.CommentEntity;
import com.postmanager.model.entity.PostEntity;
import com.postmanager.repository.PostRepository;
import com.postmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class PostService {

    private PostRepository postRepository;

    private UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
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

    @Transactional(propagation = Propagation.MANDATORY)
    public PostEntity updatePost(UUID id, PostEntity newPostEntity) {
        PostEntity postEntity = this.postRepository.findById(id).orElseThrow(() -> new ObjectNotExistException(id));
        postEntity.setContent(newPostEntity.getContent());
        postEntity.setTitle(newPostEntity.getTitle());
        return this.postRepository.save(postEntity);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void verifyExists(UUID id) {
        this.postRepository.findById(id).orElseThrow(() -> new ObjectNotExistException(id));
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void verifyOwner(UUID id, String username) {
        PostEntity postEntity =  this.postRepository.findById(id).orElseThrow(() -> new ObjectNotExistException(id));
        if(!postEntity.getUser().getUsername().equals(username)) {
            throw new UnauthorizedAccessException();
        }
    }


}
