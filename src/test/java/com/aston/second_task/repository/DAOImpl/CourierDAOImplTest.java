package com.aston.second_task.repository.DAOImpl;

import com.aston.second_task.entity.Courier;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

class CourierDAOImplTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private CourierDAOImpl courierDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save() {
        Courier courier = new Courier();
        courierDAO.save(courier);
        verify(em, times(1)).persist(courier);
    }

    @Test
    void findById() {
        Integer id = 1;
        Courier courier = new Courier();
        when(em.find(Courier.class, id)).thenReturn(courier);
        Courier result = courierDAO.findById(id);
        assertEquals(courier, result);
    }

    @Test
    void findAll() {
        // Mock the query result
        when(em.createQuery("SELECT c FROM Courier c", Courier.class).getResultList())
                .thenReturn(List.of(new Courier(), new Courier()));
        List<Courier> result = courierDAO.findAll();
        assertEquals(2, result.size());
    }

    @Test
    void update() {
        Courier courier = new Courier();
        when(em.merge(courier)).thenReturn(courier);
        courierDAO.update(courier);
        verify(em, times(1)).merge(courier);
    }

    @Test
    void remove() {
        Integer id = 1;
        doNothing().when(em).createQuery("DELETE FROM Courier c WHERE c.id = :id");
        courierDAO.remove(id);
        verify(em.createQuery("DELETE FROM Courier c WHERE c.id = :id"), times(1)).setParameter("id", id);
        verify(em.createQuery("DELETE FROM Courier c WHERE c.id = :id"), times(1)).executeUpdate();
    }

    @Test
    void setEntityManager() {
        EntityManager newEm = mock(EntityManager.class);
        courierDAO.setEntityManager(newEm);
        Assertions.assertEquals(newEm, courierDAO.getEntityManager());
    }
}