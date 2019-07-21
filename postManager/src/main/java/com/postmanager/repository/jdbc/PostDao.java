package com.postmanager.repository.jdbc;

import com.postmanager.model.entity.PostEntity;
import com.postmanager.util.UuidAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class PostDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public PostEntity save(PostEntity postEntity) throws UnsupportedEncodingException {
        UUID postId = UUID.randomUUID();
        jdbcTemplate.query(
                "SELECT id FROM post where ?", new Object[] { "1" },
                (rs, rowNum) -> new String(rs.getString("id"))
        ).forEach(p -> System.out.println(p));
        jdbcTemplate.batchUpdate("INSERT INTO POST(ID,TITLE,CONTENT, POST_USER) VALUES ('"+ postId.toString().replace("-","")+"','"+postEntity.getTitle()+"','"+postEntity.getContent()+"','"+postEntity.getUser().getId().toString().replace("-","")+"')");
        postEntity.setId(postId);
        return postEntity;
    }

    public List<PostEntity> findPostsSecure(String title) throws UnsupportedEncodingException {
        List<PostEntity> resultList = new ArrayList<>();
        jdbcTemplate.query(
                "SELECT id, title, content, visible FROM post where visible = true and title = ? ", new Object[] {  title },
                (rs, rowNum) -> {
                    String id = rs.getString("id");
//

                   PostEntity postEntity = new PostEntity(UUID.randomUUID(), null, rs.getString("title"), rs.getString("content"), true, null);
                    return postEntity;
                }).forEach(p -> resultList.add(p));

        return resultList;
    }

    public List<PostEntity> findPostsInSecure(String title) throws UnsupportedEncodingException {
        List<PostEntity> resultList = new ArrayList<>();
        jdbcTemplate.query(
                "SELECT id, title, content, visible FROM post where visible = true and title = '"+title+"' ", new Object[] {  },
                (rs, rowNum) -> {
                    PostEntity postEntity = new PostEntity(UUID.randomUUID(), null, rs.getString("title"), rs.getString("content"), true, null);
                    return postEntity;
                }).forEach(p -> resultList.add(p));

        return resultList;
    }

}
