package com.greenart.yogio.mypage.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenart.yogio.mypage.member.entity.MbMemberInfoEntity;
import com.greenart.yogio.mypage.member.entity.MpStoreLikesEntity;
import com.greenart.yogio.mypage.store.entity.MpStoreInfoEntity;

@Repository
public interface MpStoreLikesRepository extends JpaRepository<MpStoreLikesEntity, Long>{
  public MpStoreLikesEntity findByStore(MpStoreInfoEntity store);
  
  public MpStoreLikesEntity findByMember(MbMemberInfoEntity member);

  public Long findSiSeqByMember(Long slMiSeq);
}
