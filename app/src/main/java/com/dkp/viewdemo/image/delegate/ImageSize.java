package com.dkp.viewdemo.image.delegate;

/**
 * Created by shiming on 2016/10/26.
 */

public final class ImageSize {
    private final static String SEPARATOR = "x";

    private int width;
    private int height;

    public ImageSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(width).append(SEPARATOR).append(height).toString();
    }
}
