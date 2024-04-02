package org.example.mapper;

import org.example.model.dto.CommentDto;
import org.example.model.dto.PostDto;
import org.example.model.entity.Comment;
import org.example.model.entity.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostMapperTest {

    private PostMapper postMapper;

    @BeforeEach
    public void set() {
        this.postMapper = Mappers.getMapper(PostMapper.class);
    }

    @Test
    public void shouldConvertToDto() {
        Post post = Post.builder()
            .id(123L)
            .title("Test")
            .content("Test")
            .build();
        Comment comment = Comment.builder()
            .id(5L)
            .text("comment")
            .author("author")
            .postId(post.getId())
            .build();

        post.getComments().add(comment);

        PostDto postDto = postMapper.toDto(post);

        assertEquals(post.getId(), postDto.id());
        assertEquals(post.getContent(), postDto.content());
        assertEquals(post.getTitle(), postDto.title());
        CommentDto commentDto = postDto.comments().get(0);
        assertEquals(commentDto.id(), comment.getId());
        assertEquals(commentDto.postId(), comment.getPostId());
        assertEquals(commentDto.author(), comment.getAuthor());
        assertEquals(commentDto.text(), comment.getText());
    }

    @Test
    public void shouldConvertToEntity() {
        CommentDto commentDto = new CommentDto(10L, "test", "test", 129L);
        PostDto postDto = new PostDto(129L, "test", "test", List.of(commentDto));

        Post post = postMapper.toEntity(postDto);

        assertEquals(postDto.id(), post.getId());
        assertEquals(postDto.content(), post.getContent());
        assertEquals(postDto.title(), post.getTitle());
        assertEquals(1, post.getComments().size());
        Comment comment = post.getComments().get(0);
        assertEquals(commentDto.id(), comment.getId());
        assertEquals(commentDto.postId(), comment.getPostId());
        assertEquals(commentDto.author(), comment.getAuthor());
        assertEquals(commentDto.text(), comment.getText());
    }
}
