package com.digitalwardrobe.model;

public class Jacket extends ClothingItem {
    public Jacket(String name, String color, String material,
                  String season, String style, String size) {
        super(name, "Jacket", color, material, season, style, size);
    }
}
