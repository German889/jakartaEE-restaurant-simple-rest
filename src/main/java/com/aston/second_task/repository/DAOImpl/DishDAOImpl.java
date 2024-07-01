package com.aston.second_task.repository.DAOImpl;

import com.aston.second_task.entity.Dish;
import com.aston.second_task.repository.DAO.DishDAO;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;
@Dependent
public class DishDAOImpl implements DishDAO {
    @PersistenceContext(unitName = "Givermaen")
    private EntityManager em;
    @Override
    public void save(Dish dish) {
        em.persist(dish);
    }

    @Override
    public Dish findById(Integer id) {
        return em.find(Dish.class, id);
    }

    @Override
    public List<Dish> findAll() {
        return em.createQuery("SELECT d FROM Dish d", Dish.class).getResultList();
    }

    @Override
    public void update(Dish dish) {
        em.merge(dish);
    }

    @Override
    public void delete(Integer id) {
        String jpql = "DELETE FROM Dish d WHERE d.id = :id";
        Query query = em.createQuery(jpql);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
}
