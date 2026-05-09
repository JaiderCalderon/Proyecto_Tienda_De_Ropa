/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.grupo4.manageclient.view;

import com.grupo4.manageclient.model.Client;
import com.grupo4.manageclient.model.Product;
import com.grupo4.manageclient.model.Sale;
import com.grupo4.manageclient.model.SaleDetail;
import com.grupo4.manageclient.service.IClientService;
import com.grupo4.manageclient.service.IProductService;
import com.grupo4.manageclient.service.ISaleService;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author samue
 */
public class SalePanel extends javax.swing.JPanel {

    private final ISaleService saleService;
    private final IClientService clientService;
    private final IProductService productService;
    private final List<SaleDetail> cart = new ArrayList<>();

    public SalePanel(ISaleService saleService, IClientService clientService, IProductService productService) {
        this.saleService = saleService;
        this.clientService = clientService;
        this.productService = productService;
        initComponents();
        configureTables();
        reloadData();
    }

    private void configureTables() {
        // Tabla historial
        tblSalesHistory.setModel(new DefaultTableModel(
                new Object[] { "Id de venta", "Cliente", "Total" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        // Tabla carrito
        tblCart.setModel(new DefaultTableModel(
                new Object[] { "Producto", "Cantidad", "Precio unitario", "Subtotal" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }


    private void loadClients() {
        cmbClients.removeAllItems();
        for (Client client : clientService.getAllClients()) {
            cmbClients.addItem(new ClientItem(client));
        }
    }

    private void loadProducts() {
        cmbProducts.removeAllItems();
        for (Product product : productService.getAllProducts()) {
            if (product.getStock() > 0) {
                cmbProducts.addItem(new ProductItem(product));
            }
        }
    }

    private void addToCart() {
    try {
        ProductItem selectedProductItem = (ProductItem) cmbProducts.getSelectedItem();

        if (selectedProductItem == null) {
            throw new IllegalArgumentException("Debes seleccionar un producto.");
        }

        int quantity = parseInteger(txtQuantity.getText(), "cantidad");

        if (quantity <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0.");
        }

        SaleDetail detail = saleService.createSaleDetail(
                selectedProductItem.product.getId(),
                quantity);

        boolean found = false;

        for (SaleDetail item : cart) {
            if (item.getProduct().getId() == detail.getProduct().getId()) {

                int newQuantity = item.getQuantity() + detail.getQuantity();

                if (newQuantity > selectedProductItem.product.getStock()) {
                    throw new IllegalArgumentException(
                            "Stock insuficiente. Stock disponible: " 
                            + selectedProductItem.product.getStock()
                    );
                }

                item.setQuantity(newQuantity);
                found = true;
                break;
            }
        }

        if (!found) {
            cart.add(detail);
        }

        refreshCartTable();
        txtQuantity.setText("");

    } catch (Exception ex) {
        showError(ex.getMessage());
    }
}

    private void removeFromCart() {
        int selectedRow = tblCart.getSelectedRow();

        if (selectedRow < 0) {
            showError("Selecciona un producto del carrito para eliminar.");
            return;
        }

        cart.remove(selectedRow);
        refreshCartTable();
    }

    private void refreshCartTable() {
        DefaultTableModel model = (DefaultTableModel) tblCart.getModel();
        model.setRowCount(0);

        double total = 0;

        for (SaleDetail detail : cart) {

            double subtotal = detail.getUnitPrice() * detail.getQuantity();
            total += subtotal;

            model.addRow(new Object[] {
                    detail.getProduct().getName(),
                    detail.getQuantity(),
                    detail.getUnitPrice(),
                    subtotal
            });
        }

        lblTotal.setText("Total: $" + String.format("%.2f", total));
    }

    private void confirmSale() {
        try {
            ClientItem selectedClientItem = (ClientItem) cmbClients.getSelectedItem();

            if (selectedClientItem == null) {
                throw new IllegalArgumentException("Debes seleccionar un cliente.");
            }
            if (cart.isEmpty()) {
                throw new IllegalArgumentException("El carrito está vacío. Agrega al menos un producto.");
            }

            int idSale = generateNextSaleId();
            saleService.registerSale(idSale, selectedClientItem.client.getId(), new ArrayList<>(cart));

            showMessage("Venta registrada exitosamente.");

            // Limpiar estado
            cart.clear();
            refreshCartTable();
            txtQuantity.setText("");
            reloadData();

        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private int generateNextSaleId() {
        List<Sale> sales = saleService.getAllSales();
        int max = 0;
        for (Sale sale : sales) {
            if (sale.getIdSale() > max) {
                max = sale.getIdSale();
            }
        }
        return max + 1;
    }

    private void refreshSalesHistory() {
        DefaultTableModel model = (DefaultTableModel) tblSalesHistory.getModel();
        model.setRowCount(0);

        for (Sale sale : saleService.getAllSales()) {

            model.addRow(new Object[] {
                    sale.getIdSale(),
                    sale.getClient().getName(),
                    sale.getTotal()

            });
        }
    }

    public void reloadData() {
        loadClients();
        loadProducts();
        refreshSalesHistory();

    }

    private int parseInteger(String value, String fieldName) {
        try {
            return Integer.parseInt(value.trim());
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

    private static class ClientItem {

        private final Client client;

        private ClientItem(Client client) {
            this.client = client;
        }

        @Override
        public String toString() {
            return client.getId() + " - " + client.getName();
        }
    }

    private static class ProductItem {

        private final Product product;

        private ProductItem(Product product) {
            this.product = product;
        }

        @Override
        public String toString() {
            return product.getId() + " - " + product.getName() + " (stock: " + product.getStock() + ")";
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated

    // <editor-fold defaultstate="collapsed" desc="Generated

    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmbClients = new javax.swing.JComboBox<ClientItem>();
        lblClients = new javax.swing.JLabel();
        cmbProducts = new javax.swing.JComboBox<ProductItem>();
        lblProducts = new javax.swing.JLabel();
        lblQuantity = new javax.swing.JLabel();
        txtQuantity = new javax.swing.JTextField();
        lblSaleId = new javax.swing.JLabel();
        btnConfirmSale = new javax.swing.JButton();
        lblCartDetails = new javax.swing.JLabel();
        lblSalesHistory = new javax.swing.JLabel();
        scrSales = new javax.swing.JScrollPane();
        scrSalesWrapper = new javax.swing.JScrollPane();
        tblSalesHistory = new javax.swing.JTable();
        btnAddToCart = new javax.swing.JButton();
        scrCart = new javax.swing.JScrollPane();
        tblCart = new javax.swing.JTable();
        lblTotal = new javax.swing.JLabel();
        btnRemoveFromCart = new javax.swing.JButton();

        cmbClients.setModel(
                new javax.swing.DefaultComboBoxModel<ClientItem>());
        cmbClients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbClientsActionPerformed(evt);
            }
        });

        lblClients.setText("Cliente");

        cmbProducts.setModel(
                new javax.swing.DefaultComboBoxModel<ProductItem>());
        cmbProducts.setToolTipText("");

        lblProducts.setText("Producto");

        lblQuantity.setText("Cantidad");

        btnConfirmSale.setText("Confirmar venta");
        btnConfirmSale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmSaleActionPerformed(evt);
            }
        });

        lblCartDetails.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCartDetails.setText("Detalles de la venta");
        lblCartDetails.setBorder(new javax.swing.border.MatteBorder(null));

        lblSalesHistory.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSalesHistory.setText("Historial de ventas");
        lblSalesHistory.setBorder(new javax.swing.border.MatteBorder(null));

        tblSalesHistory.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null },
                        { null, null, null }
                },
                new String[] {
                        "Id de venta", "Cliente", "Total"
                }));
        scrSalesWrapper.setViewportView(tblSalesHistory);

        scrSales.setViewportView(scrSalesWrapper);

        btnAddToCart.setText("Agregar al carrito");
        btnAddToCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddToCartActionPerformed(evt);
            }
        });

        tblCart.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null }
                },
                new String[] {
                        "Producto", "Cantidad", "Precio Unitario", "Subtotal"
                }));
        scrCart.setViewportView(tblCart);

        lblTotal.setText("Total: $0");

        btnRemoveFromCart.setText("Eliminar Del Carrito");
        btnRemoveFromCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveFromCartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(cmbClients, javax.swing.GroupLayout.PREFERRED_SIZE, 212,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(lblClients, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 57,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 42,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnAddToCart)
                                                .addGap(556, 556, 556))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblSaleId, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(cmbProducts,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 212,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(lblProducts,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 128,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(0, 0, Short.MAX_VALUE))))
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(305, 305, 305)
                                                .addComponent(lblCartDetails, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(302, 302, 302)
                                                .addComponent(btnConfirmSale)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblTotal)
                                .addGap(291, 291, 291))
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                        layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(scrCart))
                                                .addComponent(scrSales, javax.swing.GroupLayout.PREFERRED_SIZE, 730,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(284, 284, 284)
                                                .addComponent(lblSalesHistory, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        157, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(btnRemoveFromCart)))
                                .addGap(0, 0, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cmbClients, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblClients))
                                .addGap(23, 23, 23)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cmbProducts, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblProducts))
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblQuantity)
                                        .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnAddToCart))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblCartDetails)
                                .addGap(18, 18, 18)
                                .addComponent(scrCart, javax.swing.GroupLayout.PREFERRED_SIZE, 76,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnRemoveFromCart)
                                .addGap(1, 1, 1)
                                .addComponent(lblTotal)
                                .addGap(37, 37, 37)
                                .addComponent(btnConfirmSale)
                                .addGap(18, 18, 18)
                                .addComponent(lblSalesHistory)
                                .addGap(18, 18, 18)
                                .addComponent(scrSales, javax.swing.GroupLayout.PREFERRED_SIZE, 276,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblSaleId)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddToCartActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnAddToCartActionPerformed
        addToCart();
    }// GEN-LAST:event_btnAddToCartActionPerformed

    private void btnRemoveFromCartActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnRemoveFromCartActionPerformed
        removeFromCart();
    }// GEN-LAST:event_btnRemoveFromCartActionPerformed

    private void cmbClientsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cmbClientsActionPerformed

    }// GEN-LAST:event_cmbClientsActionPerformed

    private void btnConfirmSaleActionPerformed(java.awt.event.ActionEvent evt) {
        confirmSale();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddToCart;
    private javax.swing.JButton btnConfirmSale;
    private javax.swing.JButton btnRemoveFromCart;
    private javax.swing.JComboBox<ClientItem> cmbClients;
    private javax.swing.JComboBox<ProductItem> cmbProducts;
    private javax.swing.JScrollPane scrSalesWrapper;
    private javax.swing.JLabel lblCartDetails;
    private javax.swing.JLabel lblClients;
    private javax.swing.JLabel lblProducts;
    private javax.swing.JLabel lblQuantity;
    private javax.swing.JLabel lblSaleId;
    private javax.swing.JLabel lblSalesHistory;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JScrollPane scrCart;
    private javax.swing.JScrollPane scrSales;
    private javax.swing.JTable tblCart;
    private javax.swing.JTable tblSalesHistory;
    private javax.swing.JTextField txtQuantity;
    // End of variables declaration//GEN-END:variables
}
