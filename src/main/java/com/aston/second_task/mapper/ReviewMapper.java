package com.aston.second_task.mapper;

import com.aston.second_task.dto.incoming.ReviewDTOInc;
import com.aston.second_task.dto.outgoing.ReviewDTOOut;
import com.aston.second_task.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReviewMapper {
    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);
    @Mapping(source = "user", target = "user")
    @Mapping(source = "restaurant", target = "restaurant")
    Review reviewDTOIncToReview(ReviewDTOInc reviewDTOInc);

    @Mapping(source = "user", target = "user")
    @Mapping(source = "restaurant", target = "restaurant")
    ReviewDTOOut reviewToReviewDTOOut(Review review);
}
