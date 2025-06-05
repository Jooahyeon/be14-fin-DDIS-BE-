package com.ddis.ddis_hr.review.command.domain.repository;

import com.ddis.ddis_hr.review.command.domain.aggregate.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
