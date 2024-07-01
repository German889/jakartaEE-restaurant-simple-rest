package com.aston.second_task.mapper;

import com.aston.second_task.dto.incoming.DishDTOInc;
import com.aston.second_task.dto.outgoing.DishDTOOut;
import com.aston.second_task.entity.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DishMapper {
    DishMapper INSTANCE = Mappers.getMapper(DishMapper.class);
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "restaurant", target = "restaurant")
    @Mapping(source = "imageURL", target = "imageURL")
    Dish dishDTOIncToDish(DishDTOInc dishDTOInc);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "imageURL", target = "imageURL")
    DishDTOOut dishToDishDTOOut(Dish dish);
}
