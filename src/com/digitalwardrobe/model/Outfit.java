package com.digitalwardrobe.model;

import java.util.List;
import java.util.ArrayList;

public class Outfit {
    private List<ClothingItem> items = new ArrayList<>();

    public void addItem(ClothingItem item) {
        items.add(item);
    }

    public List<ClothingItem> getItems() {
        return items;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Outfit: [");

        for (int i = 0; i < items.size(); i++) {
            sb.append(items.get(i).toString());
            if (i < items.size() - 1) sb.append(", ");
        }

        sb.append("]");
        return sb.toString();
    }
}

