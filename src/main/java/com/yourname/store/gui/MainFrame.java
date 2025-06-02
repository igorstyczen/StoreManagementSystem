package com.yourname.store.gui;

import com.yourname.store.dao.DAOException;
import com.yourname.store.dao.impl.ProductDAOImpl;
import com.yourname.store.entities.RetailProduct;
import com.yourname.store.util.DatabaseInitializer;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.math.BigDecimal;

public class MainFrame extends JFrame {
    private ProductTableModel tableModel;
    private JTable productTable;
    private JTextField nameField;
    private JTextField priceField;
    private JTextField stockField;
    private JButton addBtn;
    private JButton deleteBtn;

    private RetailProduct currentEditingProduct = null;

    public MainFrame() {
        setTitle("System zarządzania sklepem");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        getContentPane().setLayout(new BorderLayout());

        tableModel = new ProductTableModel();
        productTable = new JTable(tableModel);
        TableRowSorter<ProductTableModel> sorter = new TableRowSorter<>(tableModel);
        productTable.setRowSorter(sorter);

        JPanel form = new JPanel(new GridLayout(0, 2, 5, 5));
        nameField = new JTextField();
        priceField = new JTextField();
        stockField = new JTextField();
        addBtn = new JButton("Dodaj produkt");
        deleteBtn = new JButton("Usuń produkt");

        addBtn.addActionListener(e -> {
            if (currentEditingProduct == null) {
                addProduct();
            } else {
                updateProduct();
            }
        });

        deleteBtn.addActionListener(e -> deleteProduct());

        productTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow != -1) {
                    int modelRow = productTable.convertRowIndexToModel(selectedRow);
                    currentEditingProduct = tableModel.getProductAt(modelRow);
                    loadProductToForm(currentEditingProduct);
                    addBtn.setText("Zapisz zmiany");
                }
            }
        });

        form.add(new JLabel("Nazwa:"));
        form.add(nameField);
        form.add(new JLabel("Cena:"));
        form.add(priceField);
        form.add(new JLabel("Stan:"));
        form.add(stockField);
        form.add(addBtn);
        form.add(deleteBtn);

        getContentPane().add(new JScrollPane(productTable), BorderLayout.CENTER);
        getContentPane().add(form, BorderLayout.SOUTH);
    }

    private void addProduct() {
        try {
            String name = nameField.getText().trim();
            if (name.isEmpty()) throw new ValidationException("Nazwa jest wymagana.");
            BigDecimal price = new BigDecimal(priceField.getText());
            if (price.compareTo(BigDecimal.ZERO) <= 0) throw new ValidationException("Cena musi być > 0.");
            int stock = Integer.parseInt(stockField.getText());
            if (stock < 0) throw new ValidationException("Stan >= 0.");

            RetailProduct p = new RetailProduct();
            p.setName(name);
            p.setPrice(price);
            p.setStock(stock);
            new ProductDAOImpl().create(p);
            tableModel.refresh();
            clearForm();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Błędny format liczby", "Błąd", JOptionPane.ERROR_MESSAGE);
        } catch (ValidationException | DAOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateProduct() {
        try {
            if (currentEditingProduct == null) return;

            String name = nameField.getText().trim();
            if (name.isEmpty()) throw new ValidationException("Nazwa jest wymagana.");
            BigDecimal price = new BigDecimal(priceField.getText());
            if (price.compareTo(BigDecimal.ZERO) <= 0) throw new ValidationException("Cena musi być > 0.");
            int stock = Integer.parseInt(stockField.getText());
            if (stock < 0) throw new ValidationException("Stan >= 0.");

            currentEditingProduct.setName(name);
            currentEditingProduct.setPrice(price);
            currentEditingProduct.setStock(stock);

            new ProductDAOImpl().update(currentEditingProduct);
            tableModel.refresh();
            clearForm();
            currentEditingProduct = null;
            addBtn.setText("Dodaj produkt");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Błędny format liczby", "Błąd", JOptionPane.ERROR_MESSAGE);
        } catch (ValidationException | DAOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Wybierz produkt do usunięcia.", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int modelRow = productTable.convertRowIndexToModel(selectedRow);
        RetailProduct productToDelete = tableModel.getProductAt(modelRow);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Czy na pewno chcesz usunąć produkt: " + productToDelete.getName() + "?",
                "Potwierdź usunięcie", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Używamy delete po ID (albo po obiekcie, jeśli wolisz)
                new ProductDAOImpl().delete(productToDelete.getId());
                tableModel.refresh();
                clearForm();
                currentEditingProduct = null;
                addBtn.setText("Dodaj produkt");
            } catch (DAOException ex) {
                JOptionPane.showMessageDialog(this, "Błąd usuwania produktu: " + ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadProductToForm(RetailProduct product) {
        nameField.setText(product.getName());
        priceField.setText(product.getPrice().toString());
        stockField.setText(String.valueOf(product.getStock()));
    }

    private void clearForm() {
        nameField.setText("");
        priceField.setText("");
        stockField.setText("");
        productTable.clearSelection();
    }

    public static void main(String[] args) {
        DatabaseInitializer.init();
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}

// Klasa pomocnicza do walidacji formularza
class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
}