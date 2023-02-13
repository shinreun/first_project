package com.greenart.yogio.mypage.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenart.yogio.mypage.order.entity.MpPlusMenuEntity;

@Repository
public interface MpPlusMenuRepository extends JpaRepository<MpPlusMenuEntity, Long> {
  MpPlusMenuEntity findByPmSeq(Long pmSeq);
}
