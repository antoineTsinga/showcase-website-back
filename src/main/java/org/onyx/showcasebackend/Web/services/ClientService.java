package org.onyx.showcasebackend.Web.services;


import org.onyx.showcasebackend.dao.ClientRepository;
import org.onyx.showcasebackend.entities.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService extends UserService {
    @Autowired
    ClientRepository clientRepository;

    public List<Client> getClients(){
        return clientRepository.findAll();
    }

    public Client getClientById(Long id){
        return clientRepository.findById(id).isPresent()? clientRepository.findById(id).get() : null;
    }

    public void saveClient(Client client){
        clientRepository.save(client);
    }

    public void deleteClient(long id){
        clientRepository.deleteById(id);
    }
    public void updateClient(Client client, Long id){
        client.setId(id);
        clientRepository.save(client);
    }
}
