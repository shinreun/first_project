package com.greenart.yogio.mypage.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenart.yogio.mypage.order.entity.MpPlusCateJoinEntity;

@Repository
public interface MpPlusCateJoinRepository extends JpaRepository<MpPlusCateJoinEntity, Long>{
  
}
