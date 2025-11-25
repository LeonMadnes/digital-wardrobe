package com.digitalwardrobe.service;

import com.digitalwardrobe.model.ClothingItem;
import com.digitalwardrobe.model.Outfit;

import java.util.List;
import java.util.ArrayList;

public class WardrobeService {
    private List<ClothingItem> items = new ArrayList<>();
    private List<Outfit> outfits = new ArrayList<>();

    public void addItem(ClothingItem item) {
        items.add(item);
    }

    public List<ClothingItem> getAllItems() {
        return items;
    }

    public Outfit createOutfit(List<ClothingItem> selectedItems) {
        Outfit outfit = new Outfit();
        for (ClothingItem item : selectedItems) {
            outfit.addItem(item);
        }
        outfits.add(outfit);
        return outfit;
    }

    public List<Outfit> getAllOutfits() {
        return outfits;
    }
}
