package com.sunstone.example.ecommercewrap;

/**
 * Created by sunstone2 on 8/18/2015.
 */
public class ShoppingProduct {
    private String mName;
    private String mDescription;
    private String mImgSrc;

    public ShoppingProduct(String name, String description, String imgSrc) {
        mName = name;
        mDescription = description;
        mImgSrc = imgSrc;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getImgSrc() {
        return mImgSrc;
    }

    public void setImgSrc(String mImgSrc) {
        this.mImgSrc = mImgSrc;
    }
}
