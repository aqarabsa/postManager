package com.postmanager.controller;

import com.postmanager.model.dto.CommentDto;
import com.postmanager.model.dto.PostDto;
import com.postmanager.model.entity.CommentEntity;
import com.postmanager.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/api/posts")
public class PostController {

    private PostService postService;

    private ModelMapper modelMapper;

    @Autowired
    public PostController(PostService postService,  ModelMapper modelMapper) {
        this.postService = postService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(path="/all")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return ResponseEntity.ok(this.postService.getAllPosts().stream().map(p->this.modelMapper.map(p, PostDto.class)).collect(Collectors.toList()));

    }

    @GetMapping(path="/own")
    public ResponseEntity<List<PostDto>> getOwnPosts() {
        return ResponseEntity.ok(this.postService.getOwnPosts().stream().map(p->this.modelMapper.map(p, PostDto.class)).collect(Collectors.toList()));

    }

    @PutMapping(path= "/{postId}/comment")
    public ResponseEntity<PostDto> addComment(@PathVariable("postId")UUID postId, @RequestBody CommentDto comment) {
        return ResponseEntity.ok(this.modelMapper.map(this.postService.addCommentToPost(postId, this.modelMapper.map(comment, CommentEntity.class)), PostDto.class));
    }
}
