package com.greenart.yogio.mypage.review.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenart.yogio.mypage.order.entity.MpOrderInfoEntity;
import com.greenart.yogio.mypage.review.entity.MpReviewInfoEntity;

@Repository
public interface MpReviewInfoRepository extends JpaRepository<MpReviewInfoEntity, Long> {
  MpReviewInfoEntity findByOrder(MpOrderInfoEntity order);

  Page<MpReviewInfoEntity> findAllByOrder(MpOrderInfoEntity order, Pageable pageable);
  
  public void deleteByReSeq(Long reSeq);

  public MpReviewInfoEntity findByReSeq(Long reSeq);
}
