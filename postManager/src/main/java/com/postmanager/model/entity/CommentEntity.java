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

    public CommentEntity(String content, PostEntity post) {
        this.content = content;
        this.post = post;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID id;

    @Column(name = "CONTENT")
    private String content;

    @ManyToOne
    @JoinColumn(name="COMMENT_POST")
    private PostEntity post;
}
