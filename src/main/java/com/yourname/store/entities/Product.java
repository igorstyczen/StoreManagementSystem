package com.yourname.store.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import java.math.BigDecimal;

@Entity
@Table(name = "products")


public class Product extends StockableItem {
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private BigDecimal price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
