package com.spring_db.service;

import com.spring_db.dao.PlaneDAO;
import com.spring_db.entity.Plane;
import com.spring_db.exceptions.BadRequestException;
import com.spring_db.exceptions.DaoException;
import com.spring_db.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaneService {

    private PlaneDAO planeDAO;

    @Autowired
    public void setPlaneDAO(PlaneDAO planeDAO) {
        this.planeDAO = planeDAO;
    }

    private String className = PlaneService.class.getName();

    public Plane findById(Long id) throws DaoException {
        Plane plane = planeDAO.findById(id);
        planeNullValidator(plane);
        return planeDAO.findById(id);
    }

    public Plane save(Plane plane) throws DaoException {
        return planeDAO.save(plane);
    }

    public Plane update(Plane plane) throws DaoException {
        return planeDAO.update(plane);
    }

    public void delete(Plane plane) throws DaoException {
        planeDAO.delete(plane);
    }

    public void deleteById(Long id) throws DaoException {
        Plane plane = planeDAO.findById(id);
        planeNullValidator(plane);
        planeDAO.delete(plane);
    }

    public List<Plane> findAll() throws DaoException {
        return planeDAO.findAll();
    }

    public List<Plane> oldPlanes() throws DaoException {
        return planeDAO.oldPlanes();
    }

    public List<Plane> regularPlanes(int year) throws DaoException {
        yearValidator(year);
        return planeDAO.regularPlanes(year);
    }

    private void yearValidator(int year) throws ServiceException {
        if (year <= 0) throw new BadRequestException(" You entered wrong data in field: Year ! This is test " +
                " from method yearValidator(int year) in the method " + className);
    }

    private void planeNullValidator(Plane plane) throws ServiceException {
        if (plane == null) throw new BadRequestException("Plane does not exist in method" +
                " planeNullValidator(Plane plane) from class " +
                className);
    }
}
