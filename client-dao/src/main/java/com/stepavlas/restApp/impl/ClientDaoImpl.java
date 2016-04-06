package com.stepavlas.restApp.impl;

import com.stepavals.restApp.Client;
import com.stepavals.restApp.exceptions.IncorrectClientException;
import com.stepavlas.restApp.ClientDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Степан on 02.04.2016.
 */
public class ClientDaoImpl implements ClientDao {
    private Map<Long, Client> clients = new HashMap<>();

    @Override
    public List<Client> findClients(Client client) throws IncorrectClientException{
        validate(client);
        List<Client> result = new ArrayList<>();
        if (client.getId() != 0){
            Client dbUser = getById(client);
            if (dbUser != null){
                result.add(dbUser);
            }
        } else if (client.getFirstName() != null || client.getLastName() != null){
            result = findByName(client);
        } else if (client.getGender() != null){
            result = findByGender(client);
        } else if (client.getCity() != null){
            result = findByCity(client);
        } else if (client.getCountry() != null){
            result = findByCountry(client);
        }
        return result;
    }

    private void validate(Client client) throws IncorrectClientException{
        if (client == null){
            throw new IllegalArgumentException("Client argument is null");
        }
        if (client.getId() < 0){
            throw new IncorrectClientException("client has negative id");
        }
        if (client.getGender() != null && !client.getGender().equals('M') &&
                !client.getGender().equals('F')){
            throw new IncorrectClientException("client has incorrect gender");
        }

    }

    @Override
    public Client getById(Client client) throws IncorrectClientException{
        validate(client);
        if (client.getId() == 0){
            throw new IncorrectClientException("Client doesn't have id");
        }
        return clients.get(client.getId());
    }

    private List<Client> findByName(Client client) throws IncorrectClientException{
        List<Client> foundClients = new ArrayList<>();
        for (Client dbClient: clients.values()){
            if ((client.getFirstName() != null && client.getFirstName().equals(dbClient.getFirstName()))
                    || (client.getLastName() != null && client.getLastName().equals(dbClient.getLastName()))){
                foundClients.add(dbClient);
            }
        }
        return foundClients;
    }

    private List<Client> findByGender(Client client) throws IncorrectClientException {
        List<Client> foundClients = new ArrayList<>();
        for (Client dbClient: clients.values()){
            if (client.getGender().equals(dbClient.getGender())){
                foundClients.add(dbClient);
            }
        }
        return foundClients;
    }

    private List<Client> findByCity(Client client) throws IncorrectClientException {
        List<Client> foundClients = new ArrayList<>();
        for (Client dbClient: clients.values()){
            if (client.getCity().equals(dbClient.getCity())){
                foundClients.add(dbClient);
            }
        }
        return foundClients;
    }

    private List<Client> findByCountry(Client client) throws IncorrectClientException {
        List<Client> foundClients = new ArrayList<>();
        for (Client dbClient: clients.values()){
            if (client.getCountry().equals(dbClient.getCountry())){
                foundClients.add(dbClient);
            }
        }
        return foundClients;
    }

    @Override
    public void add(Client client) throws IncorrectClientException{
        validate(client);
        if (client.getId() != 0 || client.getFirstName() == null || "".equals(client.getFirstName())
                || client.getLastName() == null || "".equals(client.getLastName())
                || client.getGender() == null){
            throw new IncorrectClientException("User doesn't have mandatory field for adding");
        }
        List<Client> dbUsers = findClients(client);
        if (!dbUsers.isEmpty()) {
            throw new IncorrectClientException("User already exists");
        }
        client.setId(clients.size() + 1);
        clients.put(client.getId(), client);
    }

    @Override
    public Client update(Client client) throws IncorrectClientException{
        validate(client);
        if (client.getId() == 0 || client.getFirstName() == null || client.getLastName() == null ||
                client.getGender() == null){
            throw new IncorrectClientException("User doesn't have mandatory field or has incorrect id " +
                    "for updating");
        }
        Client dbClient = getById(client);
        if (dbClient == null){
            return null;
        }
        client.setId(client.getId());
        clients.put(client.getId(), client);
        return client;
    }

    @Override
    public Client remove(Client client) throws IncorrectClientException{
        validate(client);
        if (client.getId() == 0){
            throw new IncorrectClientException("User doesn't have mandatory field 'id' " +
                    "for operation remove");
        }
        return clients.remove(client.getId());
    }

    public void setClients(Map<Long, Client> clients){
        this.clients = clients;
    }

    @Override
    public Map<Long, Client> getClients(){
        return clients;
    }
}
