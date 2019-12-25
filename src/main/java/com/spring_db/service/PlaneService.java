package com.spring_db.service;

import com.spring_db.dao.GeneralDAO;
import com.spring_db.entity.Plane;
import com.spring_db.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaneService extends GeneralService<Plane>{

    @Autowired
    public PlaneService(GeneralDAO<Plane> dao) {
        super(dao);
    }

    @Override
    public Plane findById(Long id) throws ServiceException {
        return super.findById(id);
    }

    @Override
    public void add(Plane plane) throws ServiceException {
        super.add(plane);
    }

    @Override
    public void update(Plane plane) throws ServiceException {
        super.update(plane);
    }

    @Override
    public void remove(Plane plane) throws ServiceException {
        super.remove(plane);
    }

    @Override
    public List<Plane> getAll() throws ServiceException {
        return super.getAll();
    }
}
