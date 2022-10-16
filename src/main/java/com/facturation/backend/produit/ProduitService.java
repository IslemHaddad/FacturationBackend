package com.facturation.backend.produit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.facturation.backend.utils.AbstractService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;


@Service
public class ProduitService extends AbstractService<Produit>{

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ProduitRepository produitRepository;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public JpaRepository<Produit, Long> getRepository() {
        return produitRepository;
    }
    
    public Produit loadByProduitName(String produit){
        return  produitRepository.findByName(produit);
    }

    public List<Produit> loadProduitByTypeAscOrder(String type){
        return produitRepository.findAllByType(type);
    }

    public List<Produit> loadProduitByTypeAndQuantityGreater(String type, int quantityLeft){
        return  produitRepository.findAllByTypeAndQuantityGreaterThan(type, quantityLeft);
    }

    @Transactional
    public Produit buyQuantity(String produitName,int quantityBaught){
        Produit produit = produitRepository.findByName(produitName);
        if(quantityBaught <= produit.getQuantity()){
            produit.setQuantity(produit.getQuantity() - quantityBaught);
            update(produit.getId(), produit);
            return produit;
        }
        return null;
    }

    public String fillStorage(String produitName,int quantityAdded){
        Produit produit = produitRepository.findByName(produitName);
        produit.setQuantity(produit.getQuantity() + quantityAdded);
        update(produit.getId(), produit);
        return "Quantity Filled!";
    }
}
