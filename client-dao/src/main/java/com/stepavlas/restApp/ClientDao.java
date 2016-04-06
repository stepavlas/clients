package com.stepavlas.restApp;

import com.stepavals.restApp.Client;
import com.stepavals.restApp.exceptions.IncorrectClientException;

import java.util.List;
import java.util.Map;

/**
 * Created by Степан on 02.04.2016.
 */
public interface ClientDao {
    List<Client> findClients(Client client) throws IncorrectClientException;

    Client getById(Client client) throws IncorrectClientException;

    void add(Client client) throws IncorrectClientException;

    Client update(Client client) throws IncorrectClientException;

    Client remove(Client client) throws IncorrectClientException;

    Map<Long, Client> getClients();
}
