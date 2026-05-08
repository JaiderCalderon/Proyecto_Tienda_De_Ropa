/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo4.manageclient.repository;

import com.grupo4.manageclient.repository.interfaces.IProductRepository;
import com.grupo4.manageclient.model.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author angel
 */
public class ProductRepository implements IProductRepository {

    private static final String FILE_PATH = "data/products.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    private final List<Product> products;

    public ProductRepository() {
        this.products = loadFromFile();
    }

    @Override
    public void save(Product product) {
        products.add(product);
        saveToFile();
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    @Override
    public Product findById(int idProduct) {
        for (Product product : products) {
            if (product.getId() == idProduct) {
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
                saveToFile();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(int idProduct) {
        for (int i = 0; i < products.size(); i++) {
            Product currentProduct = products.get(i);

            if (currentProduct.getId() == idProduct) {
                products.remove(i);
                saveToFile();
                return true;
            }
        }
        return false;
    }
    
    private List<Product> loadFromFile() {
    File file = new File(FILE_PATH);
    if (!file.exists()) return new ArrayList<>();
    try (Reader reader = new FileReader(file)) {
        Type listType = new TypeToken<List<Product>>(){}.getType();
        List<Product> loaded = gson.fromJson(reader, listType);
        return loaded != null ? loaded : new ArrayList<>();
    } catch (IOException e) {
        return new ArrayList<>();
    }
}

    private void saveToFile() {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();
            try (Writer writer = new FileWriter(file)) {
                gson.toJson(products, writer);
            }
        } catch (IOException e) {
            System.err.println("Error al guardar products.json: " + e.getMessage());
        }
    }
}
