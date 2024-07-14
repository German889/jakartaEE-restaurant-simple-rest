package com.aston.second_task.dao.daoimpl;

import com.aston.second_task.entity.AppUser;
import com.aston.second_task.entity.Restaurant;
import com.aston.second_task.dao.RestaurantDAO;
import com.aston.second_task.exceptions.*;
import jakarta.enterprise.context.Dependent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

@Dependent
public class RestaurantDAOImpl implements RestaurantDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantDAOImpl.class);
    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void save(Restaurant restaurant) {
        String sql = "INSERT INTO restaurant (" +
                "name, " +
                "address, " +
                "rating, " +
                "email, " +
                "phone, " +
                "working_hours) " +
                "VALUES(?,?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, restaurant.getName());
            ps.setString(2, restaurant.getAddress());
            ps.setBigDecimal(3, restaurant.getRating());
            ps.setString(4, restaurant.getEmail());
            ps.setString(5, restaurant.getPhone());
            ps.setString(6, restaurant.getWorkingHours());
            ps.executeUpdate();
        } catch (SQLException se) {
            LOGGER.error("Error saving restaurant ", se);
            throw new ElementNotSavedException("Error saving Restaurant");
        }
    }

    public Restaurant findById(Integer id) {
        String sqlRestaurants = "SELECT * FROM restaurant WHERE id = ?";
        String sqlUsers = "SELECT DISTINCT au.* " +
                "FROM app_user au " +
                "JOIN review r ON au.id = r.user_id " +
                "WHERE r.restaurant_id = ?;";
        Restaurant rt = null;
        try (PreparedStatement st = connection.prepareStatement(sqlRestaurants)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                rt = new Restaurant();
                rt.setId(rs.getInt("id"));
                rt.setName(rs.getString("name"));
                rt.setAddress(rs.getString("address"));
                rt.setEmail(rs.getString("email"));
                rt.setRating(rs.getBigDecimal("rating"));
                rt.setPhone(rs.getString("phone"));
                rt.setWorkingHours(rs.getString("working_hours"));
                Set<AppUser> reviewers = new HashSet<>();
                try (PreparedStatement ps1 = connection.prepareStatement(sqlUsers)) {
                    ps1.setInt(1, id);
                    ResultSet rs1 = ps1.executeQuery();
                    while (rs1.next()) {
                        AppUser appUser = new AppUser();
                        appUser.setId(rs1.getInt("id"));
                        appUser.setFirstName(rs1.getString("first_name"));
                        appUser.setLastName(rs1.getString("last_name"));
                        appUser.setEmail(rs1.getString("email"));
                        appUser.setPhone(rs1.getString("phone"));
                        appUser.setPassword(rs1.getString("password"));
                        appUser.setAddress(rs1.getString("address"));
                        appUser.setRole(rs1.getString("role"));
                        reviewers.add(appUser);
                    }
                }
                rt.setReviewOwners(reviewers);
            }
        } catch (SQLException se) {
            LOGGER.error("Error finding restaurant ", se);
            throw new ElementNotFoundExceptions("Error finding Restaurant with id " + id);
        }
        return rt;
    }

    public List<Restaurant> findAll() {
        String sqlUsers = "SELECT DISTINCT au.* " +
                "FROM app_user au " +
                "JOIN review r ON au.id = r.user_id " +
                "WHERE r.restaurant_id = ?;";
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM restaurant");
            while (rs.next()) {
                Restaurant rt = new Restaurant();
                rt.setId(rs.getInt("id"));
                rt.setName(rs.getString("name"));
                rt.setAddress(rs.getString("address"));
                rt.setEmail(rs.getString("email"));
                rt.setRating(rs.getBigDecimal("rating"));
                rt.setPhone(rs.getString("phone"));
                rt.setWorkingHours(rs.getString("working_hours"));
                Set<AppUser> reviewers = new HashSet<>();
                try (PreparedStatement ps1 = connection.prepareStatement(sqlUsers)) {
                    ps1.setInt(1, rt.getId());
                    ResultSet rs1 = ps1.executeQuery();
                    while (rs1.next()) {
                        AppUser appUser = new AppUser();
                        appUser.setId(rs1.getInt("id"));
                        appUser.setFirstName(rs1.getString("first_name"));
                        appUser.setLastName(rs1.getString("last_name"));
                        appUser.setEmail(rs1.getString("email"));
                        appUser.setPhone(rs1.getString("phone"));
                        appUser.setPassword(rs1.getString("password"));
                        appUser.setAddress(rs1.getString("address"));
                        appUser.setRole(rs1.getString("role"));
                        reviewers.add(appUser);
                    }
                }
                rt.setReviewOwners(reviewers);
                restaurants.add(rt);
            }
        } catch (SQLException se) {
            LOGGER.error("Error finding all restaurants", se);
            throw new ElementsNotFoundException("Error finding list of Restaurants");
        }
        return restaurants;
    }

    public void update(Restaurant restaurant) {
        String sql = "UPDATE restaurant " +
                "SET " +
                "name = ?, " +
                "address = ?, " +
                "rating = ?, " +
                "email = ?, " +
                "phone = ?, " +
                "working_hours = ? " +
                "WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, restaurant.getName());
            ps.setString(2, restaurant.getAddress());
            ps.setBigDecimal(3, restaurant.getRating());
            ps.setString(4, restaurant.getEmail());
            ps.setString(5, restaurant.getPhone());
            ps.setString(6, restaurant.getWorkingHours());
            ps.setInt(7, restaurant.getId());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new ElementNotUpdatedException("Restaurant with id " + restaurant.getId() + " not found for update");
            }
        } catch (SQLException se) {
            LOGGER.error("Error updating restaurant", se);
            throw new ElementNotUpdatedException("Error updating Restaurant with id " + restaurant.getId());
        }
    }

    public void delete(Integer id) {
        String sql = "DELETE FROM restaurant WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new ElementNotFoundExceptions("Restaurant with id " + id + " not found for delete");
            }
        } catch (SQLException se) {
            LOGGER.error("Error deleting restaurant", se);
            throw new ElementNotDeletedException("Error deleting Restaurant with id " + id);
        }
    }

    public Integer getId(Restaurant restaurant) {
        String sql = "SELECT id FROM restaurant WHERE " +
                "name = ? AND " +
                "address = ? AND " +
                "rating = ? AND " +
                "email = ? AND " +
                "phone = ? AND " +
                "working_hours = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, restaurant.getName());
            ps.setString(2, restaurant.getAddress());
            ps.setBigDecimal(3, restaurant.getRating());
            ps.setString(4, restaurant.getEmail());
            ps.setString(5, restaurant.getPhone());
            ps.setString(6, restaurant.getWorkingHours());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException se) {
            LOGGER.error("Error getting id", se);
            throw new IdNotReceivedException("Id retrieval error of Restaurant");
        }
        return null;
    }
}