/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Interfaces/Interface.java to edit this template
 */
package com.grupo4.manageclient.service;

import com.grupo4.manageclient.model.Client;
import java.util.List;

public interface IClientService {

    void registerClient(int idClient, String nameClient, String email, String phoneNumber);

    List<Client> getAllClients();

    Client findClientById(int idClient);

    void updateClient(int idClient, String nameClient, String email, String phoneNumber);

    void deleteClient(int idClient);
}
