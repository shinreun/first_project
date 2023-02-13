package com.greenart.yogio.mypage.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenart.yogio.mypage.member.entity.MbMemberInfoEntity;

@Repository
public interface MpMemberInfoRepository extends JpaRepository<MbMemberInfoEntity, Long>{
  MbMemberInfoEntity findByMiIdAndMiPwd(String miId, String miPwd);

  MbMemberInfoEntity findByMiSeq(Long miSeq);

  MbMemberInfoEntity findByMiId(String miId);
  



  
}
