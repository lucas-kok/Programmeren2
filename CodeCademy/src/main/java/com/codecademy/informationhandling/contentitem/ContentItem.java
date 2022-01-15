package com.codecademy.informationhandling.contentitem;

public class ContentItem {
    private final int id;
    private final String title;
    private final String publicationDate;

    public ContentItem(int id, String title, String publicationDate) {
        this.id = id;
        this.title = title;
        this.publicationDate = publicationDate;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    @Override
    public String toString() {
        return "ContentItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", publicationDate='" + publicationDate + '\'' +
                '}';
    }
}
