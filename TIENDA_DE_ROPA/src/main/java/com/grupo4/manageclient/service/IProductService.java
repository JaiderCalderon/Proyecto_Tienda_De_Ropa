/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Interfaces/Interface.java to edit this template
 */
package com.grupo4.manageclient.service;

import com.grupo4.manageclient.model.Product;
import java.util.List;

public interface IProductService {

    void registerProduct(int idProduct, String nameProduct, String size, String color, double unitPrice, int stock);

    List<Product> getAllProducts();

    Product findProductById(int idProduct);

    void updateProduct(int idProduct, String name, String size, String color, double unitPrice, int stock);

    void deleteProduct(int idProduct);
}
