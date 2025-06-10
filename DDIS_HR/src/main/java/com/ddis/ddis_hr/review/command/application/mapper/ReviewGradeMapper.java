package com.ddis.ddis_hr.review.command.application.mapper;

import com.ddis.ddis_hr.review.command.domain.aggregate.ReviewGrade;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReviewGradeMapper {
    ReviewGrade selectByScore(@Param("score") int score);
}
