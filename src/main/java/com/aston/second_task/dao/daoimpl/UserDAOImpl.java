package com.aston.second_task.dao.daoimpl;

import com.aston.second_task.entity.AppUser;
import com.aston.second_task.dao.UserDAO;
import com.aston.second_task.exceptions.*;
import jakarta.enterprise.context.Dependent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Dependent
public class UserDAOImpl implements UserDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAOImpl.class);
    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public AppUser save(AppUser appUser) {
        String sql = "INSERT INTO app_user (" +
                "first_name, " +
                "last_name, " +
                "email, " +
                "phone, " +
                "password, " +
                "address, " +
                "role) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, appUser.getFirstName());
            pstmt.setString(2, appUser.getLastName());
            pstmt.setString(3, appUser.getEmail());
            pstmt.setString(4, appUser.getPhone());
            pstmt.setString(5, appUser.getPassword());
            pstmt.setString(6, appUser.getAddress());
            pstmt.setString(7, appUser.getRole());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    appUser.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException sqlException) {
            LOGGER.error("Error saving user in DB", sqlException);
            throw new ElementNotSavedException("Error saving AppUser");
        }
        return appUser;
    }

    public AppUser findById(Integer id) {
        AppUser user = null;
        String sql = "SELECT * FROM app_user WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    user = new AppUser();
                    user.setId(rs.getInt("id"));
                    user.setFirstName(rs.getString("first_name"));
                    user.setLastName(rs.getString("last_name"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                    user.setPassword(rs.getString("password"));
                    user.setAddress(rs.getString("address"));
                    user.setRole(rs.getString("role"));
                }
            }
        } catch (SQLException sqlException) {
            LOGGER.error("Error try to find user: " + id, sqlException);
            throw new ElementNotFoundExceptions("Error find AppUser");
        }
        return user;
    }

    public List<AppUser> findAll() {
        List<AppUser> lp = new ArrayList<>();
        String sql = "SELECT * FROM app_user";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AppUser user = new AppUser();
                user.setId(rs.getInt("id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setPassword(rs.getString("password"));
                user.setAddress(rs.getString("address"));
                user.setRole(rs.getString("role"));
                lp.add(user);
            }
        } catch (SQLException sqlException) {
            LOGGER.error("Error find all users ", sqlException);
            throw new ElementsNotFoundException("Error finding list of AppUsers");
        }
        return lp;
    }

    public AppUser update(AppUser appUser) {
        String sql = "UPDATE app_user " +
                "SET first_name = ?, " +
                "last_name = ?, " +
                "email = ?, " +
                "phone = ?, " +
                "password = ?, " +
                "address = ?, " +
                "role = ? " +
                "WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, appUser.getFirstName());
            ps.setString(2, appUser.getLastName());
            ps.setString(3, appUser.getEmail());
            ps.setString(4, appUser.getPhone());
            ps.setString(5, appUser.getPassword());
            ps.setString(6, appUser.getAddress());
            ps.setString(7, appUser.getRole());
            ps.setInt(8, appUser.getId());
            ps.executeUpdate();
        } catch (SQLException sqlException) {
            LOGGER.error("Error updating user ", sqlException);
            throw new ElementNotUpdatedException("Error updating AppUser");
        }
        return appUser;
    }

    public Integer delete(Integer id) {
        String sql = "DELETE FROM app_user WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException sqlException) {
            LOGGER.error("Error deleting user ", sqlException);
            throw new ElementNotDeletedException("Error deleting AppUser");
        }
        return id;
    }

    public Integer getId(AppUser appUser) {
        Integer foundId = null;
        String sql = "SELECT id FROM app_user WHERE " +
                "first_name = ? AND " +
                "last_name = ? AND " +
                "email = ? AND " +
                "phone = ? AND " +
                "password = ? AND " +
                "address = ? AND " +
                "role = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, appUser.getFirstName());
            ps.setString(2, appUser.getLastName());
            ps.setString(3, appUser.getEmail());
            ps.setString(4, appUser.getPhone());
            ps.setString(5, appUser.getPassword());
            ps.setString(6, appUser.getAddress());
            ps.setString(7, appUser.getRole());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                foundId = rs.getInt("id");
            }
        } catch (SQLException sqlException) {
            LOGGER.error("Error getting id of user ", sqlException);
            throw new IdNotReceivedException("Id retrieval error of AppUser");
        }
        return foundId;
    }
}