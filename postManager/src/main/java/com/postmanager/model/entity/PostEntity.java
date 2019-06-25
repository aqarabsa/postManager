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

    public PostEntity(String title, String content) {
        this.title = title;
        this.content = content;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT", length = 2000)
    private String content;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<CommentEntity> commentList = new ArrayList<>();
}
