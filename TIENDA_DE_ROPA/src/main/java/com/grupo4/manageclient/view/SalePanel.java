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

    public SalePanel(ISaleService saleService, IClientService clientService, IProductService productService) {
        this.saleService = saleService;
        this.clientService = clientService;
        this.productService = productService;
        initComponents();
        configureTables();
        bindEvents();
        reloadData();
    }

    private void configureTables() {
        tblHistorial.setModel(new DefaultTableModel(

                new Object[] { "Id de venta", "Cliente", "Total" }, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    private void bindEvents() {

        // El evento de btnConfirm ya está conectado desde NetBeans en initComponents().

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

    private void confirmSale() {
        try {
            ClientItem selectedClientItem = (ClientItem) cmbClients.getSelectedItem();
            ProductItem selectedProductItem = (ProductItem) cmbProducts.getSelectedItem();

            if (selectedClientItem == null) {
                throw new IllegalArgumentException("Debes seleccionar un cliente.");
            }

            if (selectedProductItem == null) {
                throw new IllegalArgumentException("Debes seleccionar un producto.");
            }

            int idSale = generateNextSaleId();
            int quantity = parseInteger(txtQuantity.getText(), "cantidad");

            SaleDetail detail = saleService.createSaleDetail(selectedProductItem.product.getId(), quantity);
            List<SaleDetail> details = new ArrayList<>();
            details.add(detail);

            saleService.registerSale(idSale, selectedClientItem.client.getId(), details);

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
        DefaultTableModel model = (DefaultTableModel) tblHistorial.getModel();
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

    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmbClients = new javax.swing.JComboBox<>();
        lblCliente = new javax.swing.JLabel();
        cmbProducts = new javax.swing.JComboBox<>();
        lblProducto = new javax.swing.JLabel();
        lblQuantity = new javax.swing.JLabel();
        txtQuantity = new javax.swing.JTextField();
        lblSaleId = new javax.swing.JLabel();

        btnConfirm = new javax.swing.JButton();
        lblDetails = new javax.swing.JLabel();
        lblHistory = new javax.swing.JLabel();
        ScrollPaneSale = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHistorial = new javax.swing.JTable();

        jButtonAddCart = new javax.swing.JButton();
        jScrollPaneCart = new javax.swing.JScrollPane();
        jTableCart = new javax.swing.JTable();
        jLabelTotal = new javax.swing.JLabel();
        jButtonDeleteCart = new javax.swing.JButton();

        cmbClients.setModel(new javax.swing.DefaultComboBoxModel<ClientItem>());
        cmbClients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbClientsActionPerformed(evt);
            }
        });

        lblCliente.setText("Cliente");

        cmbProducts.setModel(new javax.swing.DefaultComboBoxModel<ProductItem>());
        cmbProducts.setToolTipText("");

        lblProducto.setText("Producto");

        lblQuantity.setText("Cantidad");

        btnConfirm.setText("Confirmar venta");
        btnConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmActionPerformed(evt);
            }
        });

        lblDetails.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDetails.setText("Detalles de la venta");
        lblDetails.setBorder(new javax.swing.border.MatteBorder(null));

        lblHistory.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHistory.setText("Historial de ventas");
        lblHistory.setBorder(new javax.swing.border.MatteBorder(null));

        tblHistorial.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null },
                        { null, null, null }
                },
                new String[] {
                        "Id de venta", "Cliente", "Total"
                }));
        jScrollPane2.setViewportView(tblHistorial);

        ScrollPaneSale.setViewportView(jScrollPane2);

        jButtonAddCart.setText("Agregar al carrito");
        jButtonAddCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddCartActionPerformed(evt);
            }
        });

        jTableCart.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null }
                },
                new String[] {
                        "Producto", "Cantidad", "Precio Unitario", "Subtotal"
                }));
        jScrollPaneCart.setViewportView(jTableCart);

        jLabelTotal.setText("Total: $0");

        jButtonDeleteCart.setText("Eliminar Del Carrito");
        jButtonDeleteCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteCartActionPerformed(evt);
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
                                                .addComponent(lblCliente, javax.swing.GroupLayout.DEFAULT_SIZE,
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
                                                .addComponent(jButtonAddCart)
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
                                                                .addComponent(lblProducto,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 128,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(0, 0, Short.MAX_VALUE))))
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(305, 305, 305)
                                                .addComponent(lblDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 124,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(302, 302, 302)
                                                .addComponent(btnConfirm)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabelTotal)
                                .addGap(291, 291, 291))
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                        layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jScrollPaneCart))
                                                .addComponent(ScrollPaneSale, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        730, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(284, 284, 284)
                                                .addComponent(lblHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 157,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jButtonDeleteCart)))
                                .addGap(0, 0, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cmbClients, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblCliente))
                                .addGap(23, 23, 23)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cmbProducts, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblProducto))
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblQuantity)
                                        .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButtonAddCart))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblDetails)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPaneCart, javax.swing.GroupLayout.PREFERRED_SIZE, 76,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonDeleteCart)
                                .addGap(1, 1, 1)
                                .addComponent(jLabelTotal)
                                .addGap(37, 37, 37)
                                .addComponent(btnConfirm)
                                .addGap(18, 18, 18)
                                .addComponent(lblHistory)
                                .addGap(18, 18, 18)
                                .addComponent(ScrollPaneSale, javax.swing.GroupLayout.PREFERRED_SIZE, 276,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblSaleId)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAddCartActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonAddCartActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jButtonAddCartActionPerformed

    private void jButtonDeleteCartActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonDeleteCartActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_jButtonDeleteCartActionPerformed

    private void cmbClientsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cmbClientsActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_cmbClientsActionPerformed

    private void btnConfirmActionPerformed(java.awt.event.ActionEvent evt) {
        confirmSale();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ScrollPaneSale;
    private javax.swing.JButton btnConfirm;
    private javax.swing.JComboBox<ClientItem> cmbClients;
    private javax.swing.JComboBox<ProductItem> cmbProducts;
    private javax.swing.JButton jButtonAddCart;
    private javax.swing.JButton jButtonDeleteCart;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPaneCart;
    private javax.swing.JTable jTableCart;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblDetails;
    private javax.swing.JLabel lblHistory;
    private javax.swing.JLabel lblProducto;
    private javax.swing.JLabel lblQuantity;
    private javax.swing.JLabel lblSaleId;
    private javax.swing.JTable tblHistorial;
    private javax.swing.JTextField txtQuantity;
    // End of variables declaration//GEN-END:variables
}
