package com.samsung.muhammad.polisi.product;

/**
 * Created by muhammad on 15/08/17.
 */

public class Product {
    private int imageId;
    private String title;
    private String description;

    public Product(int imageId, String title) {
        this.imageId = imageId;
        this.title = title;
        this.description = description;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
