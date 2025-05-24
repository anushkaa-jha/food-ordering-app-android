package com.example.kurgerbingfinal;

import androidx.annotation.NonNull;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Cart implements Iterable<Item> {
    private static final Cart INSTANCE = new Cart();

    private Cart() {
    }

    private final List<Item> items = new LinkedList<>();

    public static Cart getInstance() {
        return INSTANCE;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public int size() {
        int size = 0;

        for (Item item : items)
            size += item.getItemCnt();

        return size;
    }

    @NonNull
    @Override
    public Iterator<Item> iterator() {
        return items.iterator();
    }
}
