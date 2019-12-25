package com.spring_db.service;

import com.spring_db.dao.GeneralDAO;
import com.spring_db.entity.Plane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaneService extends GeneralService<Plane>{

    @Autowired
    public PlaneService(GeneralDAO<Plane> dao) {
        super(dao);
    }
}
