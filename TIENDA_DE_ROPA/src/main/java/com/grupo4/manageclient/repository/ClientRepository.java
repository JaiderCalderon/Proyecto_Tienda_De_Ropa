/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo4.manageclient.repository;

import com.grupo4.manageclient.repository.interfaces.IClientRepository;
import com.grupo4.manageclient.model.Client;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author angel
 */

public class ClientRepository implements IClientRepository {
    
    private static final String FILE_PATH = "data/clients.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final List<Client> clients;

    public ClientRepository() {
        this.clients = loadFromFile();
    }

    @Override
    public void save(Client client) {
        clients.add(client);
        saveToFile();
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
                saveToFile();
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
                saveToFile();
                return true;
            }

        }
        return false;
    }
    private List<Client> loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (Reader reader = new FileReader(file)) {
            Type listType = new TypeToken<List<Client>>() {}.getType();
            List<Client> loaded = gson.fromJson(reader, listType);
            return loaded != null ? loaded : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error al cargar clients.json: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void saveToFile() {
        try {
            Files.createDirectories(Paths.get("data"));
            try (Writer writer = new FileWriter(FILE_PATH)) {
                gson.toJson(clients, writer);
            }
        } catch (IOException e) {
            System.err.println("Error al guardar clients.json: " + e.getMessage());
        }
    }
}