/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.grupo4.manageclient.view;

import com.grupo4.manageclient.model.Product;
import com.grupo4.manageclient.service.IProductService;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author anamo
 */
public class ProductPanel extends javax.swing.JPanel {

    private final IProductService productService;

    public ProductPanel(IProductService productService) {
        this.productService = productService;
        initComponents();
        configureTable();
        bindEvents();
        refreshProductTable();
    }

    private void configureTable() {
        tblProducts.setModel(new DefaultTableModel(
                new Object[] { "Código", "Producto", "Talla", "Color", "Precio", "Stock" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        tblProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void bindEvents() {
        btnCreateProduct.addActionListener(e -> clearForm());
        btnClearProductForm.addActionListener(e -> clearForm());
        btnSaveProduct.addActionListener(e -> saveProduct());
        btnUpdateProduct.addActionListener(e -> updateProduct());
        btnDeleteProduct.addActionListener(e -> deleteProduct());

        tblProducts.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                loadSelectedProduct();
            }
        });
    }

    private void saveProduct() {
        try {
            productService.registerProduct(
                    parseInteger(txtProductId.getText(), "código"),
                    txtProductName.getText().trim(),
                    txtProductSize.getText().trim(),
                    txtProductColor.getText().trim(),
                    parseDouble(txtUnitPrice.getText(), "precio"),
                    parseInteger(txtProductStock.getText(), "stock"));

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
                    parseInteger(txtProductId.getText(), "código"),
                    txtProductName.getText().trim(),
                    txtProductSize.getText().trim(),
                    txtProductColor.getText().trim(),
                    parseDouble(txtUnitPrice.getText(), "precio"),
                    parseInteger(txtProductStock.getText(), "stock"));

            refreshProductTable();
            clearForm();
            showMessage("Producto actualizado correctamente.");
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void deleteProduct() {
    try {
        int selectedRow = tblProducts.getSelectedRow();
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

        int idProduct = Integer.parseInt(tblProducts.getValueAt(selectedRow, 0).toString());
        productService.deleteProduct(idProduct);
        refreshProductTable();
        clearForm();
        showMessage("Producto eliminado correctamente.");
    } catch (Exception ex) {
        showError(ex.getMessage());
    }
}

    private void refreshProductTable() {
        DefaultTableModel model = (DefaultTableModel) tblProducts.getModel();
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
        int selectedRow = tblProducts.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        txtProductId.setText(tblProducts.getValueAt(selectedRow, 0).toString());
        txtProductName.setText(tblProducts.getValueAt(selectedRow, 1).toString());
        txtProductSize.setText(tblProducts.getValueAt(selectedRow, 2).toString());
        txtProductColor.setText(tblProducts.getValueAt(selectedRow, 3).toString());
        txtUnitPrice.setText(tblProducts.getValueAt(selectedRow, 4).toString());
        txtProductStock.setText(tblProducts.getValueAt(selectedRow, 5).toString());
    }

    private void clearForm() {
        txtProductId.setText("");
        txtProductName.setText("");
        txtProductSize.setText("");
        txtProductColor.setText("");
        txtUnitPrice.setText("");
        txtProductStock.setText("");
        tblProducts.clearSelection();
        txtProductId.requestFocus();
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tlbProduct = new javax.swing.JToolBar();
        btnCreateProduct = new javax.swing.JButton();
        btnUpdateProduct = new javax.swing.JButton();
        btnDeleteProduct = new javax.swing.JButton();
        btnClearProductForm = new javax.swing.JButton();
        txtProductId = new javax.swing.JTextField();
        lblProductName = new javax.swing.JLabel();
        lblProductSize = new javax.swing.JLabel();
        lblProductColor = new javax.swing.JLabel();
        lblProductId = new javax.swing.JLabel();
        txtProductName = new javax.swing.JTextField();
        txtProductColor = new javax.swing.JTextField();
        lblUnitPrice = new javax.swing.JLabel();
        lblProductStock = new javax.swing.JLabel();
        txtProductSize = new javax.swing.JTextField();
        txtUnitPrice = new javax.swing.JTextField();
        txtProductStock = new javax.swing.JTextField();
        btnSaveProduct = new java.awt.Button();
        scrProducts = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProducts = new javax.swing.JTable();

        tlbProduct.setRollover(true);

        btnCreateProduct.setText("Crear Producto");
        btnCreateProduct.setMaximumSize(new java.awt.Dimension(92, 24));
        btnCreateProduct.setMinimumSize(new java.awt.Dimension(92, 24));
        btnCreateProduct.setPreferredSize(new java.awt.Dimension(76, 24));
        btnCreateProduct.addActionListener(this::btnCreateProductActionPerformed);
        tlbProduct.add(btnCreateProduct);

        btnUpdateProduct.setLabel("Editar Producto");
        tlbProduct.add(btnUpdateProduct);

        btnDeleteProduct.setText("Eliminar Producto");
        tlbProduct.add(btnDeleteProduct);

        btnClearProductForm.setText("Limpiar Formulario");
        btnClearProductForm.addActionListener(this::btnClearProductFormActionPerformed);
        tlbProduct.add(btnClearProductForm);

        lblProductName.setText("Producto:");

        lblProductSize.setText("Talla:");

        lblProductColor.setText("Color:");

        lblProductId.setText("Código:");

        txtProductName.addActionListener(this::txtProductNameActionPerformed);

        txtProductColor.addActionListener(this::txtProductColorActionPerformed);

        lblUnitPrice.setText("Precio:");

        lblProductStock.setText("Stock:");

        txtProductSize.addActionListener(this::txtProductSizeActionPerformed);

        txtUnitPrice.addActionListener(this::txtUnitPriceActionPerformed);

        txtProductStock.addActionListener(this::txtProductStockActionPerformed);

        btnSaveProduct.setLabel("Guardar");
        btnSaveProduct.setName(""); // NOI18N
        btnSaveProduct.addActionListener(this::btnSaveProductActionPerformed);

        tblProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Producto", "Talla", "Color", "Precio", "Stock"
            }
        ));
        jScrollPane1.setViewportView(tblProducts);

        scrProducts.setViewportView(jScrollPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tlbProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 524, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(lblProductId, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblProductName)
                            .addComponent(lblProductSize)
                            .addComponent(lblProductColor)
                            .addComponent(lblUnitPrice)
                            .addComponent(lblProductStock))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtProductId, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtProductStock, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtProductColor, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtProductSize, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(scrProducts)
            .addGroup(layout.createSequentialGroup()
                .addGap(228, 228, 228)
                .addComponent(btnSaveProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tlbProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProductId, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProductId))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProductName)
                    .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProductSize)
                    .addComponent(txtProductSize, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProductColor)
                    .addComponent(txtProductColor, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUnitPrice)
                    .addComponent(txtUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProductStock)
                    .addComponent(txtProductStock, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(btnSaveProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(scrProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        txtProductId.getAccessibleContext().setAccessibleName("Campo: Id del Producto");
        lblProductName.getAccessibleContext().setAccessibleName("Nombre del Producto");
        lblProductSize.getAccessibleContext().setAccessibleName("Talla del producto");
        lblProductColor.getAccessibleContext().setAccessibleName("Color del producto");
        lblProductId.getAccessibleContext().setAccessibleName("Código del producto");
        txtProductName.getAccessibleContext().setAccessibleName("Campo: Nombre dle producto");
        txtProductColor.getAccessibleContext().setAccessibleName("Campo: color del producto");
        lblUnitPrice.getAccessibleContext().setAccessibleName("Precio del Producto");
        lblProductStock.getAccessibleContext().setAccessibleName("Stock del producto");
        txtProductSize.getAccessibleContext().setAccessibleName("Campo: Talla del producto");
        txtUnitPrice.getAccessibleContext().setAccessibleName("Campo: precio del producto");
        txtProductStock.getAccessibleContext().setAccessibleName("Campo: stock del producto");
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
    private javax.swing.JButton btnClearProductForm;
    private javax.swing.JButton btnCreateProduct;
    private javax.swing.JButton btnDeleteProduct;
    private java.awt.Button btnSaveProduct;
    private javax.swing.JButton btnUpdateProduct;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblProductColor;
    private javax.swing.JLabel lblProductId;
    private javax.swing.JLabel lblProductName;
    private javax.swing.JLabel lblProductSize;
    private javax.swing.JLabel lblProductStock;
    private javax.swing.JLabel lblUnitPrice;
    private javax.swing.JScrollPane scrProducts;
    private javax.swing.JTable tblProducts;
    private javax.swing.JToolBar tlbProduct;
    private javax.swing.JTextField txtProductColor;
    private javax.swing.JTextField txtProductId;
    private javax.swing.JTextField txtProductName;
    private javax.swing.JTextField txtProductSize;
    private javax.swing.JTextField txtProductStock;
    private javax.swing.JTextField txtUnitPrice;
    // End of variables declaration//GEN-END:variables
}
