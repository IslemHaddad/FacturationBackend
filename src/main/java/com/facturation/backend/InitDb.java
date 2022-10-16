package com.facturation.backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.transaction.Transactional;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.facturation.backend.appauth.appuser.AppUser;
import com.facturation.backend.appauth.appuser.AppUserService;
import com.facturation.backend.appauth.authority.Authority;
import com.facturation.backend.appauth.authority.AuthorityService;

@Component
public class InitDb implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(InitDb.class);

    private final PasswordEncoder passwordEncoder;
    
    private AppUserService userService;

    private AuthorityService authorityService;



    @Autowired
    public InitDb(AppUserService userService, AuthorityService authorityService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authorityService = authorityService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        List<Authority> authorities = new ArrayList<>();
                // Create CRUD Permissions for all Entities
        Reflections reflections = new Reflections("com.facturation.backend");
        Set<Class<?>> entities = reflections.getTypesAnnotatedWith(Entity.class);
        for(Class<?> entity : entities){
            logger.info("Preloading  "+ entity.getSimpleName() + "_READ authority");
            Authority read = createAuthorityIfNotExist(entity,"READ");
            logger.info("Preloading  "+ entity.getSimpleName() + "_WRITE authority");
            Authority write = createAuthorityIfNotExist(entity, "WRITE");
            logger.info("Preloading  "+ entity.getSimpleName() + "_UPDATE authority");
            Authority update = createAuthorityIfNotExist(entity, "UPDATE");
            logger.info("Preloading  "+ entity.getSimpleName() + "_DELETE authority");
            Authority delete = createAuthorityIfNotExist(entity, "DELETE");
            authorities.add(read);
            authorities.add(write);
            authorities.add(update);
            authorities.add(delete);

                                 
        }
        createUserIfNotExist("Islem", "HADDAD", "Islem", "Islem123", authorities);
        createUserIfNotExist("Abdou", "HADDAD", "Abdou", "Adbou123",null);
        }

    @Transactional
    public AppUser createUserIfNotExist(String firstname, String lastname, String username, String password, Collection<Authority> authorities){
        AppUser user = userService.loadUserByUsername(username);
        if (user == null ){
            user =  AppUser.builder()
            .username(username)
            .authorities(authorities)
            .accountNonExpired(true)
            .accountNonLocked(true)
            .credentialsNonExpired(true)
            .enabled(true)
            .password(passwordEncoder.encode(password))
            .build();
            userService.create(user);
            return user;
        }
        return user;
    }

    @Transactional
    public Authority createAuthorityIfNotExist(Class<?> entityClass,String authorityName){
        Authority authority = authorityService.loadBAuthority(authorityName);
        if (authority == null){
            authority = new Authority();
            authority.setAuthority(entityClass.getSimpleName().toUpperCase() +"_"+ authorityName);
            authorityService.create(authority);
            return authority;
        }
        return authority;
    }



    
}