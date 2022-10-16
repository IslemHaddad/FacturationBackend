package com.facturation.backend.lignefacture;

import java.io.Serializable;
import java.util.List;


import com.facturation.backend.facture.Facture;
import com.facturation.backend.produit.Produit;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "ligne_facture")
@Entity
@Getter
@Setter
public class Lignefacture implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Facture facture;

    @ManyToMany
    @JoinTable(name = "ligne_facture_produit",inverseJoinColumns = @JoinColumn(referencedColumnName = "id", name = "produit_id"))
    private List<Produit> produits;

}
