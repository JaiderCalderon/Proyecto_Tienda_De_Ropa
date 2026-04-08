/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.grupo4.manageclient.repository;

import com.grupo4.manageclient.model.Client;
import java.util.List;

/**
 *
 * @author angel
 */
public interface IClientRepository {

    void save(Client client);

    List<Client> getAllClients();

    Client findById(int idClient);

    boolean update(Client client);

    boolean delete(int idClient);

}
