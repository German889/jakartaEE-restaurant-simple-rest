package com.aston.second_task.dao.daoimpl;

import com.aston.second_task.entity.Dish;
import com.aston.second_task.dao.DishDAO;
import com.aston.second_task.entity.Restaurant;
import com.aston.second_task.exceptions.*;
import jakarta.enterprise.context.Dependent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Dependent
public class DishDAOImpl implements DishDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(DishDAOImpl.class);

    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void save(Dish dish) {
        String sql = "INSERT INTO dish (" +
                "name, " +
                "description, " +
                "price, " +
                "restaurant_id, " +
                "imageURL) " +
                "VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, dish.getName());
            ps.setString(2, dish.getDescription());
            ps.setString(3, dish.getPrice());
            ps.setInt(4, dish.getRestaurant().getId());
            ps.setString(5, dish.getImageURL());
            ps.executeUpdate();
        } catch (SQLException se) {
            LOGGER.error("Error saving dish ", se);
            throw new ElementNotSavedException("Error saving Dish");
        }
    }

    public Dish findById(Integer id) {
        String sql = "SELECT * FROM dish WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Dish dish = new Dish();
                dish.setId(rs.getInt("id"));
                dish.setName(rs.getString("name"));
                dish.setDescription(rs.getString("description"));
                dish.setPrice(rs.getString("price"));
                int restaurantId = rs.getInt("restaurant_id");
                RestaurantDAOImpl restaurantDAO = new RestaurantDAOImpl();
                restaurantDAO.setConnection(connection);
                Restaurant rt = restaurantDAO.findById(restaurantId);
                dish.setRestaurant(rt);
                dish.setImageURL(rs.getString("imageURL"));
                return dish;
            }
        } catch (SQLException se) {
            LOGGER.error("Error finding dish", se);
            throw new ElementNotFoundExceptions("Error finding Dish with id " + id);
        }
        return null;
    }

    public List<Dish> findAll() {
        String sql = "SELECT * FROM dish";
        List<Dish> dishes = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Dish dish = new Dish();
                dish.setId(rs.getInt("id"));
                dish.setName(rs.getString("name"));
                dish.setDescription(rs.getString("description"));
                dish.setPrice(rs.getString("price"));
                int restaurantId = rs.getInt("restaurant_id");
                RestaurantDAOImpl restaurantDAO = new RestaurantDAOImpl();
                restaurantDAO.setConnection(connection);
                Restaurant rt = restaurantDAO.findById(restaurantId);
                dish.setRestaurant(rt);
                dish.setImageURL(rs.getString("imageURL"));
                dishes.add(dish);
            }
        } catch (SQLException se) {
            LOGGER.error("Error finding all dishes", se);
            throw new ElementsNotFoundException("Error finding list of Dishes");
        }
        return dishes;
    }

    public void update(Dish dish) {
        String sql = "UPDATE dish SET name = ?, description = ?, price = ?, restaurant_id = ?, imageURL = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, dish.getName());
            ps.setString(2, dish.getDescription());
            ps.setString(3, dish.getPrice());
            ps.setInt(4, dish.getRestaurant().getId());
            ps.setString(5, dish.getImageURL());
            ps.setInt(6, dish.getId());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new ElementNotFoundExceptions("Dish with id " + dish.getId() + " not found for update");
            }
        } catch (SQLException se) {
            LOGGER.error("Error updating dish ", se);
            throw new ElementNotUpdatedException("Error updating Dish with id " + dish.getId());
        }
    }

    public void delete(Integer id) {
        String sql = "DELETE FROM dish WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new ElementNotFoundExceptions("Dish with id " + id + " not found for delete");
            }
        } catch (SQLException se) {
            LOGGER.error("Error deleting dish ", se);
            throw new ElementNotDeletedException("Error deleting Dish with id " + id);
        }
    }
}