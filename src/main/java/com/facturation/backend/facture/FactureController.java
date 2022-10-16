package com.facturation.backend.facture;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facturation.backend.client.Client;
import com.facturation.backend.client.ClientService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/facture")
public class FactureController {
    @Autowired
    private FactureService FactureService;
    @Autowired
    private ClientService clientService;
    @PreAuthorize("hasAuthority('FACTURE_READ')")
    @GetMapping("")
    public List<Facture> showAll(){
        log.info("Fetching all Factures");
        return FactureService.findAll();
    }

    @PreAuthorize("hasAuthority('FACTURE_READ')")
    @GetMapping("{serial}")
    public Facture showOneBySerial(@PathVariable String serial){
        log.info("Fetching Facture with serial : "+ serial +" if it exists!");
        return FactureService.loadFactureBySerial(serial);
    }

    @PreAuthorize("hasAuthority('FACTURE_READ')")
    @GetMapping("{username}")
    public List<Facture> ShowAllWhereClient(@PathVariable String username){
        log.info("Fetching Factures for client : "+ username +" if it exists!");
        Client client = clientService.findByUsername(username);
        return FactureService.loadFacturesOfClient(client);
    }
 

    @PreAuthorize("hasAuthority('FACTURE_WRITE')")
    @PostMapping("")
    public void createOne(@RequestBody Facture Facture){
        try{
            log.info("Creating Facture [serial="+Facture.getSerial()+", client="+Facture.getClient().getUsername()+"]");
            FactureService.create(Facture);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
