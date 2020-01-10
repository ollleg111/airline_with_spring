package com.spring_db.service.filter;

import com.spring_db.entity.Filter;
import com.spring_db.entity.Flight;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class PlaneModelsList extends Criteria {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Filter getFilter() {
        return super.getFilter();
    }

    @Override
    public void setFilter(Filter filter) {
        super.setFilter(filter);
    }

    @Override
    public List<Flight> criteriaFlights() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Flight> cq = cb.createQuery(Flight.class);
        Root<Flight> flightRoot = cq.from(Flight.class);
        cq.select(flightRoot);

        TypedQuery<Flight> typedQuery = entityManager.createQuery(cq);

        return typedQuery.getResultList();
    }
}
