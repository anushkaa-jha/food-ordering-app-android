package com.example.kurgerbingfinal;

import java.util.UUID;

// We need to create an Item class in order to be able to list the ordered items effectively
public class Item {
    private UUID itemID = UUID.randomUUID(); // The unique id of the item
    private String name;
    private int itemCnt; // Number of items ordered
    private double pricePerUnit; // The price of the item

    // Constructor
    public Item(String name, int cnt, double pricePerUnit) {
        this.name = name;
        this.itemCnt = cnt;
        this.pricePerUnit = pricePerUnit;
    }

    // Accessors
    public String getName() {
        return name;
    }

    public UUID getItemID() {
        return itemID;
    }

    public int getItemCnt() {
        return itemCnt;
    }

    public double getTotalPrice() {
        return pricePerUnit * itemCnt;
    }
}
