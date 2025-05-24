package com.example.kurgerbingfinal;

public class ItemChoice {
    private final String name;
    private final double price;
    private final String nutritionLink;
    private final int imageId;

    public ItemChoice(String name, double price, String nutritionLink, int imageId) {
        this.name = name;
        this.price = price;
        this.nutritionLink = nutritionLink;
        this.imageId = imageId;
    }

    public Item createItem(int quantity) {
        return new Item(name, quantity, price);
    }

    public String getNutritionLink() {
        return nutritionLink;
    }
    public int getImageId() {
        return imageId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
