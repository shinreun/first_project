package com.greenart.yogio.mypage.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenart.yogio.mypage.order.entity.MpMypageOrderPriceByOiSeqEntity;

@Repository
public interface MpMypageOrderPriceByOiSeqRepository extends JpaRepository<MpMypageOrderPriceByOiSeqEntity, Long>{ 
  MpMypageOrderPriceByOiSeqEntity findByOiSeq(Long oiSeq);
}
