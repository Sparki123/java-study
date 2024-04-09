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
        List<CommentEntity> commentEntities = commentRepository.findAll();
        return commentEntities.stream()
            .map(commentMapper::toCommentDto)
            .toList();
    }

    public Optional<CommentDto> getCommentById(Long id) {
        Optional<CommentEntity> commentEntity = commentRepository.findById(id);
        return commentEntity.map(commentMapper::toCommentDto);
    }

    public List<CommentDto> getCommentByPostId(Long id) {
        List<CommentEntity> commentEntities = commentRepository.findByPostId(id);
        return commentEntities.stream()
            .map(commentMapper::toCommentDto)
            .toList();
    }

    public CommentDto saveComment(CommentDto commentDto) {
        CommentEntity commentEntity = commentMapper.toComment(commentDto);
        return commentMapper.toCommentDto(commentRepository.save(commentEntity));
    }

    public void deleteCommentById(Long id) {
        commentRepository.deleteById(id);
    }
}
