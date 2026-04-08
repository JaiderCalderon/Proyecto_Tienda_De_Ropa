/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo4.manageclient.service;

import com.grupo4.manageclient.model.Product;
import com.grupo4.manageclient.repository.IProductRepository;
import java.util.List;

/**
 *
 * @author angel
 */
public class ProductService {
    private final IProductRepository productRepository;

    public ProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private void validateProductData(int id_product, String name, String size, String color, double unitPrice,
            int stock) {
        if (id_product <= 0) {
            throw new IllegalArgumentException("El ID del producto debe ser un número positivo.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío.");
        }
        if (size == null || size.trim().isEmpty()) {
            throw new IllegalArgumentException("El tamaño del producto no puede estar vacío.");
        }
        if (color == null || color.trim().isEmpty()) {
            throw new IllegalArgumentException("El color del producto no puede estar vacío.");
        }
        if (unitPrice < 0) {
            throw new IllegalArgumentException("El precio unitario del producto no puede ser negativo.");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("El stock del producto no puede ser negativo.");
        }
    }

    public void registerProduct(int id_product, String name, String size, String color, double unitPrice, int stock) {
        validateProductData(id_product, name, size, color, unitPrice, stock);

        if (productRepository.findById(id_product) != null) {
            throw new IllegalArgumentException("El ID ya existe. Por favor, elige otro.");
        }

        Product product = new Product(id_product, name, size, color, unitPrice, stock);
        productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public Product findProductById(int id_product) {
        Product product = productRepository.findById(id_product);
        if (product == null) {
            throw new IllegalArgumentException("Producto no encontrado.");
        }
        return product;
    }

    public void updateProduct(int id_product, String name, String size, String color, double unitPrice, int stock) {
        validateProductData(id_product, name, size, color, unitPrice, stock);

        Product existingProduct = productRepository.findById(id_product);

        if (existingProduct == null) {
            throw new IllegalArgumentException("Producto no encontrado.");
        }

        Product updatedProduct = new Product(id_product, name, size, color, unitPrice, stock);
        boolean update = productRepository.update(updatedProduct);

        if (!update) {
            throw new RuntimeException("Error al actualizar el producto.");
        }
    }

    public void deleteProduct(int id_product) {
        Product existingProduct = productRepository.findById(id_product);

        if (existingProduct == null) {
            throw new IllegalArgumentException("Producto no encontrado.");
        }

        boolean deleted = productRepository.delete(id_product);

        if (!deleted) {
            throw new RuntimeException("Error al eliminar el producto.");
        }
    }
}
