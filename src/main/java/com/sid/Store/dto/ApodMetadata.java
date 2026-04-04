package com.sid.Store.dto;

public class ApodMetadata {
    private String title;
    private String explanation;

    public ApodMetadata() {}

    public ApodMetadata(String title, String explanation) {
        this.title = title;
        this.explanation = explanation;
    }

    public String getTitle() { return title; }
    public String getExplanation() { return explanation; }

    public void setTitle(String title) { this.title = title; }
    public void setExplanation(String explanation) { this.explanation = explanation; }
}