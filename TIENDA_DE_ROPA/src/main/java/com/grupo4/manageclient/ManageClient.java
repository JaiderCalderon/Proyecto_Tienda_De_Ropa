/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo4.manageclient;

import com.grupo4.manageclient.repository.ClientRepository;
import com.grupo4.manageclient.repository.ProductRepository;
import com.grupo4.manageclient.repository.SaleRepository;
import com.grupo4.manageclient.service.ClientService;
import com.grupo4.manageclient.service.ProductService;
import com.grupo4.manageclient.service.SaleService;
import com.grupo4.manageclient.view.ClientPanel;
import com.grupo4.manageclient.view.ProductPanel;
import com.grupo4.manageclient.view.SalePanel;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author angel
 */
public class ManageClient {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            configureLookAndFeel();
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }

    private static void configureLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
    }
}

class MainFrame extends JFrame {

    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final ClientService clientService;
    private final ProductService productService;
    private final SaleService saleService;
    private final ClientPanel clientPanel;
    private final ProductPanel productPanel;
    private final SalePanel salePanel;

    MainFrame() {
        this.clientRepository = new ClientRepository();
        this.productRepository = new ProductRepository();
        this.saleRepository = new SaleRepository();
        this.clientService = new ClientService(clientRepository);
        this.productService = new ProductService(productRepository);
        this.saleService = new SaleService(saleRepository, clientService, productService);
        this.clientPanel = new ClientPanel(clientService);
        this.productPanel = new ProductPanel(productService);
        this.salePanel = new SalePanel(saleService, clientService, productService, null);

        initializeFrame();
    }

    private void initializeFrame() {
        setTitle("Sistema de Venta de Ropa");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTabbedPane mainTabbedPane = new JTabbedPane();
        mainTabbedPane.addTab("Clientes", clientPanel);
        mainTabbedPane.addTab("Productos", productPanel);
        mainTabbedPane.addTab("Ventas", salePanel);
        mainTabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                clientPanel.reloadData();
                productPanel.reloadData();
                salePanel.reloadData();
            }
        });

        add(mainTabbedPane, BorderLayout.CENTER);
    }
}
