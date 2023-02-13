package com.greenart.yogio.mypage.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.greenart.yogio.mypage.order.entity.MpMypageMenuChoiceEntity;

@Repository
public interface MpMypageMenuChoiceRepository extends JpaRepository<MpMypageMenuChoiceEntity, Long>{
  List<MpMypageMenuChoiceEntity> findByMiSeq(Long miSeq);

  List<MpMypageMenuChoiceEntity> findByOiOrderNum(String oiOrderNum);

  List<MpMypageMenuChoiceEntity> findByOiSeq(Long oiSeq);

  @Query(value = "select a from MpMypageMenuChoiceEntity a where a.oiStatus = 2 and a.oiOrderNum like %:keyword% group by a.oiOrderNum")
  List<MpMypageMenuChoiceEntity> findBriefOrderList(@Param("keyword") String keyword);
  
  @Query(value = "select a from MpMypageMenuChoiceEntity a where (a.oiStatus = 2 or a.oiStatus = 1) and a.oiOrderNum like %:keyword% group by a.oiOrderNum")
  List<MpMypageMenuChoiceEntity> findOrderList(@Param("keyword") String keyword);
}
