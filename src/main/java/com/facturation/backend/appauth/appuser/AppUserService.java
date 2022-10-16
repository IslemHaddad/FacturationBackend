package com.facturation.backend.appauth.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.facturation.backend.utils.AbstractService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@Service
public class AppUserService extends AbstractService<AppUser> implements UserDetailsService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public AppUser loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username);
        return appUser;
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public AppUserRepository getRepository() {
        return appUserRepository;
    }
    
}
