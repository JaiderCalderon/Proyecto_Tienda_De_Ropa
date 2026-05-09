/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo4.manageclient.repository;

import com.grupo4.manageclient.model.SaleDetail;
import com.grupo4.manageclient.repository.interfaces.ISaleRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import com.grupo4.manageclient.model.Sale;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author samue
 */
public class SaleRepository implements ISaleRepository {
    
    private static final String FILE_PATH = System.getProperty("user.dir") + File.separator + "data" + File.separator + "sales.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final List<Sale> sales;

    public SaleRepository() {
        this.sales = loadFromFile();
    }

    @Override
    public void save(Sale sale) {
        sales.add(sale);
        saveToFile();
    }

    @Override
    public List<Sale> getAllSales() {
        return new ArrayList<>(sales);
    }

    @Override
    public Sale findById(int idSale) {
        for (Sale sale : sales) {
            if (sale.getIdSale() == idSale) {
                return sale;
            }
        }
        return null;

    }

    @Override
    public boolean existsProductInSales(int idProduct) {
        for (Sale sale : sales) {
            for (SaleDetail detail : sale.getDetails()) {
                if (detail.getProduct().getId() == idProduct) {
                    return true;
                }
            }
        }
        return false;
    }

    private List<Sale> loadFromFile() {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(file)) {

            Type listType = new TypeToken<List<Sale>>() {
            }.getType();
            List<Sale> loaded = gson.fromJson(reader, listType);

            return loaded != null ? loaded : new ArrayList<>();

        } catch (IOException e) {

            System.err.println(
                    "Advertencia: No se pudo cargar sales.json. "
                            + "Se iniciará con lista vacía. Detalle: "
                            + e.getMessage());

            return new ArrayList<>();
        }
    }

    private void saveToFile() {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();
        try (Writer writer = new FileWriter(file)) {
            gson.toJson(sales, writer);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar sales.json: " + e.getMessage(), e);
        }
    }
}
