package com.aston.second_task.repository.DAOImpl;

import com.aston.second_task.entity.AppUser;
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

    public AppUser save(AppUser appUser) {
        em.persist(appUser); return appUser;
    }

    public AppUser findById(Integer id) {
        return em.find(AppUser.class, id);
    }

    public List<AppUser> findAll() {
        return em.createQuery("SELECT u FROM AppUser u", AppUser.class).getResultList();
    }

    public AppUser update(AppUser appUser) {
        em.merge(appUser); return appUser;
    }

    public Integer delete(Integer id) {
        String jpql = "DELETE FROM AppUser u WHERE u.id = :id";
        Query query = em.createQuery(jpql);
        query.setParameter("id",id);
        query.executeUpdate();
        return id;
    }
    public Integer getId(AppUser appUser){
        String jpql = "SELECT u.id FROM AppUser u WHERE u.email = :email AND u.password = :password";
        Query query = em.createQuery(jpql);
        query.setParameter("email", appUser.getEmail());
        query.setParameter("password", appUser.getPassword());
        try{
            return (Integer) query.getSingleResult();
        }catch (NoResultException e){
            e.printStackTrace();
            return 0;
        }
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

}