package com.spring_db.service;

import com.spring_db.dao.GeneralDAO;
import com.spring_db.exceptions.DaoExceptions;
import com.spring_db.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public abstract class GeneralService<T> implements GenericService<T> {

    private GeneralDAO<T> dao;

    @Autowired
    public GeneralService(GeneralDAO<T> dao) {
        this.dao = dao;
    }

    @Transactional
    @Override
    public T findById(Long id) throws ServiceException {
        try {
            return dao.getById(id);
        }catch (DaoExceptions exceptions){
            throw new ServiceException(exceptions.getMessage());
        }

    }

    @Transactional
    @Override
    public void add(T t) throws ServiceException {
        try {
            dao.add(t);
        }catch (DaoExceptions exceptions){
            throw new ServiceException(exceptions.getMessage());
        }
    }

    @Transactional
    @Override
    public void update(T t) throws ServiceException {
        try {
            dao.update(t);
        }catch (DaoExceptions exceptions){
            throw new ServiceException(exceptions.getMessage());
        }
    }

    @Transactional
    @Override
    public void remove(T t) throws ServiceException {
        try {
            dao.remove(t);
        }catch (DaoExceptions exceptions){
            throw new ServiceException(exceptions.getMessage());
        }
    }

    @Transactional
    @Override
    public List<T> getAll() throws ServiceException {
        try {
            return dao.findAll();
        }catch (DaoExceptions exceptions){
            throw new ServiceException(exceptions.getMessage());
        }
    }
}
