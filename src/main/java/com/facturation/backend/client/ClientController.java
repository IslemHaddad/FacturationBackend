package com.facturation.backend.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facturation.backend.facture.Facture;
import com.facturation.backend.facture.FactureService;
import com.facturation.backend.lignefacture.Lignefacture;
import com.facturation.backend.produit.Produit;
import com.facturation.backend.produit.ProduitService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/client")
public class ClientController {
    
    @Autowired
    private ClientService clientService;
    
    @Autowired
    private FactureService factureService;

    @Autowired
    private ProduitService produitService;

    
    @PreAuthorize("hasAuthority('CLIENT_READ')")
    @GetMapping("")
    public List<Client> showAll(){
        log.info("Fetching all Clients");
        return clientService.findAll();
    }

    @PreAuthorize("hasAuthority('CLIENT_READ')")
    @GetMapping("{username}")
    public ResponseEntity<Client> showOneById(@PathVariable String username){
        log.info("Fetching client with username : "+ username +" if it exists!");
        Client client = clientService.findByUsername(username);
        return ResponseEntity.ok(client);
    }

    @PreAuthorize("hasAuthority('CLIENT_WRITE')")
    @PostMapping("")
    public void createOne(@RequestBody Client client){
        try{
            log.info("Creating client [username="+client.getUsername()+", firstname="+client.getFirstname()+", lastname="+client.getLastname()+"]");
            clientService.create(client);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @PreAuthorize("hasAuthority('CLIENT_UPDATE')")
    @PutMapping("{username}")
    public void updateOne(@PathVariable String username, @RequestBody Client client){
        log.info("Updating client with username: "+username+" if it exists!, with the new info [username="+client.getUsername()+", firstname="+client.getFirstname()+", lastname="+client.getLastname()+"]");
        clientService.update(username, client);
    }
    @PreAuthorize("hasAuthority('CLIENT_DELETE')")
    @DeleteMapping("{username}")
    public void deleteOne(@PathVariable String username){
        log.info("Deleting Client= "+username);
        clientService.delete(username);
    }

    @PostMapping("facturation/{produit}/{quantity}")
    public ResponseEntity<Client> facturation(@PathVariable String produit ,@PathVariable int quantity,@RequestBody Client client){
        List<Produit> produits = new ArrayList<>();
        Produit produitObj = produitService.buyQuantity(produit, quantity);
        produits.add(produitObj);

        Lignefacture lignefacture = new Lignefacture();
        lignefacture.setProduits(produits);

        List<Lignefacture> lignefactures = new ArrayList<>();
        lignefactures.add(lignefacture);

        Facture facture = new Facture();
        lignefacture.setFacture(facture);

        facture.setLignefactures(lignefactures);
        facture.setClient(client);
        List<Facture> factures = new ArrayList<>();
        factures.add(facture);
        client.setFactures(factures);
        clientService.create(client);
        factureService.create(facture);
        return ResponseEntity.ok(client);
    }
}
