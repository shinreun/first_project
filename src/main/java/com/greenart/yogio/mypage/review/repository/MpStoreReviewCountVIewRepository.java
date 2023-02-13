package com.greenart.yogio.mypage.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenart.yogio.mypage.review.entity.MpStoreReviewCountViewEntity;

@Repository
public interface MpStoreReviewCountVIewRepository extends JpaRepository<MpStoreReviewCountViewEntity, Long>{
  MpStoreReviewCountViewEntity findBySiSeq(Long siSeq);
}
