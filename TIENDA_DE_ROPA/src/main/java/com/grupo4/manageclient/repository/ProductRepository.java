/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo4.manageclient.repository;

import com.grupo4.manageclient.model.Product;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author angel
 */
public class ProductRepository implements IProductRepository {

    private List<Product> products;

    public ProductRepository() {
        this.products = new ArrayList<>();
    }

    @Override
    public void save(Product product) {
        products.add(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    @Override
    public Product findById(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    @Override
    public boolean update(Product updateProduct) {
        for (int i = 0; i < products.size(); i++) {
            Product currentProduct = products.get(i);

            if (currentProduct.getId() == updateProduct.getId()) {
                products.set(i, updateProduct);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        for (int i = 0; i < products.size(); i++) {
            Product currentProduct = products.get(i);

            if (currentProduct.getId() == id) {
                products.remove(i);
                return true;
            }
        }
        return false;
    }
}
