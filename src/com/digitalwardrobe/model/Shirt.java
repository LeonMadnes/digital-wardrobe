package com.digitalwardrobe.model;

public class Shirt extends ClothingItem {
    public Shirt(String name, String color, String season,
                 String style, String size, String material) {
        super(name, "Shirt", color, season, style, size, material);
    }
}
