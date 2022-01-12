package com.codecademy.informationhandling.ContentItem;

public class ContentItem {

    //ID, Title, PublicationDate en mis of het een Module of Webcast is
    private int id;
    private String title;
    private String publicationDate;

    public ContentItem(int id, String title, String publicationDate) {
        this.id = id;
        this.title = title;
        this.publicationDate = publicationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

}
