package org.example.model.entity;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Builder
public class PostEntity {
    private Long id;
    private String title;
    private String content;
    @Builder.Default
    private List<CommentEntity> comments = new ArrayList<>();

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PostEntity postEntity = (PostEntity) o;
        return Objects.equals(id, postEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
