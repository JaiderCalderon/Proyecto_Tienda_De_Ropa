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
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
 
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
 
/**
 *
 * @author samue
 */
public class SalePanel extends javax.swing.JPanel {
 
    private final SaleService saleService;
    private final ClientService clientService;
    private final ProductService productService;
 
    // Carrito de compras: almacena los productos antes de confirmar la venta.
    private final List<SaleDetail> cart = new ArrayList<>();
 
    // ===== Componentes UI =====
    private JComboBox<ClientItem> cmbClients;
    private JComboBox<ProductItem> cmbProducts;
    private JTextField txtQuantity;
    private JButton btnAddToCart;
    private JButton btnRemoveFromCart;
    private JButton btnClearCart;
    private JButton btnConfirm;
    private JTable tblCart;
    private JTable tblHistorial;
    private JLabel lblTotal;
 
    public SalePanel(SaleService saleService, ClientService clientService, ProductService productService) {
        this.saleService = saleService;
        this.clientService = clientService;
        this.productService = productService;
        initComponents();
        configureTables();
        bindEvents();
        reloadData();
    }
 
    // =========================================================
    // UI
    // =========================================================
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
 
        // ----- Formulario "Nueva venta" -----
        cmbClients = new JComboBox<>();
        cmbProducts = new JComboBox<>();
        txtQuantity = new JTextField();
        btnAddToCart = new JButton("Agregar al carrito");
 
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 8, 8));
        formPanel.setBorder(BorderFactory.createTitledBorder("Nueva venta"));
        formPanel.add(new JLabel("Cliente:"));
        formPanel.add(cmbClients);
        formPanel.add(new JLabel("Producto:"));
        formPanel.add(cmbProducts);
        formPanel.add(new JLabel("Cantidad:"));
        formPanel.add(txtQuantity);
        formPanel.add(new JLabel(""));
        formPanel.add(btnAddToCart);
 
        // ----- Carrito -----
        tblCart = new JTable();
        btnRemoveFromCart = new JButton("Eliminar seleccionado");
        btnClearCart = new JButton("Vaciar carrito");
        lblTotal = new JLabel("Total: 0.00");
 
        JPanel cartFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cartFooter.add(btnRemoveFromCart);
        cartFooter.add(btnClearCart);
        cartFooter.add(Box.createHorizontalStrut(20));
        cartFooter.add(lblTotal);
 
        JPanel cartPanel = new JPanel(new BorderLayout(5, 5));
        cartPanel.setBorder(BorderFactory.createTitledBorder("Carrito"));
        cartPanel.add(new JScrollPane(tblCart), BorderLayout.CENTER);
        cartPanel.add(cartFooter, BorderLayout.SOUTH);
 
        // ----- Confirmar -----
        btnConfirm = new JButton("Confirmar venta");
        JPanel confirmRow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        confirmRow.add(btnConfirm);
 
        // ----- Historial -----
        tblHistorial = new JTable();
        JPanel historyPanel = new JPanel(new BorderLayout(5, 5));
        historyPanel.setBorder(BorderFactory.createTitledBorder("Historial de ventas"));
        historyPanel.add(new JScrollPane(tblHistorial), BorderLayout.CENTER);
 
        // ----- Composición -----
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(formPanel);
        topPanel.add(cartPanel);
        topPanel.add(confirmRow);
 
        add(topPanel, BorderLayout.NORTH);
        add(historyPanel, BorderLayout.CENTER);
    }
 
    private void configureTables() {
        tblCart.setModel(new DefaultTableModel(
                new Object[]{"ID", "Producto", "Cantidad", "Precio unitario", "Subtotal"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
 
        tblHistorial.setModel(new DefaultTableModel(
                new Object[]{"Id de venta", "Cliente", "Productos", "Total"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }
 
    private void bindEvents() {
        // Un único listener por botón (corrige el bug del listener duplicado).
        btnAddToCart.addActionListener(e -> addToCart());
        btnRemoveFromCart.addActionListener(e -> removeSelectedFromCart());
        btnClearCart.addActionListener(e -> clearCart());
        btnConfirm.addActionListener(e -> confirmSale());
    }
 
    // =========================================================
    // Carga de combos / historial
    // =========================================================
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
 
    private void refreshSalesHistory() {
    DefaultTableModel model = (DefaultTableModel) tblHistorial.getModel();
    model.setRowCount(0);

    for (Sale sale : saleService.getAllSales()) {
        StringBuilder productsSold = new StringBuilder();

        for (SaleDetail detail : sale.getDetails()) {
            productsSold.append(detail.getProduct().getName())
                    .append(" x")
                    .append(detail.getQuantity())
                    .append(", ");
        }

        if (productsSold.length() > 0) {
            productsSold.setLength(productsSold.length() - 2);
        }

        model.addRow(new Object[]{
                sale.getIdSale(),
                sale.getClient().getName(),
                productsSold.toString(),
                sale.getTotal()
        });
    }
}
 
    public void reloadData() {
        loadClients();
        loadProducts();
        refreshSalesHistory();
        refreshCartTable();
    }
 
    // =========================================================
    // Carrito
    // =========================================================
    private void addToCart() {
        try {
            ProductItem selectedProductItem = (ProductItem) cmbProducts.getSelectedItem();
            if (selectedProductItem == null) {
                throw new IllegalArgumentException("Debes seleccionar un producto.");
            }
 
            int quantity = parseInteger(txtQuantity.getText(), "cantidad");
 
            // Validar stock acumulado si el mismo producto ya está en el carrito.
            int productId = selectedProductItem.product.getId();
            int alreadyInCart = 0;
            for (SaleDetail d : cart) {
                if (d.getProduct().getId() == productId) {
                    alreadyInCart += d.getQuantity();
                }
            }
            if (selectedProductItem.product.getStock() < alreadyInCart + quantity) {
                throw new IllegalArgumentException(
                        "Stock insuficiente para el producto: " + selectedProductItem.product.getName());
            }
 
            SaleDetail detail = saleService.createSaleDetail(productId, quantity);
            cart.add(detail);
            refreshCartTable();
            txtQuantity.setText("");
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }
 
    private void removeSelectedFromCart() {
        int row = tblCart.getSelectedRow();
        if (row < 0) {
            showError("Selecciona una fila del carrito para eliminar.");
            return;
        }
        cart.remove(row);
        refreshCartTable();
    }
 
    private void clearCart() {
        cart.clear();
        refreshCartTable();
    }
 
    private void refreshCartTable() {
        DefaultTableModel model = (DefaultTableModel) tblCart.getModel();
        model.setRowCount(0);
        double total = 0.0;
        for (SaleDetail d : cart) {
            model.addRow(new Object[]{
                    d.getProduct().getId(),
                    d.getProduct().getName(),
                    d.getQuantity(),
                    d.getUnitPrice(),
                    d.getSubtotal()
            });
            total += d.getSubtotal();
        }
        lblTotal.setText("Total: " + total);
    }
 
    // =========================================================
    // Confirmar venta
    // =========================================================
    private void confirmSale() {
        try {
            ClientItem selectedClientItem = (ClientItem) cmbClients.getSelectedItem();
            if (selectedClientItem == null) {
                throw new IllegalArgumentException("Debes seleccionar un cliente.");
            }
            if (cart.isEmpty()) {
                throw new IllegalArgumentException("El carrito está vacío.");
            }
 
            int idSale = generateNextSaleId();
            saleService.registerSale(idSale, selectedClientItem.client.getId(), cart);
 
            // Limpiar estado tras venta exitosa.
            cart.clear();
            txtQuantity.setText("");
            refreshCartTable();
            reloadData();
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
 
    // =========================================================
    // Utilidades
    // =========================================================
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
 
    // =========================================================
    // Items para combos
    // =========================================================
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
}