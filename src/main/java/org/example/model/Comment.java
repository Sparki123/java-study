package org.example.model;

import lombok.Data;

@Data
public class Comment {
    private int id;
    private String author;
    private String comment;

    public Comment(int id, String author, String comment) {
        this.id = id;
        this.author = author;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
