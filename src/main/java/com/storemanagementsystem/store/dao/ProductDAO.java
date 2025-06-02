package com.storemanagementsystem.store.dao;

import com.storemanagementsystem.store.entities.RetailProduct;
import java.util.List;

public interface ProductDAO extends DAO<RetailProduct> {
    List<RetailProduct> findByName(String substring) throws DAOException;
}
