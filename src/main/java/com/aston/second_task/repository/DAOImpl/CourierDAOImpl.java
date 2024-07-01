package com.aston.second_task.repository.DAOImpl;

import com.aston.second_task.entity.Courier;
import com.aston.second_task.repository.DAO.CourierDAO;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;
@Dependent
public class CourierDAOImpl implements CourierDAO {
    @PersistenceContext(unitName = "Givermaen")
    private EntityManager em;
    @Override
    public void save(Courier courier) {
        em.persist(courier);
    }

    @Override
    public Courier findById(Integer id) {
        return em.find(Courier.class, id);
    }

    @Override
    public List<Courier> findAll() {
        return em.createQuery("SELECT c FROM Courier c", Courier.class).getResultList();
    }

    @Override
    public void update(Courier courier) {
        em.merge(courier);
    }

    @Override
    public void remove(Integer id) {
        String jpql = "DELETE FROM Courier c WHERE c.id = :id";
        Query query = em.createQuery(jpql);
        query.setParameter("id", id);
        query.executeUpdate();
    }
    @Override
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
}
