package com.postmanager.service;

import com.postmanager.controller.exception.ObjectNotExistException;
import com.postmanager.controller.exception.UnauthorizedAccessException;
import com.postmanager.model.entity.CommentEntity;
import com.postmanager.model.entity.PostEntity;
import com.postmanager.model.entity.UserEntity;
import com.postmanager.repository.jdbc.PostDao;
import com.postmanager.repository.jpa.PostRepository;
import com.postmanager.repository.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Service
public class PostService {

    private PostRepository postRepository;

    private UserRepository userRepository;

    private PostDao postDao;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, PostDao postDao) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postDao = postDao;
    }

    public List<PostEntity> getAllPosts() {
        return this.postRepository.findByVisibleTrue();
    }

    public List<PostEntity> getOwnPosts(String username) {

        return this.postRepository.findByVisibleTrueAndUser_Username(username);
    }

    public List<PostEntity> getPostsBytitleSecure(String title) {

        try {
            return this.postDao.findPostsSecure(title);
        } catch (UnsupportedEncodingException e) {
            return new ArrayList<>();
        }
    }

    public List<PostEntity> getPostsBytitleInSecure(String title) {

        try {
            return this.postDao.findPostsInSecure(title);
        } catch (UnsupportedEncodingException e) {
            return new ArrayList<>();
        }
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


    public PostEntity createPostInSecure(PostEntity postEntity, String username) {
        return this.fetchUserAndCreatePost(postEntity,username, p-> {
            try {
                return this.postDao.save(p);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }
        });

    }

    @Transactional
    public PostEntity createPostSecure(PostEntity postEntity, String username) {
       return this.fetchUserAndCreatePost(postEntity,username, p-> this.postRepository.save(p));
    }

    public PostEntity fetchUserAndCreatePost(PostEntity postEntity, String username, Function<PostEntity, PostEntity> function) {
        UserEntity user = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        postEntity.setUser(user);
        return function.apply(postEntity);
    }

    @Transactional
    public void deletePost(UUID postId) {
        this.postRepository.deleteById(postId);
    }
}
