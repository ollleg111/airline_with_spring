package com.spring_db.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralDAO<T> extends JpaRepository<T, Long> {
}
