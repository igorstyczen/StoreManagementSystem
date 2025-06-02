package com.yourname.store.entities;

public abstract class StockableItem extends Item {
    protected int stock;
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}
