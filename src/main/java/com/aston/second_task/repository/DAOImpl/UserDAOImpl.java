package com.aston.second_task.repository.DAOImpl;

import com.aston.second_task.entity.User;
import com.aston.second_task.repository.DAO.UserDAO;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;

@Dependent
public class UserDAOImpl implements UserDAO {

    @PersistenceContext(unitName = "Givermaen")
    private EntityManager em;

    @Override
    public User save(User user) {
        em.persist(user); return user;
    }

    @Override
    public User findById(Integer id) {
        return em.find(User.class, id);
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public User update(User user) {
        em.merge(user); return user;
    }

    @Override
    public Integer delete(Integer id) {
        String jpql = "DELETE FROM User u WHERE u.id = :id";
        Query query = em.createQuery(jpql);
        query.setParameter("id",id);
        query.executeUpdate();
        return id;
    }
    @Override
    public Integer getId(User user){
        String jpql = "SELECT u.id FROM User u WHERE u.email = :email AND u.password = :password";
        Query query = em.createQuery(jpql);
        query.setParameter("email", user.getEmail());
        query.setParameter("password", user.getPassword());
        try{
            return (Integer) query.getSingleResult();
        }catch (NoResultException e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

}