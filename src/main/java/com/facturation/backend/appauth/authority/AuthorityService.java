package com.facturation.backend.appauth.authority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facturation.backend.utils.AbstractService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class AuthorityService extends AbstractService<Authority>{

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public AuthorityRepository getRepository() {
        return authorityRepository;
    }

    public Authority loadBAuthority(String authority){
        return authorityRepository.findByAuthority(authority);
    }

}
