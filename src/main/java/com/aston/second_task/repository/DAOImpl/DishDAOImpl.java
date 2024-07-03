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
    public void save(Dish dish) {
        em.persist(dish);
    }

    public Dish findById(Integer id) {
        return em.find(Dish.class, id);
    }

    public List<Dish> findAll() {
        return em.createQuery("SELECT d FROM Dish d", Dish.class).getResultList();
    }

    public void update(Dish dish) {
        em.merge(dish);
    }

    public void delete(Integer id) {
        String jpql = "DELETE FROM Dish d WHERE d.id = :id";
        Query query = em.createQuery(jpql);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
}
