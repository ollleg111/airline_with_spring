package com.spring_db.service;

import com.spring_db.exceptions.ServiceException;

import java.util.List;

public interface ServiceInterface<T> {

    T findById(Long id) throws ServiceException;

    void save(T t) throws ServiceException;

    void update(T t) throws ServiceException;

    void delete(T t) throws ServiceException;

    void deleteById(Long id) throws ServiceException;

    List<T> findAll() throws ServiceException;
}
