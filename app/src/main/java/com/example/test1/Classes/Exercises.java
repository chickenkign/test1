package com.example.test1.Classes;

import android.provider.MediaStore;

import java.io.Serializable;

public class Exercises  {
    private String id;
    private String name;
    private String about;
    private String description;
    private String instruction;
    private String warning;
    private String linkVideo;
    private String photo;


    public Exercises(String id, String name, String about, String description, String instruction, String warning, String linkVideo, String photo) {
        this.id = id;
        this.name = name;
        this.about = about;
        this.description = description;
        this.instruction = instruction;
        this.warning = warning;
        this.linkVideo = linkVideo;
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public String getLinkVideo() {
        return linkVideo;
    }

    public void setLinkVideo(String linkVideo) {
        this.linkVideo = linkVideo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Exercises{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", about='" + about + '\'' +
                ", description='" + description + '\'' +
                ", instruction='" + instruction + '\'' +
                ", warning='" + warning + '\'' +
                ", linkVideo='" + linkVideo + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
