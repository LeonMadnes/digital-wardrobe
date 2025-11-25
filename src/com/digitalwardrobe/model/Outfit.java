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
}
