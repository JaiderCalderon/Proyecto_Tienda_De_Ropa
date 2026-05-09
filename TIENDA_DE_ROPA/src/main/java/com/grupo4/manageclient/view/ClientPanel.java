/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.grupo4.manageclient.view;

import com.grupo4.manageclient.model.Client;
import com.grupo4.manageclient.service.IClientService;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author samuel
 */
public class ClientPanel extends javax.swing.JPanel {

    private final IClientService clientService;

    public ClientPanel(IClientService clientService) {
        this.clientService = clientService;
        initComponents();
        configureTable();
        bindEvents();
        refreshClientTable();
    }

    private void configureTable() {
        tblClients.setModel(new DefaultTableModel(
                new Object[] { "ID", "Nombre", "Correo", "Teléfono" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        tblClients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void bindEvents() {
        btnCreateClient.addActionListener(e -> clearForm());
        btnClearClientForm.addActionListener(e -> clearForm());
        btnSaveClient.addActionListener(e -> saveClient());
        btnUpdateClient.addActionListener(e -> updateClient());
        btnDeleteClient.addActionListener(e -> deleteClient());

        tblClients.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                loadSelectedClient();
            }
        });
    }

    private void saveClient() {
        try {
            clientService.registerClient(
                    parseInteger(txtClientId.getText(), "ID del cliente"),
                    txtClientName.getText().trim(),
                    txtClientEmail.getText().trim(),
                    txtClientPhone.getText().trim());

            refreshClientTable();
            clearForm();
            showMessage("Cliente registrado correctamente.");
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void updateClient() {
        try {
            clientService.updateClient(
                    parseInteger(txtClientId.getText(), "ID del cliente"),
                    txtClientName.getText().trim(),
                    txtClientEmail.getText().trim(),
                    txtClientPhone.getText().trim());

            refreshClientTable();
            clearForm();
            showMessage("Cliente actualizado correctamente.");
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void deleteClient() {
    try {
        int selectedRow = tblClients.getSelectedRow();
        if (selectedRow == -1) {
            throw new IllegalArgumentException("Selecciona un cliente para eliminar.");
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de que deseas eliminar este cliente?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        int idClient = Integer.parseInt(tblClients.getValueAt(selectedRow, 0).toString());
        clientService.deleteClient(idClient);
        refreshClientTable();
        clearForm();
        showMessage("Cliente eliminado correctamente.");
    } catch (Exception ex) {
        showError(ex.getMessage());
    }
}

    private void refreshClientTable() {
        DefaultTableModel model = (DefaultTableModel) tblClients.getModel();
        model.setRowCount(0);

        List<Client> clients = clientService.getAllClients();
        for (Client client : clients) {
            model.addRow(new Object[] {
                    client.getId(),
                    client.getName(),
                    client.getEmail(),
                    client.getPhoneNumber()
            });
        }
    }

    public void reloadData() {
        refreshClientTable();
    }

    private void loadSelectedClient() {
        int selectedRow = tblClients.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        txtClientId.setText(tblClients.getValueAt(selectedRow, 0).toString());
        txtClientName.setText(tblClients.getValueAt(selectedRow, 1).toString());
        txtClientEmail.setText(tblClients.getValueAt(selectedRow, 2).toString());
        txtClientPhone.setText(tblClients.getValueAt(selectedRow, 3).toString());
    }

    private void clearForm() {
        txtClientId.setText("");
        txtClientName.setText("");
        txtClientEmail.setText("");
        txtClientPhone.setText("");
        tblClients.clearSelection();
        txtClientId.requestFocus();
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tlbClient = new javax.swing.JToolBar();
        btnCreateClient = new javax.swing.JButton();
        btnUpdateClient = new javax.swing.JButton();
        btnDeleteClient = new javax.swing.JButton();
        btnClearClientForm = new javax.swing.JButton();
        lblClientId = new javax.swing.JLabel();
        lblClientName = new javax.swing.JLabel();
        lblClientEmail = new javax.swing.JLabel();
        lblClientPhone = new javax.swing.JLabel();
        txtClientId = new javax.swing.JTextField();
        txtClientName = new javax.swing.JTextField();
        txtClientEmail = new javax.swing.JTextField();
        txtClientPhone = new javax.swing.JTextField();
        btnSaveClient = new javax.swing.JButton();
        sepFormClientsTable = new javax.swing.JSeparator();
        lblClientsTable = new javax.swing.JLabel();
        scrClients = new javax.swing.JScrollPane();
        scrClientsWrapper = new javax.swing.JScrollPane();
        tblClients = new javax.swing.JTable();

        tlbClient.setRollover(true);

        btnCreateClient.setText("Crear Cliente");
        btnCreateClient.setFocusable(false);
        btnCreateClient.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCreateClient.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tlbClient.add(btnCreateClient);

        btnUpdateClient.setText("Editar Cliente");
        btnUpdateClient.setFocusable(false);
        btnUpdateClient.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUpdateClient.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tlbClient.add(btnUpdateClient);

        btnDeleteClient.setText("Eliminar Cliente");
        btnDeleteClient.setFocusable(false);
        btnDeleteClient.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDeleteClient.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tlbClient.add(btnDeleteClient);

        btnClearClientForm.setText("Limpiar Formulario");
        btnClearClientForm.setFocusable(false);
        btnClearClientForm.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnClearClientForm.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tlbClient.add(btnClearClientForm);

        lblClientId.setText("ID:");

        lblClientName.setText("Nombre:");

        lblClientEmail.setText("Correo:");

        lblClientPhone.setText("Teléfono:");

        btnSaveClient.setText("Guardar");

        lblClientsTable.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblClientsTable.setText("Tabla De Clientes");
        lblClientsTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblClients.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Correo", "Teléfono"
            }
        ));
        scrClientsWrapper.setViewportView(tblClients);

        scrClients.setViewportView(scrClientsWrapper);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnSaveClient)
                .addGap(333, 333, 333))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lblClientName)
                                    .addComponent(lblClientPhone)
                                    .addComponent(lblClientEmail)
                                    .addComponent(lblClientId))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtClientId, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                                    .addComponent(txtClientName)
                                    .addComponent(txtClientEmail)
                                    .addComponent(txtClientPhone)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(238, 238, 238)
                                .addComponent(lblClientsTable, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 254, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scrClients))
                    .addComponent(sepFormClientsTable))
                .addContainerGap())
            .addComponent(tlbClient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tlbClient, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblClientId)
                    .addComponent(txtClientId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtClientName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblClientName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblClientEmail)
                    .addComponent(txtClientEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblClientPhone)
                    .addComponent(txtClientPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addComponent(btnSaveClient)
                .addGap(24, 24, 24)
                .addComponent(sepFormClientsTable, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblClientsTable, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(scrClients, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClearClientForm;
    private javax.swing.JButton btnCreateClient;
    private javax.swing.JButton btnDeleteClient;
    private javax.swing.JButton btnSaveClient;
    private javax.swing.JButton btnUpdateClient;
    private javax.swing.JScrollPane scrClientsWrapper;
    private javax.swing.JLabel lblClientEmail;
    private javax.swing.JLabel lblClientId;
    private javax.swing.JLabel lblClientName;
    private javax.swing.JLabel lblClientPhone;
    private javax.swing.JLabel lblClientsTable;
    private javax.swing.JScrollPane scrClients;
    private javax.swing.JSeparator sepFormClientsTable;
    private javax.swing.JTable tblClients;
    private javax.swing.JToolBar tlbClient;
    private javax.swing.JTextField txtClientEmail;
    private javax.swing.JTextField txtClientId;
    private javax.swing.JTextField txtClientName;
    private javax.swing.JTextField txtClientPhone;
    // End of variables declaration//GEN-END:variables
}
