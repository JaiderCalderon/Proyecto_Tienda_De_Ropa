/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo4.manageclient.model;

/**
 *
 * @author angel
 */
public class Product {

    private int idProduct;
    private String nameProduct;
    private String size;
    private String color;
    private double unitPrice;
    private int stock;

    public Product() {
    }

    public Product(int idProduct, String nameProduct, String size, String color, double unitPrice, int stock) {
        this.idProduct = idProduct;
        this.nameProduct = nameProduct;
        this.size = size;
        this.color = color;
        this.unitPrice = unitPrice;
        this.stock = stock;
    }

    public int getId() {
        return idProduct;
    }

    public void setId(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return nameProduct;
    }

    public void setName(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getPrice() {
        return unitPrice;
    }

    public void setPrice(double price) {
        this.unitPrice = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
