package org.example;

import org.example.model.Comment;
import org.example.model.Post;

import java.util.List;

import static org.example.PostRepository.getPostById;
import static org.example.PostRepository.getPosts;
import static org.example.PostRepository.addPost;
import static org.example.PostRepository.updatePost;
import static org.example.PostRepository.getPostWithComments;

import static org.example.CommentsRepository.addCommentToPost;
import static org.example.CommentsRepository.updateComment;
import static org.example.CommentsRepository.deleteComment;
import static org.example.CommentsRepository.getComments;

public class Main {
    public static void main(String[] args) {
        int postId = addPost("Заголовок 1", "Контент 1");
        int postId2 = addPost("Заголовок 2", "Контент 2");

        Post post = getPostById(postId);
        List<Post> posts = getPosts();
        updatePost(postId, "Updated Заголовок 2", "Updated Контент 2");
        System.out.println(getPostWithComments(postId));
        addCommentToPost(postId, "Kirill", "что это такое?");

        updateComment(1, "Updated Comment");
        deleteComment(1);
        System.out.println(getComments(postId));
    }
}