package com.digitalwardrobe.model;

public class ClothingItem {
    private String itemId;
    private String name;
    private String category;
    private String color;
    private String pattern;
    private String material;
    private String photoPath;

    public ClothingItem(String name, String category, String color, String pattern, String material) {
        this.name = name;
        this.category = category;
        this.color = color;
        this.pattern = pattern;
        this.material = material;
    }

}
