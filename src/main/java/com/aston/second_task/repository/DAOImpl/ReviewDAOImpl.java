package com.aston.second_task.repository.DAOImpl;

import com.aston.second_task.entity.Review;
import com.aston.second_task.repository.DAO.ReviewDAO;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;
@Dependent
public class ReviewDAOImpl implements ReviewDAO {
    @PersistenceContext(unitName = "Givermaen")
    private EntityManager em;
    @Override
    public void save(Review review) {
        em.persist(review);
    }

    @Override
    public Review findById(Integer id) {
        return em.find(Review.class, id);
    }

    @Override
    public List<Review> findAll() {
        return em.createQuery("SELECT r FROM Review r", Review.class).getResultList();
    }

    @Override
    public void update(Review review) {
        em.merge(review);
    }

    @Override
    public void delete(Integer id) {
        String jpql = "DELETE FROM Review r WHERE r.id = :id";
        Query query = em.createQuery(jpql);
        query.setParameter("id", id);
        query.executeUpdate();
    }
    @Override
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
}
