package com.aston.second_task.repository.DAOImpl;

import com.aston.second_task.entity.Restaurant;
import com.aston.second_task.repository.DAO.RestaurantDAO;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;
import java.util.NoSuchElementException;

@Dependent
public class RestaurantDAOImpl implements RestaurantDAO {
    @PersistenceContext(unitName = "Givermaen")
    private EntityManager em;
    @Override
    public void save(Restaurant restaurant) {
        em.persist(restaurant);
    }

    @Override
    public Restaurant findById(Integer id) {
        return em.find(Restaurant.class, id);
    }

    @Override
    public List<Restaurant> findAll() {
        return em.createQuery("SELECT r FROM Restaurant r", Restaurant.class).getResultList();
    }

    @Override
    public void update(Restaurant restaurant) {
        em.merge(restaurant);
    }

    @Override
    public void delete(Integer id) {
        String jpql = "DELETE FROM Restaurant r WHERE r.id = :id";
        Query query = em.createQuery(jpql);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Integer getId(Restaurant restaurant){
        try{
            String jpql = "SELECT r.id FROM Restaurant r " +
                    "WHERE r.address = :address AND r.email = :email";
            Query query = em.createQuery(jpql);
            query.setParameter("address", restaurant.getAddress());
            query.setParameter("email", restaurant.getEmail());
            return (Integer) query.getSingleResult();
        }catch(NoSuchElementException e){
            e.printStackTrace();
            return 0;
        }
    }
    @Override
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
}
