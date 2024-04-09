package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.mapper.CommentMapper;
import org.example.model.dto.CommentDto;
import org.example.model.entity.CommentEntity;
import org.example.repository.CommentRepository;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);

    public List<CommentDto> getAllComments() {
        return commentRepository.findAll().stream()
            .map(commentMapper::toCommentDto)
            .toList();
    }

    public Optional<CommentDto> getCommentById(Long id) {
        return commentRepository.findById(id)
            .map(commentMapper::toCommentDto);
    }

    public List<CommentDto> getCommentByPostId(Long id) {
        return commentRepository.findByPostId(id).stream()
            .map(commentMapper::toCommentDto)
            .toList();
    }

    public CommentDto saveComment(final CommentDto commentDto) {
        final CommentEntity commentEntity = commentMapper.toComment(commentDto);
        commentRepository.save(commentEntity);

        return commentMapper.toCommentDto(commentEntity);
    }

    public void deleteCommentById(Long id) {
        commentRepository.deleteById(id);
    }
}
