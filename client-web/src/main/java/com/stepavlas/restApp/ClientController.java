package com.stepavlas.restApp;

import com.stepavals.restApp.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Stepan on 02.04.2016.
 */

@Controller
@RequestMapping(path = "/")
public class ClientController {
    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @RequestMapping(value = "/clients", method = GET)
    public ModelAndView getClient(){
        return new ModelAndView("clientsMainPage");
    }

    @RequestMapping(value = "/add", method = POST)
    public ModelAndView addClient(@RequestParam Map<String, String> params){
        String firstName = params.get("firstName");
        String lastName = params.get("lastName");
        String strGender = params.get("gender");
        Character gender = 0;
        if (strGender.length() == 1) {
            gender = strGender.charAt(0);
        }
        String country = params.get("country");
        String city = params.get("city");

        Client client = new Client(firstName, lastName, gender, city, country);
        Client dbClient = clientService.add(client);
        Map<String, String> model = new HashMap<>();
        String message;
        if (dbClient == null){
            message = "Incorrect input. Couldn't save client";
        } else {
            message = "Client was registered with id " + dbClient.getId();
        }
        model.put("msg", message);
        return new ModelAndView("clientsMainPage", model);
    }

    @RequestMapping(value = "/remove", method = POST)
    public ModelAndView removeClient(@RequestParam("remClientId") Long id,
                                     @RequestParam("remFirstName") String firstName,
                                     @RequestParam("remLastName") String lastName) {
        Client client = new Client();
        Map<String, String> model = new HashMap<>();
        if (id != null && id > 0){
            client.setId(id);
        } else if ((firstName != null && !"".equals(firstName))
                && (lastName != null && !"".equals(lastName))) {
            client.setFirstName(firstName);
            client.setLastName(lastName);
        } else {
            String message = "Not all mandatory fields were entered for removing client";
            model.put("msg", message);
            return new ModelAndView("clientsMainPage", model);
        }
        Client removedClient = clientService.remove(client);
        String message;
        if (removedClient == null){
            message = "No such user in db or more than one client were found. Couldn't remove client";
        } else {
            message = "Client with id " + removedClient.getId() + " was removed";
        }
        model.put("msg", message);
        return new ModelAndView("clientsMainPage", model);
    }
}
