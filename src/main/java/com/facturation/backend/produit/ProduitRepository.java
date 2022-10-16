package com.facturation.backend.produit;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long>{
    public Produit findByName(String produit);
    public List<Produit> findAllByType(String type);
    public List<Produit> findAllByTypeAndQuantityGreaterThan(String type, int quantityLeft);
}
