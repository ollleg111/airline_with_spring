package com.spring_db.service;

import com.spring_db.dao.GeneralDAO;
import com.spring_db.exceptions.DaoException;
import com.spring_db.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public abstract class GeneralService<T> implements ServiceInterface<T> {

    private GeneralDAO<T> dao;
    private Class<T> typeParameterClass;

    @Autowired
    public GeneralService(GeneralDAO<T> dao) {
        this.dao = dao;
    }

    @Autowired
    public void setTypeParameterClass(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    @Transactional
    @Override
    public T findById(Long id) throws ServiceException {
        try {
            return dao.getOne(id);
        }catch (DaoException exception){
            System.err.println("findById is failed");
            System.err.println(exception.getMessage());
            throw new ServiceException(" The method findById(Long id) was failed in class "
                    + typeParameterClass.getName());
        }
    }

    @Transactional
    @Override
    public void save(T t) throws ServiceException {
        try {
            dao.save(t);
        }catch (DaoException exception){
            System.err.println("save is failed");
            System.err.println(exception.getMessage());
            throw new ServiceException(" The method save(T t) was failed in class "
                    + typeParameterClass.getName());
        }
    }

    @Transactional
    @Override
    public void update(T t) throws ServiceException {
        try {
            dao.save(t);
        }catch (DaoException exception){
            System.err.println("update is failed");
            System.err.println(exception.getMessage());
            throw new ServiceException(" The method update(T t) was failed in class "
                    + typeParameterClass.getName());
        }
    }

    @Transactional
    @Override
    public void delete(T t) throws ServiceException {
        try {
            dao.delete(t);
        }catch (DaoException exception){
            System.err.println("delete is failed");
            System.err.println(exception.getMessage());
            throw new ServiceException(" The method delete(T t) was failed in class "
                    + typeParameterClass.getName());
        }
    }

    @Transactional
    @Override
    public List<T> findAll() throws ServiceException {
        try {
            return dao.findAll();
        }catch (DaoException exception){
            System.err.println("findAll is failed");
            System.err.println(exception.getMessage());
            throw new ServiceException(" The method findAll() was failed in class "
                    + typeParameterClass.getName());
        }
    }
}
