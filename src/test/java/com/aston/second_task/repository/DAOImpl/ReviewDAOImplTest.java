package com.aston.second_task.repository.DAOImpl;

import com.aston.second_task.entity.Review;
import com.aston.second_task.repository.DAO.ReviewDAO;
import com.aston.second_task.repository.DAOImpl.ReviewDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class ReviewDAOImplTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private ReviewDAO reviewDAO;

    @BeforeEach
    public void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("test", persistenceProperties());
        entityManager = entityManagerFactory.createEntityManager();
        reviewDAO = new ReviewDAOImpl();
        ((ReviewDAOImpl) reviewDAO).setEntityManager(entityManager);
    }

    private java.util.Map<String, String> persistenceProperties() {
        return java.util.Map.of(
                "jakarta.persistence.jdbc.url", postgreSQLContainer.getJdbcUrl(),
                "jakarta.persistence.jdbc.user", postgreSQLContainer.getUsername(),
                "jakarta.persistence.jdbc.password", postgreSQLContainer.getPassword()
        );
    }

    @Test
    public void testSave() {
        Review review = new Review();
        review.setId(1);

        entityManager.getTransaction().begin();
        reviewDAO.save(review);
        entityManager.getTransaction().commit();
    }

    @Test
    public void testFindById() {
        Review review = new Review();
        review.setId(1);
        reviewDAO.save(review);

        Review foundReview = reviewDAO.findById(review.getId());
        assertNotNull(foundReview);
        assertEquals(review.getId(), foundReview.getId());
    }

    @Test
    public void testFindAll() {
        Review review1 = new Review();
        review1.setId(2);

        Review review2 = new Review();
        review2.setId(3);

        entityManager.getTransaction().begin();
        reviewDAO.save(review1);
        reviewDAO.save(review2);
        entityManager.getTransaction().commit();

        List<Review> reviews = reviewDAO.findAll();
        assertEquals(2, reviews.size());
    }

    @Test
    public void testUpdate() {
        Review review = new Review();
        review.setId(3);
        reviewDAO.save(review);
        review.setId(5);

        entityManager.getTransaction().begin();
        reviewDAO.update(review);
        entityManager.getTransaction().commit();

        Review updatedReview = reviewDAO.findById(review.getId());
        assertNotNull(updatedReview);
        assertEquals(5, updatedReview.getId());
    }

    @Test
    public void testDelete() {
        Review review = new Review();
        review.setId(38);

        entityManager.getTransaction().begin();
        reviewDAO.save(review);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        reviewDAO.delete(review.getId());
        entityManager.getTransaction().commit();

        Review deletedReview = reviewDAO.findById(review.getId());
        assertNull(deletedReview);
    }
}