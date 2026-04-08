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

    private int id_product;
    private String name_product;
    private String size;
    private String color;
    private double price;
    private int stock;

    public Product() {
    }

    public Product(int id, String name, String size, String color, double price, int stock) {
        this.id_product = id;
        this.name_product = name;
        this.size = size;
        this.color = color;
        this.price = price;
        this.stock = stock;
    }

    public int getId() {
        return id_product;
    }

    public void setId(int id) {
        this.id_product = id;
    }

    public String getName() {
        return name_product;
    }

    public void setName(String name) {
        this.name_product = name;
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
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
