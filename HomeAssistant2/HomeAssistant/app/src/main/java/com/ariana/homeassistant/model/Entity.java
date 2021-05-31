package com.ariana.homeassistant.model;

public class Entity {
    String Text;
    int Image;

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public Entity(String text, int image) {
        Text = text;
        Image = image;
    }
}
