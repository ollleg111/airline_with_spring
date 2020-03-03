package com.spring_db.dao;

import com.spring_db.entity.Plane;
import com.spring_db.exceptions.DaoException;
import com.spring_db.service.PlaneService;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class PlaneDAO extends GeneralDAO<Plane> {
    @PersistenceContext
    private EntityManager entityManager;

    public PlaneDAO() {
        setEntityManager(entityManager);
        setTypeParameterClass(Plane.class);
    }

    private String className = PlaneDAO.class.getName();

    private static final String OLD_PLANES_REQUEST =
            "SELECT " +
                    "p.id, " +
                    "p.model, " +
                    "p.code, " +
                    "p.year_produced, " +
                    "p.avg_fuel_consumption " +
                    "FROM PLANE p " +
                    "WHERE TO_NUMBER(TO_CHAR(SYSDATE,'YYYY') - TO_CHAR(YEAR_PRODUCED,'YYYY')) > 20";

    /*
     private static final String OLD_PLANES_REQUEST =
            "SELECT * FROM PLANE " +
                    "WHERE TO_NUMBER(TO_CHAR(EXTRACT(YEAR FROM SYSDATE)) - TO_CHAR(YEAR_PRODUCED,'YYYY')) > 20";
     */

    private static final String REGULAR_PLANES_REQUEST =
            "SELECT" +
                    "p.id, p.model, p.code, p.year_produced, p.avg_fuel_consumption " +
                    "FROM plane p, flight f " +
                    "WHERE " +
                    "f.plane = p.id AND" +
                    "TO_NUMBER(TO_CHAR(f.date_flight,'YYYY')) = ? GROUP BY" +
                    "p.id, p.model, p.code, p.year_produced, p.avg_fuel_consumption HAVING COUNT(f.id) > 300";

    @Override
    public Plane findById(Long id) throws DaoException {
        return super.findById(id);
    }

    @Override
    public Plane save(Plane plane) throws DaoException {
        return super.save(plane);
    }

    @Override
    public Plane update(Plane plane) throws DaoException {
        return super.update(plane);
    }

    @Override
    public void delete(Plane plane) throws DaoException {
        super.delete(plane);
    }

    @Override
    public List<Plane> findAll() throws DaoException {
        return super.findAll();
    }

    /*
    oldPlanes() - самолеты старше 20 лет
     */
    @Transactional
    public List<Plane> oldPlanes() throws DaoException {
        try {
            Query query = entityManager.createNativeQuery(OLD_PLANES_REQUEST, Plane.class);
            return query.getResultList();
        } catch (DaoException exception) {
            System.err.println(exception.getMessage());
            throw new HibernateException("Operation with planes was filed in method" +
                    " oldPlane() from class " + className);
        }
    }

    /*
    regularPlanes(int year) - самолеты, которые с больше 300 полетов за год
     */
    @Transactional
    public List<Plane> regularPlanes(int year) throws DaoException {
        try {
            Query query = entityManager.createNativeQuery(REGULAR_PLANES_REQUEST, Plane.class);
            query.setParameter(1, year);
            return query.getResultList();
        } catch (DaoException exception) {
            System.err.println(exception.getMessage());
            throw new HibernateException("Operation with planes was filed in method" +
                    " regularPlanes() from class " + className);
        }
    }
}
