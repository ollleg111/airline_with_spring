package com.spring_db.dao;

import com.spring_db.entity.Passenger;
import com.spring_db.exceptions.DaoException;
import com.spring_db.service.PassengerService;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class PassengerDAO extends GeneralDAO<Passenger> {
    @PersistenceContext
    private EntityManager entityManager;

    public PassengerDAO() {
        setEntityManager(entityManager);
        setTypeParameterClass(Passenger.class);
    }

    private static final String REGULAR_PASSENGERS_REQUEST =
            "SELECT " +
                    "p.id, p.last_name, p.nationality, p.date_of_birth, p.passport_code FROM " +
                    "passenger p, " +
                    "flights_passengers fp, " +
                    "flight f " +
                    "WHERE p.id = fp.passenger_id AND " +
                    "fp.flight_id = f.id AND " +
                    "TO_NUMBER(TO_CHAR(f.date_flight,'YYYY')) = ? " +
                    "GROUP BY " +
                    "p.id, p.last_name, p.nationality, p.date_of_birth, p.passport_code " +
                    "HAVING COUNT(fp.flight_id) > 25";

    @Override
    public Passenger findById(Long id) throws DaoException {
        return super.findById(id);
    }

    @Override
    public Passenger save(Passenger passenger) throws DaoException {
        return super.save(passenger);
    }

    @Override
    public Passenger update(Passenger passenger) throws DaoException {
        return super.update(passenger);
    }

    @Override
    public void delete(Passenger passenger) throws DaoException {
        super.delete(passenger);
    }

    @Override
    public List<Passenger> findAll() throws DaoException {
        return super.findAll();
    }

    /*
    regularPassengers(int year) - пассажиры, с больше 25 полетов за год
     */
    @Transactional
    public List<Passenger> regularPassengers(int year) throws DaoException {
        try {
            Query query = entityManager.createNativeQuery(REGULAR_PASSENGERS_REQUEST, Passenger.class);
            query.setParameter(1, year);
            return query.getResultList();
        } catch (DaoException exception) {
            System.err.println(exception.getMessage());
            throw new HibernateException("Operation with passengers was filed in method" +
                    " regularPassengers() from class " + PassengerService.class.getName());
        }
    }
}
