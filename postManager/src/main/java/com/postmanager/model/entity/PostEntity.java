package com.postmanager.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name="POST")
@EqualsAndHashCode(of ="id")
@Data
@ToString
@AllArgsConstructor()
@NoArgsConstructor
public class PostEntity {

    public PostEntity(String title, String content, UserEntity user, boolean visible) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.visible = visible;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID id;
    
    @OneToOne
    @JoinColumn(name="POST_USER")
    private UserEntity user;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT", length = 2000)
    private String content;

    @Column(name="VISIBLE")
    private boolean visible = true;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<CommentEntity> commentList = new ArrayList<>();
}
