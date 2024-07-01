package com.aston.second_task.mapper;

import com.aston.second_task.dto.incoming.RestaurantDTOInc;
import com.aston.second_task.dto.outgoing.RestaurantDTOOut;
import com.aston.second_task.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper
public interface RestaurantMapper {
    RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);
    @Mapping(source = "name", target = "name")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "rating", target = "rating")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "workingHours", target = "workingHours")
    Restaurant restaurantDTOIncToRestaurant(RestaurantDTOInc restaurantDTOInc);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "rating", target = "rating")
    RestaurantDTOOut restaurantToRestaurantDTOOut(Restaurant restaurant);
}
