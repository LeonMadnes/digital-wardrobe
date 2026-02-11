package com.digitalwardrobe.service;

import com.digitalwardrobe.model.ClothingItem;
import com.digitalwardrobe.model.Outfit;

import java.io.*;
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

    public ClothingItem getItemByIndex(int index) {
        if (index < 0 || index >= items.size()) {
            return null;
        }
        return items.get(index);
    }

    public void removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
        }
    }

    // Load Clothing Items from File
    public void loadFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                ClothingItem item = ClothingItem.deserialize(line);
                if (item != null) items.add(item);
            }
            System.out.println("Wardrobe loaded.");
        } catch (IOException e) {
            System.out.println("No existing wardrobe file found. Starting fresh.");
        }
    }

    // Save Clothing Items to File
    public void saveToFile(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (ClothingItem item : items) {
                pw.println(item.serialize());
            }
            System.out.println("Wardrobe saved.");
        } catch (IOException e) {
            System.out.println("Error saving wardrobe.");
        }
    }

    // Save Individual Outfit
    public void saveOutfit(Outfit outfit) {
        try (FileWriter fw = new FileWriter("saved_outfits.txt", true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            bw.write(outfit.toString());
            bw.newLine();

        } catch (IOException e) {
            System.out.println("Error saving outfit: " + e.getMessage());
        }
    }

    // Show Saved Outfits
    public void showSavedOutfits() {
        File file = new File("saved_outfits.txt");

        if (!file.exists()) {
            System.out.println("\nNo saved outfits yet.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            System.out.println("\n--- Saved Outfits ---");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("----------------------");
        } catch (IOException e) {
            System.out.println("Error reading saved outfits: " + e.getMessage());
        }
    }

}
