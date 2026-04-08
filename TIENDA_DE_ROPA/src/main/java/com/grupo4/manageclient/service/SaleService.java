/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo4.manageclient.service;

import com.grupo4.manageclient.model.Client;
import com.grupo4.manageclient.model.Product;
import com.grupo4.manageclient.model.Sale;
import com.grupo4.manageclient.model.SaleDetail;
import com.grupo4.manageclient.repository.ISaleRepository;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author samue
 */
public class SaleService {

    private final ISaleRepository saleRepository;
    private final ClientService clientService;
    private final ProductService productService;

    public SaleService(ISaleRepository saleRepository, ClientService clientService, ProductService productService) {
        this.saleRepository = saleRepository;
        this.clientService = clientService;
        this.productService = productService;
    }

    public SaleDetail createSaleDetail(int idProduct, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }

        Product product = productService.findProductById(idProduct);

        if (product == null) {
            throw new IllegalArgumentException("Producto no encontrado.");
        }

        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("Stock insuficiente para el producto: " + product.getName());
        }

        return new SaleDetail(product, quantity);
    }

    public void registerSale(int idSale, int idClient, List<SaleDetail> details) {
        if (idSale <= 0) {
            throw new IllegalArgumentException("El ID de la venta debe ser mayor que cero.");
        }

        if (saleRepository.findById(idSale) != null) {
            throw new IllegalArgumentException("Ya existe una venta con ese ID.");
        }

        Client client = clientService.findClientById(idClient);

        if (client == null) {
            throw new IllegalArgumentException("Cliente no encontrado.");
        }

        if (details == null || details.isEmpty()) {
            throw new IllegalArgumentException("La venta debe tener al menos un producto.");
        }

        validateDetails(details);
        discountStock(details);

        Sale sale = new Sale(idSale, client, new ArrayList<>(details));
        saleRepository.save(sale);
    }

    public List<Sale> getAllSales() {
        return saleRepository.getAllSales();
    }

    public Sale findSaleById(int idSale) {
        Sale sale = saleRepository.findById(idSale);

        if (sale == null) {
            throw new IllegalArgumentException("Venta no encontrada.");
        }

        return sale;
    }

    private void validateDetails(List<SaleDetail> details) {
        for (SaleDetail detail : details) {
            if (detail.getProduct() == null) {
                throw new IllegalArgumentException("Hay un detalle sin producto.");
            }

            if (detail.getQuantity() <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
            }

            Product currentProduct = productService.findProductById(detail.getProduct().getId());

            if (currentProduct == null) {
                throw new IllegalArgumentException("Producto no encontrado: " + detail.getProduct().getName());
            }

            if (currentProduct.getStock() < detail.getQuantity()) {
                throw new IllegalArgumentException("Stock insuficiente para el producto: " + currentProduct.getName());
            }
        }
    }

    private void discountStock(List<SaleDetail> details) {
        for (SaleDetail detail : details) {
            Product product = productService.findProductById(detail.getProduct().getId());
            int newStock = product.getStock() - detail.getQuantity();

            productService.updateProduct(
                    product.getId(),
                    product.getName(),
                    product.getSize(),
                    product.getColor(),
                    product.getPrice(),
                    newStock);
        }
    }
}
