package com.greenart.yogio.mypage.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenart.yogio.mypage.member.entity.MbMemberInfoEntity;
import com.greenart.yogio.mypage.order.entity.MpOrderInfoEntity;
import com.greenart.yogio.mypage.store.entity.MpMenuInfoEntity;

@Repository
public interface MpOrderInfoRepository extends JpaRepository<MpOrderInfoEntity, Long>{
  MpOrderInfoEntity findByMember(MbMemberInfoEntity member);

  List<MpOrderInfoEntity> findAllByMember(MbMemberInfoEntity member);

  MpOrderInfoEntity findByMenu(MpMenuInfoEntity menu);

  MpOrderInfoEntity findByOiSeq(Long oiSeq);
  
  List<MpOrderInfoEntity> findAllByOiOrderNum(String orderNum);

}
