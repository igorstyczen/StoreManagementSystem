package com.yourname.store.dao.impl;

import com.yourname.store.dao.DAOException;
import com.yourname.store. dao.ProductDAO;
import com.yourname.store.entities.RetailProduct;
import com.yourname.store.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {
    @Override
    public RetailProduct findById(int id) throws DAOException {
        String sql = "SELECT * FROM products WHERE product_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DAOException("findById failed", e);
        }
    }

    @Override
    public List<RetailProduct> findAll() throws DAOException {
        String sql = "SELECT * FROM products";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            List<RetailProduct> list = new ArrayList<>();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DAOException("findAll failed", e);
        }
    }

    @Override
    public void create(RetailProduct p) throws DAOException {
        String sql = "INSERT INTO products(name, price, stock) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getName());
            ps.setBigDecimal(2, p.getPrice());
            ps.setInt(3, p.getStock());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) p.setId(keys.getInt(1));
        } catch (SQLException e) {
            throw new DAOException("create failed", e);
        }
    }

    @Override
    public void update(RetailProduct p) throws DAOException {
        String sql = "UPDATE products SET name=?, price=?, stock=? WHERE product_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setBigDecimal(2, p.getPrice());
            ps.setInt(3, p.getStock());
            ps.setInt(4, p.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("update failed", e);
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        String sql = "DELETE FROM products WHERE product_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("delete failed", e);
        }
    }

    @Override
    public List<RetailProduct> findByName(String substring) throws DAOException {
        String sql = "SELECT * FROM products WHERE name LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + substring + "%");
            ResultSet rs = ps.executeQuery();
            List<RetailProduct> list = new ArrayList<>();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DAOException("findByName failed", e);
        }
    }

    private RetailProduct mapRow(ResultSet rs) throws SQLException {
        RetailProduct p = new RetailProduct();
        p.setId(rs.getInt("product_id"));
        p.setName(rs.getString("name"));
        p.setPrice(rs.getBigDecimal("price"));
        p.setStock(rs.getInt("stock"));
        return p;
    }
    public void delete(RetailProduct product) throws DAOException {
        delete(product.getId());
    }
}
