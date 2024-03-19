package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.repository.CommentRepository;

@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
}
