package com.facturation.backend.facture;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facturation.backend.client.Client;


@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {
    public Facture findBySerial(String serial);
    public List<Facture> findByClient(Client client);
}
