package com.greenart.yogio.mypage.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenart.yogio.mypage.review.entity.MpMypageReviewViewEntity;

@Repository
public interface MpMypageReviewViewRepository extends JpaRepository<MpMypageReviewViewEntity, Long> {
  Page<MpMypageReviewViewEntity> findAll(Pageable pageable);
}
