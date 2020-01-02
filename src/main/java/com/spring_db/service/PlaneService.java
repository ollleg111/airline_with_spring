package com.spring_db.service;

import com.spring_db.dao.GeneralDAO;
import com.spring_db.dao.PlaneDAO;
import com.spring_db.entity.Plane;
import com.spring_db.exceptions.BadRequestException;
import com.spring_db.exceptions.DaoException;
import com.spring_db.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Service
public class PlaneService extends GeneralService<Plane> {

    private PlaneDAO planeDAO;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    public PlaneService(GeneralDAO<Plane> dao, PlaneDAO planeDAO) {
        super(dao);
        this.planeDAO = planeDAO;
    }

    private static final String OLD_PLANES_REQUEST = "";
    private static final String REGULAR_PLANES_REQUEST = "";

    @Override
    public Plane findById(Long id) throws ServiceException {
        Plane plane = planeDAO.getOne(id);
        planeNullValidator(plane);
        return super.findById(id);
    }

    @Override
    public Plane save(Plane plane) throws ServiceException {
        return super.save(plane);
    }

    @Override
    public Plane update(Plane plane) throws ServiceException {
        return super.update(plane);
    }

    @Override
    public void delete(Plane plane) throws ServiceException {
        super.delete(plane);
    }

    @Transactional
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

    /*
    oldPlanes() - самолеты старше 20 лет
     */
    @Transactional
    public List<Plane> oldPlanes() throws ServiceException {
        try {
            Query query = entityManager.createNativeQuery(OLD_PLANES_REQUEST, Plane.class);
            return query.getResultList();
        } catch (DaoException exception) {
            System.err.println(exception.getMessage());
            throw new ServiceException("Operation with planes was filed in method" +
                    " oldPlane() from class " + PlaneService.class.getName());
        }
    }

    /*
    regularPlanes(int year) - самолеты, которые с больше 300 полетов за год
     */
    @Transactional
    public List<Plane> regularPlanes(int year) throws ServiceException {
        yearValidator(year);
        try {
            Query query = entityManager.createNativeQuery(REGULAR_PLANES_REQUEST, Plane.class);
            query.setParameter("year", year);
            return query.getResultList();
        } catch (DaoException exception) {
            System.err.println(exception.getMessage());
            throw new ServiceException("Operation with planes was filed in method" +
                    " regularPlanes() from class " + PlaneService.class.getName());
        }
    }

    private void yearValidator(int year) throws BadRequestException {
        if (year <= 0) throw new BadRequestException(" You entered wrong data in field: Year ! This is test " +
                " from method yearValidator(int year) in the method " + PlaneService.class.getName());
    }
}
