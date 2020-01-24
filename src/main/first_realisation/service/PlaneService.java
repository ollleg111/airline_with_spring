//package com.spring_db.first_realisation.dao.service;
//
//import com.spring_db.dao.GeneralDAO;
//import com.spring_db.dao.PlaneDAO;
//import com.spring_db.entity.Plane;
//import com.spring_db.exceptions.BadRequestException;
//import com.spring_db.exceptions.DaoException;
//import com.spring_db.exceptions.ServiceException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;
//import java.util.List;
//
//@Service
//public class PlaneService extends GeneralService<Plane> {
//
//        private static final String OLD_PLANES_REQUEST =
//            "SELECT " +
//                    "p.id, " +
//                    "p.model, " +
//                    "p.code, " +
//                    "p.year_produced, " +
//                    "p.avg_fuel_consumption " +
//                    "FROM PLANE p " +
//                    "WHERE TO_NUMBER(TO_CHAR(SYSDATE,'YYYY') - TO_CHAR(YEAR_PRODUCED,'YYYY')) > 20";
//
//    /*
//     private static final String OLD_PLANES_REQUEST =
//            "SELECT * FROM PLANE " +
//                    "WHERE TO_NUMBER(TO_CHAR(EXTRACT(YEAR FROM SYSDATE)) - TO_CHAR(YEAR_PRODUCED,'YYYY')) > 20";
//     */
//
//    private static final String REGULAR_PLANES_REQUEST =
//            "SELECT" +
//                    "p.id, p.model, p.code, p.year_produced, p.avg_fuel_consumption " +
//                    "FROM plane p, flight f " +
//                    "WHERE " +
//                    "f.plane = p.id AND" +
//                    "TO_NUMBER(TO_CHAR(f.date_flight,'YYYY')) = ? GROUP BY" +
//                    "p.id, p.model, p.code, p.year_produced, p.avg_fuel_consumption HAVING COUNT(f.id) > 300;";
//
//    private String alarmMessage = PlaneService.class.getName();
//
//    private PlaneDAO planeDAO;
//
//    @PersistenceContext
//    EntityManager entityManager;
//
//    @Autowired
//    public PlaneService(GeneralDAO<Plane> dao, PlaneDAO planeDAO) {
//        super(dao);
//        this.planeDAO = planeDAO;
//    }
//
//    @Override
//    public Plane findById(Long id) throws ServiceException {
//        Plane plane = planeDAO.getOne(id);
////        planeNullValidator(plane);
//        GeneralService.nullValidator(plane);
//        return super.findById(id);
//    }
//
//    @Override
//    public Plane save(Plane plane) throws ServiceException {
//        return super.save(plane);
//    }
//
//    @Override
//    public Plane update(Plane plane) throws ServiceException {
//        return super.update(plane);
//    }
//
//    @Override
//    public void delete(Plane plane) throws ServiceException {
//        super.delete(plane);
//    }
//
//    public void deleteById(Long id) throws ServiceException {
//        Plane plane = planeDAO.getOne(id);
////        planeNullValidator(plane);
//        GeneralService.nullValidator(plane);
//        super.delete(plane);
//    }
//
//    @Override
//    public List<Plane> findAll() throws ServiceException {
//        return super.findAll();
//    }
//
//    /*
//    oldPlanes() - самолеты старше 20 лет
//     */
//    @Transactional
//    public List<Plane> oldPlanes() throws ServiceException {
//        try {
//            Query query = entityManager.createNativeQuery(OLD_PLANES_REQUEST, Plane.class);
//            return query.getResultList();
//        } catch (DaoException exception) {
//            System.err.println(exception.getMessage());
//            throw new ServiceException("Operation with planes was filed in method" +
//                    " oldPlane() from class " + alarmMessage);
//        }
//    }
//
//    /*
//    regularPlanes(int year) - самолеты, которые с больше 300 полетов за год
//     */
//    @Transactional
//    public List<Plane> regularPlanes(int year) throws ServiceException {
//        yearValidator(year);
//        try {
//            Query query = entityManager.createNativeQuery(REGULAR_PLANES_REQUEST, Plane.class);
//            query.setParameter(1, year);
//            return query.getResultList();
//        } catch (DaoException exception) {
//            System.err.println(exception.getMessage());
//            throw new ServiceException("Operation with planes was filed in method" +
//                    " regularPlanes() from class " + alarmMessage);
//        }
//    }
//
//    private void yearValidator(int year) throws BadRequestException {
//        if (year <= 0) throw new BadRequestException(" You entered wrong data in field: Year ! This is test " +
//                " from method yearValidator(int year) in the method " + alarmMessage);
//    }
//
//    /*
//        private void planeNullValidator(Plane plane) throws RuntimeException {
//        if (plane == null) throw new BadRequestException("Plane does not exist in method" +
//                " planeNullValidator(Plane plane) from class " +
//                PlaneService.class.getName());
//    }
//     */
//}
