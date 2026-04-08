/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo4.manageclient.model;

/**
 *
 * @author samuel
 */
public class SaleDetail {

    private Product product;
    private int quantity;
    private double unitPrice;
    private double subtotal;

    public SaleDetail() {
    }

    public SaleDetail(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = product.getPrice();
        this.subtotal = quantity * unitPrice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        recalculateSubtotal();
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
        recalculateSubtotal();
    }

    public double getSubtotal() {
        return subtotal;
    }

    private void recalculateSubtotal() {
        this.subtotal = this.unitPrice * this.quantity;
    }

}
