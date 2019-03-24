package com.example.androidtabletop.Model;

public class Game {
    private String Name;
    private String Image;
    private String Description;
    private String Date;
    private String CategoryId;

    public Game() {
    }

    public Game(String name, String image, String description, String date, String categoryId) {
        Name = name;
        Image = image;
        Description = description;
        Date = date;
        CategoryId = categoryId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDate() {
        return Date;
    }

    public void setData(String date) {
        Date = date;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }
}
