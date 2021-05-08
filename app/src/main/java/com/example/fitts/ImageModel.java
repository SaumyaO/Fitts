package com.example.fitts;

public class ImageModel {

    private int x,y;
    private int image_drawable;
    ImageModel(int x, int y, int d)
    {
        this.x=x;
        this.y=y;
        this.image_drawable=d;
    }
    public int getPostion() {
        return x;
    }

    public void setPostion(int postion) {
        this.x = postion;
    }

    public int getImage_drawable() {
        return image_drawable;
    }

    public void setImage_drawable(int image_drawable) {
        this.image_drawable = image_drawable;
    }
}