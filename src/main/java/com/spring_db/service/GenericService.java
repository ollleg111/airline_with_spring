package com.spring_db.service;

import com.spring_db.exceptions.ServiceException;

import java.util.List;

public interface GenericService<T> {

    T findById(Long id) throws ServiceException;

    void add(T t) throws ServiceException;

    void update(T t) throws ServiceException;

    void remove(T t) throws ServiceException;

    List<T> findAll() throws ServiceException;
}
