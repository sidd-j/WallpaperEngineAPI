package com.sid.Store.dto;

public class NasaDto {
    private String title;
    private String explanation;
    private String imageUrl;

    public NasaDto(String title, String explanation, String imageUrl) {
        this.title = title;
        this.explanation = explanation;
        this.imageUrl = imageUrl;
    }

    public String getTitle() { return title; }
    public String getExplanation() { return explanation; }
    public String getImageUrl() { return imageUrl; }
}
