package com.stepavlas.restApp.impl;

import com.stepavals.restApp.Client;
import com.stepavals.restApp.exceptions.IncorrectClientException;

import com.stepavlas.restApp.ClientDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Степан on 02.04.2016.
 */

public class ClientsDaoTest {
    private static ClientDaoImpl clientDao = new ClientDaoImpl();

    @Before
    public void init(){
        Map<Long, Client> clients = new HashMap<>();
        Client client1 = new Client(1, "Sergei", "Shnaps", 'M', "Moscow", "Russia");
        clients.put(client1.getId(), client1);
        Client client2 = new Client(2, "Pavel", "Velocipedov", 'M', "Saint-Petersburg", "Russia");
        clients.put(client2.getId(), client2);
        Client client3 = new Client(3, "Anna", "Somina", 'F', "Los Angeles", "USA");
        clients.put(client3.getId(), client3);
        Client client4 = new Client(4, "Veronica", "Uliashova", 'F', null, null);
        clients.put(client4.getId(), client4);
        clientDao.setClients(clients);
    }

    @Test
    public void findUserByIdTest() throws IncorrectClientException{
        Client client1 = new Client();
        client1.setId(2);
        List<Client> clients = clientDao.findClients(client1);
        Client client2 = new Client(2, "Pavel", "Velocipedov", 'M', "Saint-Petersburg", "Russia");
        Assert.assertEquals(1, clients.size());
        client1 = clients.get(0);
        Assert.assertTrue(clientsAreEqual(client1, client2));
    }

    @Test
    public void findClientByNullTest() throws IncorrectClientException {
        try {
            clientDao.findClients(null);
            Assert.fail();
        } catch (IllegalArgumentException iae) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void getUserByNullTest() throws IncorrectClientException {
        try {
            clientDao.getById(null);
            Assert.fail();
        } catch (IllegalArgumentException iae) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void getUserByIdZeroTest() throws IncorrectClientException {
        Client client = new Client();
        client.setId(0);
        try {
            clientDao.getById(client);
            Assert.fail();
        } catch (IncorrectClientException ice) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void findUserByIdZeroTest() throws IncorrectClientException {
        Client client = new Client();
        client.setId(0);
        List<Client> clients = clientDao.findClients(client);
        Assert.assertTrue(clients.isEmpty());
    }

    @Test
    public void findClientByFirstNameTest() throws IncorrectClientException {
        Client client1 = new Client();
        client1.setFirstName("Pavel");
        List<Client> clients = clientDao.findClients(client1);
        Client client2 = new Client(2, "Pavel", "Velocipedov", 'M', "Saint-Petersburg", "Russia");
        Assert.assertEquals(1, clients.size());
        client1 = clients.get(0);
        Assert.assertTrue(clientsAreEqual(client1, client2));
    }

    @Test
    public void findClientByLastNameTest() throws IncorrectClientException {
        Client client1 = new Client();
        client1.setLastName("Somina");
        List<Client> clients = clientDao.findClients(client1);
        Client client2 = new Client(3, "Anna", "Somina", 'F', "Los Angeles", "USA");
        Assert.assertEquals(1, clients.size());
        client1 = clients.get(0);
        Assert.assertTrue(clientsAreEqual(client1, client2));
    }

    @Test
    public void findSeveralClientsByNameTest() throws IncorrectClientException {
        Client client1 = new Client();
        client1.setFirstName("Sergei");
        client1.setLastName("Velocipedov");
        Client client2 = new Client(1, "Sergei", "Shnaps", 'M', "Moscow", "Russia");
        Client client3 = new Client(2, "Pavel", "Velocipedov", 'M', "Saint-Petersburg", "Russia");
        // shall return all users with such name and all users with such lastName
        List<Client> clients = clientDao.findClients(client1);
        Assert.assertEquals(2, clients.size());
        Client foundClient2 = clients.get(0);
        Assert.assertTrue(clientsAreEqual(foundClient2, client2));
        Client foundClient3 = clients.get(1);
        Assert.assertTrue(clientsAreEqual(foundClient3, client3));
    }

    @Test
    public void findByGenderTest() throws IncorrectClientException {
        Client client1 = new Client();
        client1.setGender('F');
        Client client2 = new Client(3, "Anna", "Somina", 'F', "Los Angeles", "USA");
        Client client3 = new Client(4, "Veronica", "Uliashova", 'F', null, null);
        List<Client> clients = clientDao.findClients(client1);
        Assert.assertEquals(2, clients.size());
        Client foundClient2 = clients.get(0);
        Assert.assertTrue(clientsAreEqual(foundClient2, client2));
        Client foundClient3 = clients.get(1);
        Assert.assertTrue(clientsAreEqual(foundClient3, client3));
    }

    @Test
    public void findByIncorrectGenderTest() throws IncorrectClientException {
        Client client = new Client();
        client.setGender('V');
        try {
            clientDao.findClients(client);
            Assert.fail();
        } catch (IncorrectClientException ice){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void findByCityTest() throws IncorrectClientException {
        Client client1 = new Client();
        client1.setCity("Saint-Petersburg");
        List<Client> clients = clientDao.findClients(client1);
        Client client2 = new Client(2, "Pavel", "Velocipedov", 'M', "Saint-Petersburg", "Russia");
        Assert.assertEquals(1, clients.size());
        client1 = clients.get(0);
        Assert.assertTrue(clientsAreEqual(client1, client2));
    }

    @Test
    public void findByIncorrectCityTest() throws IncorrectClientException {
        Client client1 = new Client();
        client1.setCity("Incorrect city");
        List<Client> clients = clientDao.findClients(client1);
        Assert.assertTrue(clients.isEmpty());
    }

    @Test
    public void findByCountryTest() throws IncorrectClientException {
        Client client1 = new Client();
        client1.setCountry("Russia");
        List<Client> clients = clientDao.findClients(client1);
        Client client2 = new Client(1, "Sergei", "Shnaps", 'M', "Moscow", "Russia");
        Client client3 = new Client(2, "Pavel", "Velocipedov", 'M', "Saint-Petersburg", "Russia");
        Assert.assertEquals(2, clients.size());
        Client foundClient2 = clients.get(0);
        Client foundClient3 = clients.get(1);
        Assert.assertTrue(clientsAreEqual(foundClient2, client2));
        Assert.assertTrue(clientsAreEqual(foundClient3, client3));
    }

    @Test
    public void addClientTest(){
        int oldSize = clientDao.getClients().size();
        Client client = new Client("Ira", "Antonitova", 'F', "Saint-Petersburg", "Russia");
        try {
            clientDao.add(client);
        } catch (IncorrectClientException e) {
            Assert.fail("add method threw IncorrectClientException with message:" + e.getMessage());
        }
        Map<Long, Client> clients = clientDao.getClients();
        Assert.assertEquals(oldSize + 1, clients.size());
        Client dbClient = clients.get((long)5);
        Assert.assertTrue(clientsAreEqual(client, dbClient));
    }

    @Test
    public void addNullTest() throws IncorrectClientException {
        int oldSize = clientDao.getClients().size();
        try {
            clientDao.add(null);
            Assert.fail();
        } catch (IllegalArgumentException iae) {
            Map<Long, Client> newClients = clientDao.getClients();
            Assert.assertEquals(oldSize, newClients.size());
        }
    }

    @Test
    public void addClientWithoutMandatoryField(){
        int oldSize = clientDao.getClients().size();
        Client client = new Client();
        client.setFirstName("Ira");
        client.setLastName("Antonitova");
        client.setCity("Saint-Petersburg");
        client.setCountry("Russia");
        try{
            clientDao.add(client);
            Assert.fail();
        } catch (IncorrectClientException ice) {
            Map<Long, Client> newClients = clientDao.getClients();
            Assert.assertEquals(oldSize, newClients.size());
        }
    }

    @Test
    public void addClientWithoutOptionalField(){
        int oldSize = clientDao.getClients().size();
        Client client = new Client();
        client.setFirstName("Ira");
        client.setLastName("Antonitova");
        client.setGender('F');

        try {
            clientDao.add(client);
        } catch (IncorrectClientException e) {
            Assert.fail("add method threw IncorrectClientException with message:" + e.getMessage());
        }

        Map<Long, Client> newClients = clientDao.getClients();
        Assert.assertEquals(oldSize + 1, newClients.size());
        Client dbClient = newClients.get((long)5);
        Assert.assertTrue(clientsAreEqual(client, dbClient));
    }

    @Test
    public void updateClientTest(){
        Map<Long, Client> oldClients = clientDao.getClients();
        Client oldClient = oldClients.get((long)2);
        Client client = new Client(2, "Sergei", "Klimov", 'M', "New York", "USA");
        try {
            clientDao.update(client);
        } catch (IncorrectClientException e) {
            Assert.fail("add method threw IncorrectClientException with message:" + e.getMessage());
        }
        Map<Long, Client> newClients = clientDao.getClients();
        Client newClient = newClients.get((long)2);
        Assert.assertEquals(oldClients.size(), newClients.size());
        Assert.assertFalse(clientsAreEqual(oldClient, newClient));
        Assert.assertTrue(clientsAreEqual(newClient, client));
    }

    @Test
    public void updateClientWithoutMandatoryFieldTest(){
        int oldSize = clientDao.getClients().size();
        Client client = new Client();
        client.setId(2);
        client.setFirstName("Sergei");
        client.setGender('M');
        client.setCity("New York");
        client.setCountry("USA");
        try {
            clientDao.update(client);
            Assert.fail();
        } catch (IncorrectClientException e) {
            Map<Long, Client> newClients = clientDao.getClients();
            Assert.assertEquals(oldSize, newClients.size());
        }
    }

    @Test
    public void updateClientWithoutIdTest(){
        int oldSize = clientDao.getClients().size();
        Client client = new Client("Sergei", "Klimov", 'M', "New York", "USA");
        try {
            clientDao.update(client);
            Assert.fail();
        } catch (IncorrectClientException e) {
            Map<Long, Client> newClients = clientDao.getClients();
            Assert.assertEquals(oldSize, newClients.size());
        }
    }

    @Test
    public void updateWithoutOptionalFieldTest(){
        Map<Long, Client> oldClients = clientDao.getClients();
        Client client = new Client(2, "Sergei", "Klimov", 'M', null, "USA");
        Client oldClient = oldClients.get((long)2);
        try {
            clientDao.update(client);
        } catch (IncorrectClientException e) {
            Assert.fail("add method threw IncorrectClientException with message:" + e.getMessage());
        }
        Map<Long, Client> newClients = clientDao.getClients();
        Assert.assertEquals(oldClients.size(), newClients.size());
        Client newClient = newClients.get((long)2);
        Assert.assertFalse(clientsAreEqual(oldClient, newClient));
        Assert.assertTrue(clientsAreEqual(newClient, client));
    }

    @Test
    public void updateNullTest() throws IncorrectClientException {
        int oldSize = clientDao.getClients().size();
        try {
            clientDao.update(null);
            Assert.fail();
        } catch (IllegalArgumentException iae) {
            Map<Long, Client> newClients = clientDao.getClients();
            Assert.assertEquals(oldSize, newClients.size());
        }
    }

    @Test
    public void removeClientTest() {
        int oldSize = clientDao.getClients().size();
        Client client = new Client();
        client.setId(3);

        try {
            clientDao.remove(client);
        } catch (IncorrectClientException e) {
            Assert.fail("remove method threw IncorrectUserException with message:" + e.getMessage());
        }

        Map<Long, Client> newCliens = clientDao.getClients();
        int newSize = newCliens.size();
        Assert.assertEquals(oldSize - 1, newSize);
        Assert.assertFalse(newCliens.containsKey((long) 3));
    }

    @Test
    public void removeClientWithoutFields() {
        int oldSize = clientDao.getClients().size();
        Client client = new Client();
        try {
            clientDao.remove(client);
        } catch (IncorrectClientException e) {
            Map<Long, Client> newCliens = clientDao.getClients();
            int newSize = newCliens.size();
            Assert.assertEquals(oldSize, newSize);
        }
    }

    @Test
    public void removeNullTest() throws IncorrectClientException {
        int oldSize = clientDao.getClients().size();
        try {
            clientDao.remove(null);
            Assert.fail();
        } catch (IllegalArgumentException iae) {
            Map<Long, Client> newClients = clientDao.getClients();
            Assert.assertEquals(oldSize, newClients.size());
        }
    }

    private boolean clientsAreEqual(Client client1, Client client2){
        if (client1.getId() != client2.getId()){
            return false;
        }
        if (!(strsAreEqual(client1.getFirstName(), client2.getFirstName()))){
            return false;
        }
        if (!(strsAreEqual(client1.getLastName(), client2.getLastName()))){
            return false;
        }
        if (!(strsAreEqual(String.valueOf(client1.getGender()), String.valueOf(client2.getGender())))){
            return false;
        }
        if (!(strsAreEqual(client1.getCity(), client2.getCity()))){
            return false;
        }
        if (!(strsAreEqual(client1.getCountry(), client2.getCountry()))){
            return false;
        }
        return true;
    }

    private boolean strsAreEqual(String str1, String str2){
        return (str1 == null ? str2 == null : str1.equals(str2));
    }
}
