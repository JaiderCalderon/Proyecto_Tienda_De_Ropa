/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo4.manageclient.repository;

import com.grupo4.manageclient.repository.interfaces.ISaleRepository;
import com.grupo4.manageclient.model.Sale;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author samue
 */
public class SaleRepository implements ISaleRepository {
    private final List<Sale> sales;

    public SaleRepository() {
        this.sales = new ArrayList<>();
    }

    @Override
    public void save(Sale sale) {
        sales.add(sale);
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

}
