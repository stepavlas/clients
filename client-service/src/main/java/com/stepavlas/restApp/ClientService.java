package com.stepavlas.restApp;

import com.stepavals.restApp.Client;
import com.stepavals.restApp.exceptions.IncorrectClientException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Степан on 02.04.2016.
 */
public class ClientService {
    private ClientDao clientDao;

    public ClientService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public Client add(Client client){
        try {
            clientDao.add(client);
            List<Client> foundClients = clientDao.findClients(client);
            if (foundClients == null || foundClients.size() != 1){
                System.out.println("Unexpected error. More than one client was written in db" +
                        " or no clients were written in db");
                return null;
            }
            return foundClients.get(0);
        } catch (IncorrectClientException | IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Client> getAll(){
        Map<Long, Client> clientsMap = clientDao.getClients();
        List<Client> clients = new ArrayList<>(clientsMap.values());
        return clients;
    }

    public Client remove(Client client){
        try {
            List<Client> foundClients = clientDao.findClients(client);
            if (foundClients == null || foundClients.size() != 1){
                return null;
            }
            return clientDao.remove(foundClients.get(0));
        } catch (IncorrectClientException e) {
            e.printStackTrace();
            return null;
        }
    }
}
