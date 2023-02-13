package com.greenart.yogio.mypage.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenart.yogio.mypage.order.entity.MpOrderInfoEntity;
import com.greenart.yogio.mypage.order.entity.MpPlusMenuChoiceEntity;

@Repository
public interface MpPlusMenuChoiceRepository extends JpaRepository<MpPlusMenuChoiceEntity, Long> {
  List<MpPlusMenuChoiceEntity> findByOrder(MpOrderInfoEntity order);

  MpPlusMenuChoiceEntity findByPmcSeq(Long pmcSeq);

}
