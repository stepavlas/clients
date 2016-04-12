package com.stepavlas.restApp;

import com.stepavals.restApp.Client;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

/**
 * Created by Степан on 13.04.2016.
 */

@WebService(serviceName = "clientService")
public class ClientEndpoint {
    private ClientService service;

    @WebMethod(exclude = true)
    public void setService(ClientService service){
        this.service = service;
    }

    public List<Client> getAll(Client client) {
        return service.getAll(client);
    }

}
