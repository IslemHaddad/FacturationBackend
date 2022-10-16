package com.facturation.backend.produit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/produit")
public class ProduitController {
    @Autowired
    private ProduitService produitService;
    
    @PreAuthorize("hasAuthority('PRODUIT_READ')")
    @GetMapping("")
    public List<Produit> showAll(){
        log.info("Fetching all Produits");
        return produitService.findAll();
    }

    @PreAuthorize("hasAuthority('PRODUIT_READ')")
    @GetMapping("{produit}")
    public Produit showOneByName(@PathVariable String produit){
        log.info("Fetching Produit with name : "+ produit +" if it exists!");
        return produitService.loadByProduitName(produit);
    }

    @PreAuthorize("hasAuthority('PRODUIT_READ')")
    @GetMapping("{type}")
    public List<Produit> ShowAllWhereClient(@PathVariable String type){
        log.info("Fetching Produits with type : "+ type +" if it exists!");
        return produitService.loadProduitByTypeAscOrder(type);
    }
 

    @PreAuthorize("hasAuthority('PRODUIT_WRITE')")
    @PostMapping("")
    public void createOne(@RequestBody Produit produit){
        try{
            log.info("Creating Produit [produit="+produit.getName()+", type="+produit.getType()+", quantity left="+produit.getQuantity()+"]");
            produitService.create(produit);
        }catch (Exception e){
            e.printStackTrace();
        }
    }    
    @PreAuthorize("HasAuthority('PRODUIT_UPDATE')")
    @PostMapping("{produitName}/{quantityBaught}")
    @ResponseBody
    public String buyProduit(@PathVariable String produitName,@PathVariable int quantityBaught){
        return "{\"sucess\":\""+produitService.buyQuantity(produitName, quantityBaught)+"\"}";
    }
    @PreAuthorize("HasAuthority('PRODUIT_UPDATE')")
    @PostMapping("{produitName}/{quantityAdded}")
    @ResponseBody
    public String refillStorage(@PathVariable String produitName,@PathVariable int quantityAdded){
        return "{\"sucess\":\""+produitService.fillStorage(produitName, quantityAdded)+"\"}";
    }
}
