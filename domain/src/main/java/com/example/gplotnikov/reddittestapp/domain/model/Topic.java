package com.example.gplotnikov.reddittestapp.domain.model;

import java.util.Date;

public class Topic {
    private final String title;
    private final String author;
    private final Date creationDate;
    private final String thumbnailUrl;
    private final String imageUrl;
    private final int commentsCount;

    public Topic(String title, String author, Date creationDate, String thumbnailUrl, String imageUrl, int commentsCount) {
        this.title = title;
        this.author = author;
        this.creationDate = creationDate;
        this.thumbnailUrl = thumbnailUrl;
        this.imageUrl = imageUrl;
        this.commentsCount = commentsCount;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Topic topic = (Topic) o;

        if (commentsCount != topic.commentsCount) return false;
        if (!title.equals(topic.title)) return false;
        if (!author.equals(topic.author)) return false;
        if (!creationDate.equals(topic.creationDate)) return false;
        if (!thumbnailUrl.equals(topic.thumbnailUrl)) return false;
        return imageUrl.equals(topic.imageUrl);

    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + creationDate.hashCode();
        result = 31 * result + thumbnailUrl.hashCode();
        result = 31 * result + imageUrl.hashCode();
        result = 31 * result + commentsCount;
        return result;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", creationDate=" + creationDate +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", commentsCount=" + commentsCount +
                '}';
    }
}