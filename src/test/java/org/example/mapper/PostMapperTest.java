package org.example.mapper;

import org.example.model.dto.PostDto;
import org.example.model.entity.Comment;
import org.example.model.entity.Post;
import org.example.support.TestDataProvider;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PostMapperTest {

    private final PostMapper postMapper = Mappers.getMapper(PostMapper.class);

    @Test
    public void shouldConvertToDto() {
        final Post post = Post.builder()
            .id(123L)
            .title("Test")
            .content("Test")
            .build();
        final Comment comment = Comment.builder()
            .id(5L)
            .text("comment")
            .author("author")
            .postId(post.getId())
            .build();

        post.getComments().add(comment);

        final PostDto postDto = postMapper.toPostDto(post);

//        assertEquals(post.getId(), postDto.id());
//        assertEquals(post.getContent(), postDto.content());
//        assertEquals(post.getTitle(), postDto.title());
//        CommentDto commentDto = postDto.comments().get(0);
//        assertEquals(commentDto.getId(), comment.getId());
//        assertEquals(commentDto.getPostId(), comment.getPostId());
//        assertEquals(commentDto.getAuthor(), comment.getAuthor());
//        assertEquals(commentDto.getText(), comment.getText());
    }

    @Test
    public void shouldConvertToEntity() {
        final PostDto postDto = TestDataProvider.preparePostDto()
            .title("321")
            .build();

        assertThat(postMapper.toEntityPost(postDto))
            .usingRecursiveComparison()
            .isEqualTo(Post.builder()
                .id(123L)
                .title("321")
                .content("Test")
                .comments(List.of(Comment.builder()
                    .id(10L)
                    .author("test")
                    .text("test")
                    .postId(129L)
                    .build()))
                .build());
    }

}
