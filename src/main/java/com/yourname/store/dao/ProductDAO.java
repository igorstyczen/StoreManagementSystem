package com.yourname.store.dao;

import com.yourname.store.entities.RetailProduct;
import java.util.List;

public interface ProductDAO extends DAO<RetailProduct> {
    List<RetailProduct> findByName(String substring) throws DAOException;
}
