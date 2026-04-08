/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo4.manageclient.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author samuel
 */
public class Sale {

    private int id_sale;
    private Client client;
    private List<SaleDetail> details;
    private double total;

    public Sale() {
        this.details = new ArrayList<>();
    }

    public Sale(int id_sale, Client client, List<SaleDetail> details) {
        this.id_sale = id_sale;
        this.client = client;
        this.details = new ArrayList<>();
        calculateTotal();
    }

    public int getId_sale() {
        return id_sale;
    }

    public void setId_sale(int id_sale) {
        this.id_sale = id_sale;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<SaleDetail> getDetails() {
        return details;
    }

    public void setDetails(List<SaleDetail> details) {
        this.details = details;
        calculateTotal();
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void addDetail(SaleDetail detail) {
        this.details.add(detail);
        calculateTotal();
    }

    private void calculateTotal() {
        this.total = 0;
        for (SaleDetail detail : details) {
            this.total += detail.getSubtotal();
        }
    }

}
