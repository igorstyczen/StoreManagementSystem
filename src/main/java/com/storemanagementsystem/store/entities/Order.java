package com.storemanagementsystem.store.entities;

import java.time.LocalDate;

public class Order extends BaseEntity {
    private int customerId;
    private LocalDate orderDate;

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }
}
