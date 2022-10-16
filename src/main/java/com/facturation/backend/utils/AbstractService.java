package com.facturation.backend.utils;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;

public abstract class AbstractService<T> {


    public abstract EntityManager getEntityManager();
    public abstract JpaRepository<T,Long> getRepository();

    public List<T> findAll(){
        return getRepository().findAll();
    }

    public Optional<T> findOneById(Long id) throws EntityNotFoundException{
        return getRepository().findById(id);
    }

    public void create(T entity) throws EntityExistsException{
        getRepository().saveAndFlush(entity);
    }

    public void update(Long id, T entity) {
        if(findOneById(id).get() != null){
            getEntityManager().merge(entity);
        }else{
            throw new EntityNotFoundException();
        }
    }
    public void delete(T entity){
        getRepository().delete(entity);
    }
}
