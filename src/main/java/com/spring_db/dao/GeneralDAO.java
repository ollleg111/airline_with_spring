package com.spring_db.dao;

import com.spring_db.exceptions.DaoExceptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeneralDAO<T> extends JpaRepository {

    T getById(Long id) throws DaoExceptions;

    void add(T t) throws DaoExceptions;

    void update(T t) throws DaoExceptions;

    void remove(T t) throws DaoExceptions;

    List<T> findAll() throws DaoExceptions;
}
