/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Interfaces/Interface.java to edit this template
 */
package com.grupo4.manageclient.service;

import com.grupo4.manageclient.model.Sale;
import com.grupo4.manageclient.model.SaleDetail;
import java.util.List;

public interface ISaleService {

    SaleDetail createSaleDetail(int idProduct, int quantity);

    void registerSale(int idSale, int idClient, List<SaleDetail> details);

    List<Sale> getAllSales();

    Sale findSaleById(int idSale);
}
