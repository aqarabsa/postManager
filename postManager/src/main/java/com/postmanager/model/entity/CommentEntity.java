package com.postmanager.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity(name="COMMENT")
@EqualsAndHashCode(of ="id")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentEntity {

    public CommentEntity(String content, PostEntity post, UserEntity user) {
        this.content = content;
        this.post = post;
        this.user = user;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID id;
    
    @OneToOne
    @JoinColumn(name="COMMENT_USER")
    private UserEntity user;

    @Column(name = "CONTENT")
    private String content;

    @ManyToOne
    @JoinColumn(name="COMMENT_POST")
    private PostEntity post;
}
