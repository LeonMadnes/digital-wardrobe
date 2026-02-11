package com.digitalwardrobe.model;

public class ClothingItem {
    protected String name;
    private String  type;
    private String color;
    private String season; // Winter, Summer, Fall, Spring or Any
    private String style; // Casual, Sporty, Formal, Streetwear or Any
    private String size;
    private String material;

    public ClothingItem(String name, String type, String color, String season, String style, String size, String material) {
        this.name = name;
        this.type = type;
        this.color = color;
        this.season = season;
        this.style = style;
        this.size = size;
        this.material = material;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getType() {
        return type;
    }

    public String getColor() {
        return color;
    }

    public String getSeason() {
        return season;
    }

    public String getStyle() {
        return style;
    }

    public String getSize() { return size; }

    public String getMaterial() {
        return material;
    }

    @Override
    public String toString() {
        return name + " (" + type + ", " + color + ", " + season + ", " + style +
                ", " + size + ", " + material + ")";
    }

    public String serialize() {
        return getType() + "|" + name + "|" + color + "|" + season + "|" +
                style + "|" + size + "|" + material;
    }

    public static ClothingItem deserialize(String line) {
        try {
            String[] p = line.split("\\|");
            String type     = p[0];
            String name     = p[1];
            String color    = p[2];
            String season = p[3];
            String style    = p[4];
            String size   = p[5];
            String material     = p[6];

            switch (type) {
                case "Shirt":  return new Shirt(name, color, season, style, size, material);
                case "Pants":  return new Pants(name, color, season, style, size, material);
                case "Shoes":  return new Shoes(name, color, season, style, size, material);
                case "Jacket": return new Jacket(name, color, season, style, size, material);
                default: return null;
            }

        } catch (Exception e) {
            return null;
        }
    }
}
