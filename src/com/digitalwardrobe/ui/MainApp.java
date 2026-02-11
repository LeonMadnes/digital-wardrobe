package com.digitalwardrobe.ui;

import com.digitalwardrobe.model.*;
import com.digitalwardrobe.service.WardrobeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class MainApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final WardrobeService wardrobeService = new WardrobeService();

    // Wardrobe Menu
    public static void main(String[] args) {
        wardrobeService.loadFromFile("wardrobe.txt");

        while(true) {
            System.out.println("======================================");
            System.out.println("        DIGITAL WARDROBE MENU        ");
            System.out.println("======================================");
            System.out.println("1. Add Clothing Item");
            System.out.println("2. View Wardrobe");
            System.out.println("3. Generate Outfit");
            System.out.println("4. View Saved Outfits");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addClothing();
                    break;
                case "2":
                    viewWardrobe();
                    break;
                case "3":
                    generateOutfit();
                    break;
                case "4":
                    wardrobeService.showSavedOutfits();
                    pause();
                    break;
                case "0":
                    wardrobeService.saveToFile("wardrobe.txt");
                    System.out.println("Exiting Digital Wardrobe...");
                    return;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    // Add clothing method
    private static void addClothing() {
        System.out.println("\n--- Add clothing item ---");
        String name = readNonEmpty("Enter clothing name (ex. Nike stripped shirt): ");

        // Choose clothing type
        System.out.println("Choose clothing type:");
        System.out.println("1. Shirt");
        System.out.println("2. Pants");
        System.out.println("3. Shoes");
        System.out.println("4. Jacket");
        int typeChoice = readIntInRange("Your choice: ", 1, 4);

        String color = readNonEmpty("Enter Color: ");
        String season = readNonEmpty("Enter season (Winter, Summer, Fall, Spring, Any): ");
        String style = readNonEmpty("Enter style (Casual, Sporty, Formal, Streetwear, Any): ");
        String size = readNonEmpty("Enter size: ");
        String material = readNonEmpty("Enter material (Silk, Wool, Cotton, Polyester, Leather, Etc...): ");

        ClothingItem item = switch (typeChoice) {
            case 1 -> new Shirt(name, color, season, style, size, material);
            case 2 -> new Pants(name, color, season, style, size, material);
            case 3 -> new Shoes(name, color, season, style, size, material);
            case 4 -> new Jacket(name, color, season, style, size, material);
            default -> null;
        };

        wardrobeService.addItem(item);
        wardrobeService.saveToFile("wardrobe.txt");
        System.out.println("Added: " + item);
    }

    // View wardrobe method
    private static void viewWardrobe() {
        System.out.println("\n--- Your Wardrobe ---");

        List<ClothingItem> items = wardrobeService.getAllItems();

        if(items.isEmpty()) {
            System.out.println("Wardrobe is empty!");
            pause();
            return;
        }

        for (int i = 0; i < items.size(); i++) {
            System.out.println((i+1) + ". " + items.get(i));
        }

        System.out.println("\nOptions:");
        System.out.println("1. Edit an item");
        System.out.println("2. Delete an item");
        System.out.println("3. Back to menu");
        System.out.print("Choose: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                editWardrobeItem();
                break;
            case "2":
                deleteWardrobeItem();
                break;
            case "3":
                return;
            default:
                System.out.println("Invalid choice.");
        }

        pause();
    }

    // Generate outfit method
    private static void generateOutfit() {
        System.out.println("\n--- Generate Outfit ---");

        // Season filter
        System.out.println("Choose season filter:");
        System.out.println("1. Any");
        System.out.println("2. Summer");
        System.out.println("3. Winter");
        System.out.println("4. Fall");
        System.out.println("5. Spring");
        int seasonChoice = readIntInRange("Enter choice: ", 1, 5);

        String seasonFilter = switch (seasonChoice) {
            case 2 -> "Summer";
            case 3 -> "Winter";
            case 4 -> "Fall";
            case 5 -> "Spring";
            default -> "Any";
        };

        // Style filter
        System.out.println("\nChoose style:");
        System.out.println("1. Any");
        System.out.println("2. Casual");
        System.out.println("3. Formal");
        System.out.println("4. Sport");
        System.out.println("5. Streetwear");
        int styleChoice = readIntInRange("Enter choice: ", 1, 5);

        String styleFilter = switch (styleChoice) {
            case 2 -> "Casual";
            case 3 -> "Formal";
            case 4 -> "Sport";
            case 5 -> "Streetwear";
            default -> "Any";
        };

        System.out.println("Season chosen: " + seasonFilter + "\n Style chosen: " + styleFilter);

        Random rand = new Random();

        ArrayList<ClothingItem> filtered = new ArrayList<>();

        for (ClothingItem item : wardrobeService.getAllItems()) {
            boolean matchesSeason = seasonFilter.equals("Any")
                    || item.getSeason().equalsIgnoreCase(seasonFilter);

            boolean matchesStyle = styleFilter.equals("Any")
                    || item.getStyle().equalsIgnoreCase(styleFilter);

            if (matchesSeason && matchesStyle) {
                filtered.add(item);
            }
        }

        if (filtered.isEmpty()) {
            System.out.println("\nNo items match your filters.");
            return;
        }

        ArrayList<ClothingItem> shirts = new ArrayList<>();
        ArrayList<ClothingItem> pantsList = new ArrayList<>();
        ArrayList<ClothingItem> shoesList = new ArrayList<>();
        ArrayList<ClothingItem> jackets = new ArrayList<>();

        for (ClothingItem item : filtered) {
            switch (item.getType()) {
                case "Shirt": shirts.add(item); break;
                case "Pants": pantsList.add(item); break;
                case "Shoes": shoesList.add(item); break;
                case "Jacket": jackets.add(item); break;
            }
        }

        if (shirts.isEmpty() || pantsList.isEmpty() || shoesList.isEmpty()) {
            System.out.println("\nNot enough items to generate a full outfit.");
            return;
        }

        // pick random items
        ClothingItem shirt = shirts.get(rand.nextInt(shirts.size()));
        ClothingItem pants = pantsList.get(rand.nextInt(pantsList.size()));
        ClothingItem shoes = shoesList.get(rand.nextInt(shoesList.size()));

        ClothingItem jacket = null;
        if (!jackets.isEmpty() && rand.nextInt(100) < 50) { // 50% chance to include jacket!
            jacket = jackets.get(rand.nextInt(jackets.size()));
        }

        // Build outfit
        Outfit outfit = new Outfit();
        outfit.addItem(shirt);
        outfit.addItem(pants);
        outfit.addItem(shoes);
        if (jacket != null) outfit.addItem(jacket);

        // Display the Outfit
        System.out.println("\nGenerated Outfit:");
        System.out.println(outfit);

        if (readYesNo("\nSave this outfit?")) {
            wardrobeService.saveOutfit(outfit);
            System.out.println("Outfit saved!");
        }
    }

    private static int readIntInRange(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine());
                if (value >= min && value <= max) return value;
                System.out.println("Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    private static String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.println("Input cannot be empty. Try again.");
        }
    }

    private static boolean readYesNo(String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y")) return true;
            if (input.equals("n")) return false;
            System.out.println("Please enter 'y' or 'n'.");
        }
    }

    // Pause method to avoid instant transitions
    private static void pause() {
        System.out.println("\nPress ENTER to return...");
        scanner.nextLine();
    }

    // Edit item inside wardrobe method
    private static void editWardrobeItem() {
        int index = readIntInRange("Enter item number to edit: ", 1, wardrobeService.getAllItems().size()) - 1;

        ClothingItem item = wardrobeService.getItemByIndex(index);
        if (item == null) {
            System.out.println("Invalid item.");
            return;
        }

        System.out.println("Editing: " + item);

        System.out.print("New name (blank to keep same): ");
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) item.setName(newName);

        System.out.print("New color (blank to keep same): ");
        String newColor = scanner.nextLine();
        if (!newColor.isEmpty()) item.setColor(newColor);

        System.out.print("New season (Winter/Spring/Summer/Fall/Any): ");
        String newSeason = scanner.nextLine();
        if (!newSeason.isEmpty()) item.setSeason(newSeason);

        System.out.print("New style (Casual/Sporty/Formal/Streetwear/Any): ");
        String newStyle = scanner.nextLine();
        if (!newStyle.isEmpty()) item.setStyle(newStyle);

        System.out.print("New size (blank to keep same): ");
        String newSize = scanner.nextLine();
        if (!newSize.isEmpty()) item.setSize(newSize);

        System.out.print("New material (blank to keep same): ");
        String newMaterial = scanner.nextLine();
        if (!newMaterial.isEmpty()) item.setMaterial(newMaterial);

        System.out.println("Item updated!");
    }

    // Delete item inside wardrobe method
    private static void deleteWardrobeItem() {
        int index = readIntInRange("Enter item number to delete: ", 1, wardrobeService.getAllItems().size()) - 1;

        ClothingItem item = wardrobeService.getItemByIndex(index);
        if (item == null) {
            System.out.println("Invalid item.");
            return;
        }

        System.out.println("Deleting: " + item);
        wardrobeService.removeItem(index);
        System.out.println("Item removed.");
    }
}