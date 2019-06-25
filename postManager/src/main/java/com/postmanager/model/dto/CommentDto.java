package com.postmanager.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@EqualsAndHashCode(of ="id")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDto {

    private UUID id;

    @NotNull(message = "Content is required")
    private String content;
}
