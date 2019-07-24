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
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/api/secure/posts")
public class SecurePostController extends InSecurePostController{


    public SecurePostController(PostService postService, ModelMapper modelMapper, JwtTokenProvider jwtTokenProvider) {
       super(postService, modelMapper, jwtTokenProvider);
    }

    @Override
    public ResponseEntity<List<PostDto>> getPostsByttile(@PathVariable("title") Optional<String> title, HttpServletRequest request) {
        return ResponseEntity.ok(this.postService.getPostsBytitleSecure(title.orElse("")).stream().map(p->this.modelMapper.map(p, PostDto.class)).collect(Collectors.toList()));

    }

    @Override
    public ResponseEntity<PostDto> editPost(@PathVariable("postId")UUID postId, @RequestBody PostDto post, HttpServletRequest request) {
        System.out.println("Targeting secure method");
        this.postService.verifyExists(postId);
        this.postService.verifyOwner(postId, this.jwtTokenProvider.getUsername(request.getHeader("Authorization")));
        return super.editPost(postId, post, request);
    }

    @Override
    public ResponseEntity<String> deletePost(@PathVariable("postId")UUID postId, HttpServletRequest request) {
        this.postService.verifyExists(postId);
        this.postService.verifyOwner(postId, this.jwtTokenProvider.getUsername(request.getHeader("Authorization")));
        this.postService.deletePost(postId);
        return ResponseEntity.ok("success");
    }
}
