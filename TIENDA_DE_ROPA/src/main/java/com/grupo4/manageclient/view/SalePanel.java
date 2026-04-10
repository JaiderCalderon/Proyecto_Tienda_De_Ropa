/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.grupo4.manageclient.view;

import com.grupo4.manageclient.model.Client;
import com.grupo4.manageclient.model.Product;
import com.grupo4.manageclient.model.Sale;
import com.grupo4.manageclient.model.SaleDetail;
import com.grupo4.manageclient.service.ClientService;
import com.grupo4.manageclient.service.ProductService;
import com.grupo4.manageclient.service.SaleService;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author samue
 */
public class SalePanel extends javax.swing.JPanel {

    private final SaleService saleService;
    private final ClientService clientService;
    private final ProductService productService;

    private final JTextField txtSaleId;
    private final JComboBox<ClientItem> cmbClients;
    private final JComboBox<ProductItem> cmbProducts;
    private final JTextField txtQuantity;
    private final JButton btnAddDetail;
    private final JButton btnRemoveDetail;
    private final JButton btnConfirmSale;
    private final JButton btnClearSale;
    private final JTable tblSaleDetails;
    private final JTable tblSalesHistory;
    private final DefaultTableModel saleDetailTableModel;
    private final DefaultTableModel saleHistoryTableModel;
    private final JLabel lblTotalValue;

    private final List<SaleDetail> currentDetails;

    public SalePanel(SaleService saleService, ClientService clientService, ProductService productService, javax.swing.JComboBox<com.grupo4.manageclient.view.SalePanel.ClientItem> cmbClients) {
        this.saleService = saleService;
        this.clientService = clientService;
        this.productService = productService;
        this.txtSaleId = new JTextField(15);
        this.cmbClients = new JComboBox<>();
        this.cmbProducts = new JComboBox<>();
        this.txtQuantity = new JTextField(10);
        this.btnAddDetail = new JButton("Agregar producto");
        this.btnRemoveDetail = new JButton("Quitar producto");
        this.btnConfirmSale = new JButton("Confirmar venta");
        this.btnClearSale = new JButton("Limpiar venta");
        this.saleDetailTableModel = new DefaultTableModel(
                new Object[] { "Código", "Producto", "Cantidad", "Precio unitario", "Subtotal" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.saleHistoryTableModel = new DefaultTableModel(
                new Object[] { "Id de venta", "Cliente", "Total" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.tblSaleDetails = new JTable(saleDetailTableModel);
        this.tblSalesHistory = new JTable(saleHistoryTableModel);
        this.lblTotalValue = new JLabel("Total: 0.0");
        this.currentDetails = new ArrayList<>();

        buildUi();
        bindEvents();
        reloadData();
        this.cmbClients = cmbClients;
    }

    private void buildUi() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addFormRow(formPanel, gbc, 0, "Id venta:", txtSaleId);
        addFormRow(formPanel, gbc, 1, "Cliente:", cmbClients);
        addFormRow(formPanel, gbc, 2, "Producto:", cmbProducts);
        addFormRow(formPanel, gbc, 3, "Cantidad:", txtQuantity);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(btnAddDetail);
        buttonPanel.add(btnRemoveDetail);
        buttonPanel.add(btnConfirmSale);
        buttonPanel.add(btnClearSale);
        buttonPanel.add(lblTotalValue);

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        JScrollPane detailScrollPane = new JScrollPane(tblSaleDetails);
        JScrollPane historyScrollPane = new JScrollPane(tblSalesHistory);
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, detailScrollPane, historyScrollPane);
        splitPane.setResizeWeight(0.45);

        add(topPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, String label, java.awt.Component component) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(component, gbc);
    }

    private void bindEvents() {
        btnAddDetail.addActionListener(e -> addDetail());
        btnRemoveDetail.addActionListener(e -> removeDetail());
        btnConfirmSale.addActionListener(e -> confirmSale());
        btnClearSale.addActionListener(e -> clearSale());
    }

    private void addDetail() {
        try {
            ProductItem selectedProductItem = (ProductItem) cmbProducts.getSelectedItem();
            if (selectedProductItem == null) {
                throw new IllegalArgumentException("Debes seleccionar un producto.");
            }

            int quantity = parseInteger(txtQuantity.getText(), "cantidad");
            SaleDetail detail = saleService.createSaleDetail(selectedProductItem.product.getId(), quantity);
            currentDetails.add(detail);
            refreshDetailTable();
            txtQuantity.setText("");
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void removeDetail() {
        int selectedRow = tblSaleDetails.getSelectedRow();
        if (selectedRow == -1) {
            showError("Selecciona un producto del detalle para quitarlo.");
            return;
        }

        currentDetails.remove(selectedRow);
        refreshDetailTable();
    }

    private void confirmSale() {
        try {
            ClientItem selectedClientItem = (ClientItem) cmbClients.getSelectedItem();
            if (selectedClientItem == null) {
                throw new IllegalArgumentException("Debes seleccionar un cliente.");
            }

            int idSale = parseInteger(txtSaleId.getText(), "id de venta");
            List<SaleDetail> groupedDetails = mergeDetails(currentDetails);
            saleService.registerSale(idSale, selectedClientItem.client.getId(), groupedDetails);

            reloadData();
            clearSale();
            showMessage("Venta registrada correctamente.");
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private List<SaleDetail> mergeDetails(List<SaleDetail> details) {
        if (details.isEmpty()) {
            throw new IllegalArgumentException("La venta debe tener al menos un producto agregado.");
        }

        Map<Integer, SaleDetail> grouped = new LinkedHashMap<>();
        for (SaleDetail detail : details) {
            int productId = detail.getProduct().getId();
            if (grouped.containsKey(productId)) {
                SaleDetail existing = grouped.get(productId);
                existing.setQuantity(existing.getQuantity() + detail.getQuantity());
            } else {
                grouped.put(productId, new SaleDetail(detail.getProduct(), detail.getQuantity()));
            }
        }
        return new ArrayList<>(grouped.values());
    }

    private void refreshDetailTable() {
        saleDetailTableModel.setRowCount(0);
        double total = 0;
        for (SaleDetail detail : currentDetails) {
            saleDetailTableModel.addRow(new Object[] {
                    detail.getProduct().getId(),
                    detail.getProduct().getName(),
                    detail.getQuantity(),
                    detail.getUnitPrice(),
                    detail.getSubtotal()
            });
            total += detail.getSubtotal();
        }
        lblTotalValue.setText("Total: " + total);
    }

    private void refreshSalesHistory() {
        saleHistoryTableModel.setRowCount(0);
        for (Sale sale : saleService.getAllSales()) {
            saleHistoryTableModel.addRow(new Object[] {
                    sale.getIdSale(),
                    sale.getClient().getName(),
                    sale.getTotal()
            });
        }
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

    public void reloadData() {
        loadClients();
        loadProducts();
        refreshSalesHistory();
        refreshDetailTable();
    }

    private void clearSale() {
        txtSaleId.setText("");
        txtQuantity.setText("");
        currentDetails.clear();
        refreshDetailTable();
        tblSaleDetails.clearSelection();
        txtSaleId.requestFocus();
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
    @SuppressWarnings("unchecked")
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

        cmbClients.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbClients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbClientsActionPerformed(evt);
            }
        });

        lblCliente.setText("Cliente");

        cmbProducts.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
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

    private void btnConfirmActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnConfirmActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnConfirmActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ScrollPaneSale;
    private javax.swing.JButton btnConfirm;
    private javax.swing.JComboBox<String> cmbClients;
    private javax.swing.JComboBox<String> cmbProducts;
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
