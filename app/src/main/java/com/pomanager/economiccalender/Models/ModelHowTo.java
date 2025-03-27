package com.pomanager.economiccalender.Models;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class ModelHowTo implements Serializable {
    String title,description;
    Drawable drawable;

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

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
}
