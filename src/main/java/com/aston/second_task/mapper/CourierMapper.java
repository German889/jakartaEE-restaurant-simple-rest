package com.aston.second_task.mapper;

import com.aston.second_task.dto.incoming.CourierDTOInc;
import com.aston.second_task.dto.outgoing.CourierDTOOut;
import com.aston.second_task.entity.Courier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CourierMapper {
    CourierMapper INSTANCE = Mappers.getMapper(CourierMapper.class);

    @Mapping(source = "user", target = "user")
    @Mapping(source = "vehicleRegistrationNumber", target = "vehicleRegistrationNumber")
    @Mapping(source = "vehicleModel", target = "vehicleModel")
    @Mapping(source = "status", target = "status")
    Courier courierDTOIncToCourier(CourierDTOInc courierDTOInc);

    @Mapping(source = "user", target = "user")
    @Mapping(source = "vehicleModel", target = "vehicleModel")
    @Mapping(source = "status", target = "status")
    CourierDTOOut courierToCourierDTOOut(Courier courier);
}
