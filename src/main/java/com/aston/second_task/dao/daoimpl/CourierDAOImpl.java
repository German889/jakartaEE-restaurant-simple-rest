package com.aston.second_task.dao.daoimpl;

import com.aston.second_task.dao.CourierDAO;
import com.aston.second_task.entity.AppUser;
import com.aston.second_task.entity.Courier;
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

public class CourierDAOImpl implements CourierDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourierDAOImpl.class);
    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void save(Courier courier) {
        String sql = "INSERT INTO courier (userid, vehicle_registration_number, vehicle_model, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, courier.getUser().getId());
            ps.setString(2, courier.getVehicleRegistrationNumber());
            ps.setString(3, courier.getVehicleModel());
            ps.setString(4, courier.getStatus());
            ps.executeUpdate();
        } catch (SQLException se) {
            LOGGER.error("Error saving courier", se);
            throw new ElementNotSavedException("Error saving Courier");
        }
    }

    public Courier findById(Integer id) {
        String sql = "SELECT * FROM courier WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToCourier(rs);
            }
        } catch (SQLException se) {
            LOGGER.error("Error finding courier by id", se);
            throw new ElementNotFoundExceptions("Error finding Courier with id " + id);
        }
        return null;
    }

    public List<Courier> findAll() {
        String sql = "SELECT * FROM courier";
        List<Courier> couriers = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                couriers.add(mapResultSetToCourier(rs));
            }
        } catch (SQLException se) {
            LOGGER.error("Error finding all couriers", se);
            throw new ElementsNotFoundException("Error finding list of Couriers");
        }
        return couriers;
    }

    public void update(Courier courier) {
        String sql = "UPDATE courier SET userid = ?, vehicle_registration_number = ?, vehicle_model = ?, status = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, courier.getUser().getId());
            ps.setString(2, courier.getVehicleRegistrationNumber());
            ps.setString(3, courier.getVehicleModel());
            ps.setString(4, courier.getStatus());
            ps.setInt(5, courier.getId());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new ElementNotFoundExceptions("Courier with id " + courier.getId() + " not found for update");
            }
        } catch (SQLException se) {
            LOGGER.error("Error updating courier", se);
            throw new ElementNotUpdatedException("Error updating Courier with id " + courier.getId());
        }
    }

    public void remove(Integer id) {
        String sql = "DELETE FROM courier WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new ElementNotFoundExceptions("Courier with id " + id + " not found for delete");
            }
        } catch (SQLException se) {
            LOGGER.error("Error deleting courier", se);
            throw new ElementNotDeletedException("Error deleting Courier with id " + id);
        }
    }

    Courier mapResultSetToCourier(ResultSet rs) throws SQLException {
        Courier courier = new Courier();
        courier.setId(rs.getInt("id"));
        AppUser appUser = new AppUser();
        appUser.setId(rs.getInt("userid"));
        courier.setUser(appUser);
        courier.setVehicleRegistrationNumber(rs.getString("vehicle_registration_number"));
        courier.setVehicleModel(rs.getString("vehicle_model"));
        courier.setStatus(rs.getString("status"));
        return courier;
    }
}