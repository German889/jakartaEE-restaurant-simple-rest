package com.aston.second_task.service.implementations;

import com.aston.second_task.entity.Courier;
import com.aston.second_task.repository.DAO.CourierDAO;
import com.aston.second_task.service.interfaces.CourierService;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.transaction.UserTransaction;

import java.util.List;

@ApplicationScoped
public class CourierServiceImpl implements CourierService {

    @Inject
    private CourierDAO courierDAO;

    @Resource
    private UserTransaction userTransaction;

    @Transactional
    public void saveCourier(Courier courier) {
        courierDAO.save(courier);
    }

    public Courier findCourierById(Integer id) {
        return courierDAO.findById(id);
    }

    public List<Courier> findAllCouriers() {
        return courierDAO.findAll();
    }

    @Transactional
    public void updateCourier(Courier courier, Integer id) {
        courier.setId(id);
        courierDAO.update(courier);
    }

    @Transactional
    public void deleteCourier(Integer id) {
        courierDAO.remove(id);
    }
}