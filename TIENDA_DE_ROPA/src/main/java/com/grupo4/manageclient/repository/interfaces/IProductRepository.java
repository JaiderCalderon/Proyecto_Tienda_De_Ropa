/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.grupo4.manageclient.repository.interfaces;

import com.grupo4.manageclient.model.Product;
import java.util.List;

/**
 *
 * @author angel
 */
public interface IProductRepository {
    void save(Product product);

    List<Product> getAllProducts();

    Product findById(int idProduct);

    boolean update(Product product);

    boolean delete(int idProduct);

}
