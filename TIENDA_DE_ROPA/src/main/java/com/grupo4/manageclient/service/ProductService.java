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
public class ProductService implements IProductService {
    private final IProductRepository productRepository;

    public ProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private void validateProductData(int idProduct, String nameProduct, String size, String color, double unitPrice,
            int stock) {
        if (idProduct <= 0) {
            throw new IllegalArgumentException("El ID del producto debe ser un número positivo.");
        }
        if (nameProduct == null || nameProduct.trim().isEmpty()) {
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

    @Override
    public void registerProduct(int idProduct, String nameProduct, String size, String color, double unitPrice,
            int stock) {
        validateProductData(idProduct, nameProduct, size, color, unitPrice, stock);

        if (productRepository.findById(idProduct) != null) {
            throw new IllegalArgumentException("El ID ya existe. Por favor, elige otro.");
        }

        Product product = new Product(idProduct, nameProduct, size, color, unitPrice, stock);
        productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    @Override
    public Product findProductById(int idProduct) {
        Product product = productRepository.findById(idProduct);
        if (product == null) {
            throw new IllegalArgumentException("Producto no encontrado.");
        }
        return product;
    }

    @Override
    public void updateProduct(int idProduct, String name, String size, String color, double unitPrice, int stock) {
        validateProductData(idProduct, name, size, color, unitPrice, stock);

        Product existingProduct = productRepository.findById(idProduct);

        if (existingProduct == null) {
            throw new IllegalArgumentException("Producto no encontrado.");
        }

        Product updatedProduct = new Product(idProduct, name, size, color, unitPrice, stock);
        boolean update = productRepository.update(updatedProduct);

        if (!update) {
            throw new RuntimeException("Error al actualizar el producto.");
        }
    }

    @Override
    public void deleteProduct(int idProduct) {
        Product existingProduct = productRepository.findById(idProduct);

        if (existingProduct == null) {
            throw new IllegalArgumentException("Producto no encontrado.");
        }

        boolean deleted = productRepository.delete(idProduct);

        if (!deleted) {
            throw new RuntimeException("Error al eliminar el producto.");
        }
    }
}
