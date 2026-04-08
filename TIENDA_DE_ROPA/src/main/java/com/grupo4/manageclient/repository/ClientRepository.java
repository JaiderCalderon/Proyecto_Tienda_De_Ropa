/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo4.manageclient.repository;

import com.grupo4.manageclient.model.Client;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author angel
 */
public class ClientRepository implements IClientRepository {

    private final List<Client> clients;

    public ClientRepository() {
        this.clients = new ArrayList<>();
    }

    @Override
    public void save(Client client) {
        clients.add(client);
    }

    @Override
    public List<Client> getAllClients() {
        return new ArrayList<>(clients);
    }

    @Override
    public Client findById(int idClient) {
        for (Client client : clients) {
            if (client.getId() == idClient) {
                return client;
            }
        }
        return null;
    }

    @Override
    public boolean update(Client updateClient) {
        for (int i = 0; i < clients.size(); i++) {
            Client currentClient = clients.get(i);

            if (currentClient.getId() == updateClient.getId()) {
                clients.set(i, updateClient);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(int idClient) {
        for (int i = 0; i < clients.size(); i++) {
            Client currentClient = clients.get(i);
            if (currentClient.getId() == idClient) {
                clients.remove(i);
                return true;
            }

        }
        return false;
    }
}
