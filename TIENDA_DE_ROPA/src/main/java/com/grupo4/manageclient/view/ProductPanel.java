/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.grupo4.manageclient.view;

import com.grupo4.manageclient.model.Product;
import com.grupo4.manageclient.service.ProductService;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author anamo
 */
public class ProductPanel extends javax.swing.JPanel {

    private final ProductService productService;

    public ProductPanel(ProductService productService) {
        this.productService = productService;
        initComponents();
        configureTable();
        bindEvents();
        refreshProductTable();
    }

    private void configureTable() {
        TableProduct.setModel(new DefaultTableModel(
                new Object[] { "Código", "Producto", "Talla", "Color", "Precio", "Stock" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        TableProduct.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void bindEvents() {
        btnCreateP.addActionListener(e -> clearForm());
        btnClearP.addActionListener(e -> clearForm());
        btnSaveProduct.addActionListener(e -> saveProduct());
        btnEditP.addActionListener(e -> updateProduct());
        btnDeleteP.addActionListener(e -> deleteProduct());

        TableProduct.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                loadSelectedProduct();
            }
        });
    }

    private void saveProduct() {
        try {
            productService.registerProduct(
                    parseInteger(txtIdProductField.getText(), "código"),
                    txtProductField.getText().trim(),
                    txtSizePField.getText().trim(),
                    txtColorPField.getText().trim(),
                    parseDouble(txtUnitPriceField.getText(), "precio"),
                    parseInteger(txtStockField.getText(), "stock"));

            refreshProductTable();
            clearForm();
            showMessage("Producto registrado correctamente.");
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void updateProduct() {
        try {
            productService.updateProduct(
                    parseInteger(txtIdProductField.getText(), "código"),
                    txtProductField.getText().trim(),
                    txtSizePField.getText().trim(),
                    txtColorPField.getText().trim(),
                    parseDouble(txtUnitPriceField.getText(), "precio"),
                    parseInteger(txtStockField.getText(), "stock"));

            refreshProductTable();
            clearForm();
            showMessage("Producto actualizado correctamente.");
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void deleteProduct() {
    try {
        int selectedRow = TableProduct.getSelectedRow();
        if (selectedRow == -1) {
            throw new IllegalArgumentException("Selecciona un producto para eliminar.");
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de que deseas eliminar este producto?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        int idProduct = Integer.parseInt(TableProduct.getValueAt(selectedRow, 0).toString());
        productService.deleteProduct(idProduct);
        refreshProductTable();
        clearForm();
        showMessage("Producto eliminado correctamente.");
    } catch (Exception ex) {
        showError(ex.getMessage());
    }
}

    private void refreshProductTable() {
        DefaultTableModel model = (DefaultTableModel) TableProduct.getModel();
        model.setRowCount(0);

        List<Product> products = productService.getAllProducts();
        for (Product product : products) {
            model.addRow(new Object[] {
                    product.getId(),
                    product.getName(),
                    product.getSize(),
                    product.getColor(),
                    product.getPrice(),
                    product.getStock()
            });
        }
    }

    public void reloadData() {
        refreshProductTable();
    }

    private void loadSelectedProduct() {
        int selectedRow = TableProduct.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        txtIdProductField.setText(TableProduct.getValueAt(selectedRow, 0).toString());
        txtProductField.setText(TableProduct.getValueAt(selectedRow, 1).toString());
        txtSizePField.setText(TableProduct.getValueAt(selectedRow, 2).toString());
        txtColorPField.setText(TableProduct.getValueAt(selectedRow, 3).toString());
        txtUnitPriceField.setText(TableProduct.getValueAt(selectedRow, 4).toString());
        txtStockField.setText(TableProduct.getValueAt(selectedRow, 5).toString());
    }

    private void clearForm() {
        txtIdProductField.setText("");
        txtProductField.setText("");
        txtSizePField.setText("");
        txtColorPField.setText("");
        txtUnitPriceField.setText("");
        txtStockField.setText("");
        TableProduct.clearSelection();
        txtIdProductField.requestFocus();
    }

    private int parseInteger(String value, String fieldName) {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("El campo " + fieldName + " debe ser numérico.");
        }
    }

    private double parseDouble(String value, String fieldName) {
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("El campo " + fieldName + " debe ser numérico.");
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ToolBarProduct = new javax.swing.JToolBar();
        btnCreateP = new javax.swing.JButton();
        btnEditP = new javax.swing.JButton();
        btnDeleteP = new javax.swing.JButton();
        btnClearP = new javax.swing.JButton();
        txtIdProductField = new javax.swing.JTextField();
        lblNameProduct = new javax.swing.JLabel();
        lblSizeProduct = new javax.swing.JLabel();
        lblColorProduct = new javax.swing.JLabel();
        lblIdProduct = new javax.swing.JLabel();
        txtProductField = new javax.swing.JTextField();
        txtColorPField = new javax.swing.JTextField();
        lblUnitPrice = new javax.swing.JLabel();
        lblStock = new javax.swing.JLabel();
        txtSizePField = new javax.swing.JTextField();
        txtUnitPriceField = new javax.swing.JTextField();
        txtStockField = new javax.swing.JTextField();
        btnSaveProduct = new javax.swing.JButton();
        ScrollPaneProduct = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableProduct = new javax.swing.JTable();

        ToolBarProduct.setRollover(true);

        btnCreateP.setText("Crear Producto");
        btnCreateP.setMaximumSize(new java.awt.Dimension(92, 24));
        btnCreateP.setMinimumSize(new java.awt.Dimension(92, 24));
        btnCreateP.setPreferredSize(new java.awt.Dimension(76, 24));
        btnCreateP.addActionListener(this::btnCreatePActionPerformed);
        ToolBarProduct.add(btnCreateP);

        btnEditP.setText("Editar Producto");
        ToolBarProduct.add(btnEditP);

        btnDeleteP.setText("Eliminar Producto");
        ToolBarProduct.add(btnDeleteP);

        btnClearP.setText("Limpiar Formulario");
        btnClearP.addActionListener(this::btnClearPActionPerformed);
        ToolBarProduct.add(btnClearP);

        lblNameProduct.setText("Producto:");

        lblSizeProduct.setText("Talla:");

        lblColorProduct.setText("Color:");

        lblIdProduct.setText("Código:");

        txtProductField.addActionListener(this::txtProductFieldActionPerformed);

        txtColorPField.addActionListener(this::txtColorPFieldActionPerformed);

        lblUnitPrice.setText("Precio:");

        lblStock.setText("Stock:");

        txtSizePField.addActionListener(this::txtSizePFieldActionPerformed);

        txtUnitPriceField.addActionListener(this::txtUnitPriceFieldActionPerformed);

        txtStockField.addActionListener(this::txtStockFieldActionPerformed);

        btnSaveProduct.setText("Guardar");
        btnSaveProduct.setName(""); // NOI18N
        btnSaveProduct.addActionListener(this::btnSaveProductActionPerformed);

        TableProduct.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null, null, null },
                        { null, null, null, null, null, null },
                        { null, null, null, null, null, null },
                        { null, null, null, null, null, null }
                },
                new String[] {
                        "Codigo", "Producto", "Talla", "Color", "Precio", "Stock"
                }));
        jScrollPane1.setViewportView(TableProduct);

        ScrollPaneProduct.setViewportView(jScrollPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(ToolBarProduct, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        524, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(34, 34, 34)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(2, 2, 2)
                                                                .addComponent(lblIdProduct,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 42,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(lblNameProduct)
                                                        .addComponent(lblSizeProduct)
                                                        .addComponent(lblColorProduct)
                                                        .addComponent(lblUnitPrice)
                                                        .addComponent(lblStock))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtIdProductField,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 223,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtStockField,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 223,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtColorPField,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 223,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtProductField,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 223,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtSizePField,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 223,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtUnitPriceField,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 223,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        Short.MAX_VALUE))))
                        .addComponent(ScrollPaneProduct)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(228, 228, 228)
                                .addComponent(btnSaveProduct, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(ToolBarProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 31,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtIdProductField, javax.swing.GroupLayout.PREFERRED_SIZE, 22,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblIdProduct))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblNameProduct)
                                        .addComponent(txtProductField, javax.swing.GroupLayout.PREFERRED_SIZE, 22,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblSizeProduct)
                                        .addComponent(txtSizePField, javax.swing.GroupLayout.PREFERRED_SIZE, 22,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblColorProduct)
                                        .addComponent(txtColorPField, javax.swing.GroupLayout.PREFERRED_SIZE, 22,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblUnitPrice)
                                        .addComponent(txtUnitPriceField, javax.swing.GroupLayout.PREFERRED_SIZE, 22,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblStock)
                                        .addComponent(txtStockField, javax.swing.GroupLayout.PREFERRED_SIZE, 22,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37,
                                        Short.MAX_VALUE)
                                .addComponent(btnSaveProduct, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21)
                                .addComponent(ScrollPaneProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 224,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)));

        txtIdProductField.getAccessibleContext().setAccessibleName("Campo: Id del Producto");
        lblNameProduct.getAccessibleContext().setAccessibleName("Nombre del Producto");
        lblSizeProduct.getAccessibleContext().setAccessibleName("Talla del producto");
        lblColorProduct.getAccessibleContext().setAccessibleName("Color del producto");
        lblIdProduct.getAccessibleContext().setAccessibleName("Código del producto");
        txtProductField.getAccessibleContext().setAccessibleName("Campo: Nombre dle producto");
        txtColorPField.getAccessibleContext().setAccessibleName("Campo: color del producto");
        lblUnitPrice.getAccessibleContext().setAccessibleName("Precio del Producto");
        lblStock.getAccessibleContext().setAccessibleName("Stock del producto");
        txtSizePField.getAccessibleContext().setAccessibleName("Campo: Talla del producto");
        txtUnitPriceField.getAccessibleContext().setAccessibleName("Campo: precio del producto");
        txtStockField.getAccessibleContext().setAccessibleName("Campo: stock del producto");
        btnSaveProduct.getAccessibleContext().setAccessibleName("Guardar Producto");
    }// </editor-fold>//GEN-END:initComponents

    private void btnCreatePActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCreatePActionPerformed
    }// GEN-LAST:event_btnCreatePActionPerformed

    private void btnClearPActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnClearPActionPerformed
    }// GEN-LAST:event_btnClearPActionPerformed

    private void txtProductFieldActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtProductFieldActionPerformed
    }// GEN-LAST:event_txtProductFieldActionPerformed

    private void txtColorPFieldActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtColorPFieldActionPerformed
    }// GEN-LAST:event_txtColorPFieldActionPerformed

    private void txtSizePFieldActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtSizePFieldActionPerformed
    }// GEN-LAST:event_txtSizePFieldActionPerformed

    private void txtUnitPriceFieldActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtUnitPriceFieldActionPerformed

    }// GEN-LAST:event_txtUnitPriceFieldActionPerformed

    private void txtStockFieldActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtStockFieldActionPerformed
    }// GEN-LAST:event_txtStockFieldActionPerformed

    private void btnSaveProductActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSaveProductActionPerformed
    }// GEN-LAST:event_btnSaveProductActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ScrollPaneProduct;
    private javax.swing.JTable TableProduct;
    private javax.swing.JToolBar ToolBarProduct;
    private javax.swing.JButton btnClearP;
    private javax.swing.JButton btnCreateP;
    private javax.swing.JButton btnDeleteP;
    private javax.swing.JButton btnEditP;
    private javax.swing.JButton btnSaveProduct;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblColorProduct;
    private javax.swing.JLabel lblIdProduct;
    private javax.swing.JLabel lblNameProduct;
    private javax.swing.JLabel lblSizeProduct;
    private javax.swing.JLabel lblStock;
    private javax.swing.JLabel lblUnitPrice;
    private javax.swing.JTextField txtColorPField;
    private javax.swing.JTextField txtIdProductField;
    private javax.swing.JTextField txtProductField;
    private javax.swing.JTextField txtSizePField;
    private javax.swing.JTextField txtStockField;
    private javax.swing.JTextField txtUnitPriceField;
    // End of variables declaration//GEN-END:variables
}
