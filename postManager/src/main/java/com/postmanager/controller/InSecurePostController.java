package com.postmanager.controller;

import com.postmanager.model.dto.CommentDto;
import com.postmanager.model.dto.PostDto;
import com.postmanager.model.entity.CommentEntity;
import com.postmanager.model.entity.PostEntity;
import com.postmanager.service.PostService;
import com.postmanager.util.JwtTokenProvider;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/api/insecure/posts")
public class InSecurePostController {

    protected PostService postService;

    protected ModelMapper modelMapper;

    protected JwtTokenProvider jwtTokenProvider;

    @Autowired
    public InSecurePostController(PostService postService, ModelMapper modelMapper, JwtTokenProvider jwtTokenProvider) {
        this.postService = postService;
        this.modelMapper = modelMapper;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping(path="/all")
    public ResponseEntity<List<PostDto>> getAllPosts( HttpServletRequest request) {
        this.jwtTokenProvider.getUsername(request.getHeader("Authorization"));
        return ResponseEntity.ok(this.postService.getAllPosts().stream().map(p->this.modelMapper.map(p, PostDto.class)).collect(Collectors.toList()));

    }

    @GetMapping(path="/own")
    public ResponseEntity<List<PostDto>> getOwnPosts(HttpServletRequest request) {
        return ResponseEntity.ok(this.postService.getOwnPosts(this.jwtTokenProvider.getUsername(request.getHeader("Authorization"))).stream().map(p->this.modelMapper.map(p, PostDto.class)).collect(Collectors.toList()));

    }

    @GetMapping(path="/title/{title}")
    public ResponseEntity<List<PostDto>> getPostsByttile(@PathVariable("title") String title, HttpServletRequest request) {
        return ResponseEntity.ok(this.postService.getPostsBytitleInSecure(title).stream().map(p->this.modelMapper.map(p, PostDto.class)).collect(Collectors.toList()));

    }

    @PostMapping
    public ResponseEntity<PostDto> createPost( @RequestBody PostDto post, HttpServletRequest request) {
        return ResponseEntity.ok(this.modelMapper.map(this.postService.createPostInSecure( this.modelMapper.map(post, PostEntity.class), this.jwtTokenProvider.getUsername(request.getHeader("Authorization"))), PostDto.class));
    }

    @PutMapping(path= "/{postId}/comment")
    public ResponseEntity<PostDto> addComment(@PathVariable("postId")UUID postId, @RequestBody CommentDto comment) {
        return ResponseEntity.ok(this.modelMapper.map(this.postService.addCommentToPost(postId, this.modelMapper.map(comment, CommentEntity.class)), PostDto.class));
    }

    @PutMapping(path= "/{postId}")
    @Transactional
    public ResponseEntity<PostDto> editPost(@PathVariable("postId")UUID postId, @RequestBody PostDto post, HttpServletRequest request) {
        return ResponseEntity.ok(this.modelMapper.map(this.postService.updatePost(postId, this.modelMapper.map(post, PostEntity.class)), PostDto.class));
    }
}
