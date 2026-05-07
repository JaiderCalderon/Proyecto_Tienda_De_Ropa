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
                new Object[]{"Id de venta", "Cliente", "Total"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    private void bindEvents() {
        btnConfirm.addActionListener(e -> confirmSale());
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

    private void updateDetailPreview(SaleDetail detail) {
        lblSaleName.setText("ID: " + detail.getProduct().getId());
        lblSaleQuantity.setText("Nombre: " + detail.getProduct().getName());
        lblSaleUnitaryPrice.setText("Precio unitario: " + detail.getUnitPrice());
        lblSaleSubtotal.setText("Subtotal: " + detail.getSubtotal());
    }

    private void clearDetailPreview() {
        lblSaleName.setText("ID:");
        lblSaleQuantity.setText("Nombre:");
        lblSaleUnitaryPrice.setText("Precio unitario:");
        lblSaleSubtotal.setText("Subtotal:");
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

            updateDetailPreview(detail);
            reloadData();
            txtQuantity.setText("");
            showMessage("Venta registrada correctamente.");
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
            model.addRow(new Object[]{
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
        clearDetailPreview();
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
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmbClients = new javax.swing.JComboBox<>();
        lblCliente = new javax.swing.JLabel();
        cmbProducts = new javax.swing.JComboBox<>();
        lblProducto = new javax.swing.JLabel();
        lblQuantity = new javax.swing.JLabel();
        txtQuantity = new javax.swing.JTextField();
        lblSaleId = new javax.swing.JLabel();
        lblSaleName = new javax.swing.JLabel();
        lblSaleQuantity = new javax.swing.JLabel();
        lblSaleUnitaryPrice = new javax.swing.JLabel();
        lblSaleSubtotal = new javax.swing.JLabel();
        btnConfirm = new javax.swing.JButton();
        lblDetails = new javax.swing.JLabel();
        lblHistory = new javax.swing.JLabel();
        ScrollPaneSale = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHistorial = new javax.swing.JTable();

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

        lblSaleName.setText("ID:");

        lblSaleQuantity.setText("Nombre:");

        lblSaleUnitaryPrice.setText("Precio unitario:");

        lblSaleSubtotal.setText("Subtotal:");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(lblSaleName, javax.swing.GroupLayout.PREFERRED_SIZE, 107,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblSaleQuantity, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblSaleUnitaryPrice,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 152,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblSaleSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(lblSaleId, javax.swing.GroupLayout.PREFERRED_SIZE, 54,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(lblDetails,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 124,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING,
                                                                        false)
                                                                        .addGroup(
                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                layout.createSequentialGroup()
                                                                                        .addComponent(lblQuantity,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                57,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addPreferredGap(
                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                        .addComponent(txtQuantity,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                42,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addComponent(cmbProducts, 0,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                        .addComponent(cmbClients,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                212,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING,
                                                                        false)
                                                                        .addComponent(lblCliente,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                        .addComponent(lblProducto,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                128,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                .addContainerGap(45, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                .createSequentialGroup()
                                                .addComponent(lblHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 157,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(236, 236, 236))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                layout.createSequentialGroup()
                                                        .addComponent(btnConfirm)
                                                        .addGap(256, 256, 256))))
                        .addComponent(ScrollPaneSale));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cmbClients, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblCliente))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cmbProducts, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblProducto))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblQuantity)
                                        .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(22, 22, 22)
                                .addComponent(lblDetails)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblSaleName)
                                        .addComponent(lblSaleQuantity)
                                        .addComponent(lblSaleUnitaryPrice)
                                        .addComponent(lblSaleSubtotal))
                                .addGap(31, 31, 31)
                                .addComponent(btnConfirm)
                                .addGap(18, 18, 18)
                                .addComponent(lblHistory)
                                .addGap(26, 26, 26)
                                .addComponent(ScrollPaneSale, javax.swing.GroupLayout.PREFERRED_SIZE, 346,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblSaleId)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
    }// </editor-fold>//GEN-END:initComponents

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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblDetails;
    private javax.swing.JLabel lblHistory;
    private javax.swing.JLabel lblProducto;
    private javax.swing.JLabel lblQuantity;
    private javax.swing.JLabel lblSaleId;
    private javax.swing.JLabel lblSaleName;
    private javax.swing.JLabel lblSaleQuantity;
    private javax.swing.JLabel lblSaleSubtotal;
    private javax.swing.JLabel lblSaleUnitaryPrice;
    private javax.swing.JTable tblHistorial;
    private javax.swing.JTextField txtQuantity;
    // End of variables declaration//GEN-END:variables
}
