package com.facturation.backend.facture;

import java.io.Serializable;
import java.util.List;

import com.facturation.backend.client.Client;
import com.facturation.backend.lignefacture.Lignefacture;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "facture")
@Entity
@Getter
@Setter
public class Facture implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToOne
    private Client client;

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long serial;

    @OneToMany(mappedBy = "facture",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Lignefacture> lignefactures;

    
    
}
