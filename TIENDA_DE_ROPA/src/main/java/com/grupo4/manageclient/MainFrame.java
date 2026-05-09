package com.grupo4.manageclient;

import com.grupo4.manageclient.repository.ClientRepository;
import com.grupo4.manageclient.repository.ProductRepository;
import com.grupo4.manageclient.repository.SaleRepository;
import com.grupo4.manageclient.service.ClientService;
import com.grupo4.manageclient.service.IClientService;
import com.grupo4.manageclient.service.IProductService;
import com.grupo4.manageclient.service.ISaleService;
import com.grupo4.manageclient.service.ProductService;
import com.grupo4.manageclient.service.SaleService;
import com.grupo4.manageclient.view.ClientPanel;
import com.grupo4.manageclient.view.ProductPanel;
import com.grupo4.manageclient.view.SalePanel;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainFrame extends JFrame {

    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final IClientService clientService;
    private final IProductService productService;
    private final ISaleService saleService;
    private final ClientPanel clientPanel;
    private final ProductPanel productPanel;
    private final SalePanel salePanel;

    MainFrame() {
        this.clientRepository = new ClientRepository();
        this.productRepository = new ProductRepository();
        this.saleRepository = new SaleRepository();
        this.clientService = new ClientService(clientRepository);
        this.productService = new ProductService(productRepository, saleRepository);
        this.saleService = new SaleService(saleRepository, clientService, productService);
        this.clientPanel = new ClientPanel(clientService);
        this.productPanel = new ProductPanel(productService);
        this.salePanel = new SalePanel(saleService, clientService, productService);

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