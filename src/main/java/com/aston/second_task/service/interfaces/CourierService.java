package com.aston.second_task.service.interfaces;

import com.aston.second_task.entity.Courier;

import java.util.List;

public interface CourierService {
    void saveCourier(Courier courier);
    Courier findCourierById(Integer id);
    List<Courier> findAllCouriers();
    void updateCourier(Courier courier, Integer id);
    void deleteCourier(Integer id);
}