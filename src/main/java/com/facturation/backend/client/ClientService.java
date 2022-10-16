package com.facturation.backend.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.facturation.backend.utils.AbstractService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;

@Service
public class ClientService extends AbstractService<Client> {
    
    @PersistenceContext(name = "punit")
    private EntityManager em;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public JpaRepository<Client, Long> getRepository() {
        return clientRepository;
    }

    public Client findByUsername(String username){
        return clientRepository.findByUsername(username);
    }
    public void update(String username, Client entity) {
        if(findByUsername(username) != null){
            Client client = clientRepository.findByUsername(username);
            client.setFirstname(entity.getFirstname());
            client.setLastname(entity.getLastname());
            clientRepository.save(client);
        }else{
            throw new EntityNotFoundException();
        }
    }
    
    public void delete(String username){
        Client client = clientRepository.findByUsername(username);
        clientRepository.delete(client);
    }

}
