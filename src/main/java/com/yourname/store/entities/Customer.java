package com.yourname.store.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import java.math.BigDecimal;

@Entity
@Table(name = "customers")

public class Customer extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "wallet_amount")
    private BigDecimal walletAmount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAmount(BigDecimal walletAmount) {
        this.walletAmount = walletAmount;
    }
}
