package com.storemanagementsystem.store.gui;

import com.storemanagementsystem.store.dao.DAOException;
import com.storemanagementsystem.store.dao.impl.ProductDAOImpl;
import com.storemanagementsystem.store.entities.RetailProduct;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ProductTableModel extends AbstractTableModel {
    private final String[] columns = {"ID", "Nazwa", "Cena", "Stan"};
    private List<RetailProduct> data;

    public ProductTableModel() {
        refresh();
    }

    public void refresh() {
        try {
            data = new ProductDAOImpl().findAll();
            fireTableDataChanged();
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getRowCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int col) {
        return columns[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        if (data == null || row >= data.size()) return null;
        RetailProduct p = data.get(row);
        return switch (col) {
            case 0 -> p.getId();
            case 1 -> p.getName();
            case 2 -> p.getPrice();
            case 3 -> p.getStock();
            default -> null;
        };
    }

    public RetailProduct getProductAt(int row) {
        return data.get(row);
    }
}