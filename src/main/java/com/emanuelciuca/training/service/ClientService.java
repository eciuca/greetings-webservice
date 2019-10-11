package com.emanuelciuca.training.service;

import com.emanuelciuca.training.model.Client;

import java.time.LocalDate;
import java.util.List;

public interface ClientService {

    List<Client> getAllClients();

    Client createClient(String firstName, String lastName, LocalDate dateOfBirth);

    void deleteClient(Long id);
}