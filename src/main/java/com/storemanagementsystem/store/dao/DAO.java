package com.storemanagementsystem.store.dao;

import java.util.List;

public interface DAO<T> {
    T findById(int id) throws DAOException;
    List<T> findAll() throws DAOException;
    void create(T entity) throws DAOException;
    void update(T entity) throws DAOException;
    void delete(int id) throws DAOException;
}
