package com.spring_db.service;

import com.spring_db.dao.GeneralDAO;
import com.spring_db.dao.PlaneDAO;
import com.spring_db.entity.Plane;
import com.spring_db.exceptions.BadRequestException;
import com.spring_db.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaneService extends GeneralService<Plane> {

    private PlaneDAO planeDAO;

    @Autowired
    public PlaneService(GeneralDAO<Plane> dao, PlaneDAO planeDAO) {
        super(dao);
        this.planeDAO = planeDAO;
    }

    @Override
    public Plane findById(Long id) throws ServiceException {
        Plane plane = planeDAO.getOne(id);
        planeNullValidator(plane);
        return super.findById(id);
    }

    @Override
    public void save(Plane plane) throws ServiceException {
        super.save(plane);
    }

    @Override
    public void update(Plane plane) throws ServiceException {
        super.update(plane);
    }

    @Override
    public void delete(Plane plane) throws ServiceException {
        super.delete(plane);
    }

    public void deleteById(Long id) throws ServiceException {
        Plane plane = planeDAO.getOne(id);
        planeNullValidator(plane);
        super.delete(plane);
    }

    @Override
    public List<Plane> findAll() throws ServiceException {
        return super.findAll();
    }

    private void planeNullValidator(Plane plane) throws RuntimeException {
        if (plane == null) throw new BadRequestException("Plane does not exist in method" +
                " planeNullValidator(Plane plane) from class " +
                PlaneService.class.getName());
    }
}
