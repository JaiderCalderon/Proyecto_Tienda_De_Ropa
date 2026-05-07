/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo4.manageclient.service;

import com.grupo4.manageclient.model.Client;
import com.grupo4.manageclient.repository.interfaces.IClientRepository;
import java.util.List;

/**
 *
 * @author angel
 */
public class ClientService implements IClientService {

    private final IClientRepository clientRepository;

    public ClientService(IClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    private void validateClientData(int idClient, String nameClient, String email, String phoneNumber) {
    if (idClient <= 0) {
        throw new IllegalArgumentException("El ID debe ser un número positivo mayor que cero.");
    }

    if (nameClient == null || nameClient.trim().isEmpty()) {
        throw new IllegalArgumentException("El nombre es obligatorio.");
    }

    if (email == null || email.trim().isEmpty()) {
        throw new IllegalArgumentException("El correo es obligatorio.");
    }
    if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
        throw new IllegalArgumentException("El correo no tiene un formato válido. Ejemplo: usuario@dominio.com");
    }

    if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
        throw new IllegalArgumentException("El teléfono es obligatorio.");
    }
    if (!phoneNumber.matches("^\\d{7,10}$")) {
        throw new IllegalArgumentException("El teléfono debe contener solo números y tener entre 7 y 10 dígitos.");
    }
}

    @Override
    public void registerClient(int idClient, String nameClient, String email, String phoneNumber) {
        validateClientData(idClient, nameClient, email, phoneNumber);

        if (clientRepository.findById(idClient) != null) {
            throw new IllegalArgumentException("El ID ya existe. Por favor, elige otro.");
        }

        Client client = new Client(idClient, nameClient, email, phoneNumber);
        clientRepository.save(client);

    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.getAllClients();
    }

    @Override
    public Client findClientById(int idClient) {
        Client client = clientRepository.findById(idClient);
        if (client == null) {
            throw new IllegalArgumentException("Cliente no encontrado.");
        }
        return client;
    }

    @Override
    public void updateClient(int idClient, String nameClient, String email, String phoneNumber) {
        validateClientData(idClient, nameClient, email, phoneNumber);

        Client existingClient = clientRepository.findById(idClient);

        if (existingClient == null) {
            throw new IllegalArgumentException("Cliente no encontrado.");
        }

        Client updatedClient = new Client(idClient, nameClient, email, phoneNumber);
        boolean update = clientRepository.update(updatedClient);

        if (!update) {
            throw new IllegalStateException("Error al actualizar el cliente.");
        }
    }

    @Override
    public void deleteClient(int idClient) {
        Client existingClient = clientRepository.findById(idClient);

        if (existingClient == null) {
            throw new IllegalArgumentException("Cliente no encontrado.");
        }
        boolean deleted = clientRepository.delete(idClient);

        if (!deleted) {
            throw new IllegalStateException("Error al eliminar el cliente.");
        }
    }
}
