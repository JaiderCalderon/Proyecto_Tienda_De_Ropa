/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo4.manageclient.repository.interfaces;

import com.grupo4.manageclient.model.Sale;
import java.util.List;

/**
 *
 * @author samue
 */
public interface ISaleRepository {
    void save(Sale sale);

    List<Sale> getAllSales();

    Sale findById(int idSale);

}
