package com.facturation.backend.facture;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facturation.backend.client.Client;
import com.facturation.backend.utils.AbstractServiceNoUpdateNoDelete;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class FactureService extends AbstractServiceNoUpdateNoDelete<Facture>{

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private FactureRepository factureRepository;


    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public FactureRepository getRepository() {
        return factureRepository;
    }

    public Facture loadFactureBySerial(String serial){
        return factureRepository.findBySerial(serial);
    }

    public List<Facture> loadFacturesOfClient(Client client){
        return factureRepository.findByClient(client);
    }
    
}
