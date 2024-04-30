package org.example.jdbc.service;

import lombok.RequiredArgsConstructor;
import org.example.jdbc.mapper.CommentMapper;
import org.example.jdbc.model.dto.CommentDto;
import org.example.jdbc.model.entity.CommentEntity;
import org.example.jdbc.repository.CommentRepository;
import org.mapstruct.factory.Mappers;

import java.util.List;

@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);

    public List<CommentDto> getAllComments() {
        return commentRepository.findAll().stream()
                .map(commentMapper::toCommentDto)
                .toList();
    }

    public CommentDto getCommentById(final Long id) {
        final CommentEntity comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Comment with id %s not found".formatted(id)));
        return commentMapper.toCommentDto(comment);
    }

    public List<CommentDto> getCommentByPostId(final Long id) {
        return commentRepository.findByPostId(id).stream()
                .map(commentMapper::toCommentDto)
                .toList();
    }

    public CommentDto saveComment(final CommentDto commentDto) {
        final CommentEntity commentEntity = commentMapper.toComment(commentDto);
        commentRepository.save(commentEntity);

        return commentMapper.toCommentDto(commentEntity);
    }

    public void deleteCommentById(final Long id) {
        commentRepository.deleteById(id);
    }
}
