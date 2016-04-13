package com.stepavlas.soapClient;

import com.stepavlas.restapp.Client;
import com.stepavlas.restapp.ClientEndpoint;
import com.stepavlas.restapp.ClientService;

import java.util.List;

/**
 * Created by Степан on 13.04.2016.
 */
public class Main {
    public static void main(String[] args) {
        ClientEndpoint service = new ClientService().getClientEndpointPort();

        List<Client> clients = service.getAll();
        for (Client client: clients) {
            System.out.println(client.getFirstName() + " " + client.getLastName());
        }
    }
}
