package com.dcoders.satishkumar.g.moviesappstage2.modelClasses;

import java.io.Serializable;

public class ReviewData implements Serializable{
    private String author;

    public ReviewData(String author, String comment) {
        this.author = author;
        this.comment = comment;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    private String comment;
}
