package org.example.model.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public class CommentEntity {
    private Long id;
    private String author;
    private String text;
    private Long postId;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CommentEntity commentEntity = (CommentEntity) o;
        return Objects.equals(id, commentEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
