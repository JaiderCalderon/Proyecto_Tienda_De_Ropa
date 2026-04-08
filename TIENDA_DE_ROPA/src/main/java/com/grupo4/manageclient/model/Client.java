/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo4.manageclient.model;

/**
 *
 * @author angel
 */
public class Client {

    private int idClient;
    private String nameClient;
    private String email;
    private String phoneNumber;

    public Client() {
    }

    public Client(int idClient, String nameClient, String email, String phoneNumber) {
        this.idClient = idClient;
        this.nameClient = nameClient;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return idClient;
    }

    public void setId(int idClient) {
        this.idClient = idClient;
    }

    public String getName() {
        return nameClient;
    }

    public void setName(String nameClient) {
        this.nameClient = nameClient;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
