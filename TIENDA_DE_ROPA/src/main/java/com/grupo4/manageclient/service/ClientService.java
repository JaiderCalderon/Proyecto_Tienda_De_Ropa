/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo4.manageclient.service;

import com.grupo4.manageclient.model.Client;
import com.grupo4.manageclient.repository.IClientRepository;
import java.util.List;

/**
 *
 * @author angel
 */
public class ClientService {

    private final IClientRepository clientRepository;

    public ClientService(IClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    private void validateClientData(int id, String name, String email, String phoneNumber) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser un número positivo mayor que cero.");
        }

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }

        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo es obligatorio.");
        }
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("El teléfono es obligatorio.");
        }
    }

    public void registerClient(int id, String name, String email, String phoneNumber) {
        validateClientData(id, name, email, phoneNumber);

        if (clientRepository.findById(id) != null) {
            throw new IllegalArgumentException("El ID ya existe. Por favor, elige otro.");
        }

        Client client = new Client(id, name, email, phoneNumber);
        clientRepository.save(client);

    }

    public List<Client> getAllClients() {
        return clientRepository.getAllClients();
    }

    public Client findClientById(int id) {
        Client client = clientRepository.findById(id);
        if (client == null) {
            throw new IllegalArgumentException("Cliente no encontrado.");
        }
        return client;
    }

    public void updateClient(int id, String name, String email, String phoneNumber) {
        validateClientData(id, name, email, phoneNumber);

        Client existingClient = clientRepository.findById(id);

        if (existingClient == null) {
            throw new IllegalArgumentException("Cliente no encontrado.");
        }

        Client updatedClient = new Client(id, name, email, phoneNumber);
        boolean update = clientRepository.update(updatedClient);

        if (!update) {
            throw new IllegalStateException("Error al actualizar el cliente.");
        }
    }

    public void deleteClient(int id) {
        Client existingClient = clientRepository.findById(id);

        if (existingClient == null) {
            throw new IllegalArgumentException("Cliente no encontrado.");
        }
        boolean deleted = clientRepository.delete(id);

        if (!deleted) {
            throw new IllegalStateException("Error al eliminar el cliente.");
        }
    }
}
