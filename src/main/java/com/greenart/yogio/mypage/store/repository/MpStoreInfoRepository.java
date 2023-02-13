package com.greenart.yogio.mypage.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenart.yogio.mypage.store.entity.MpStoreInfoEntity;

@Repository
public interface MpStoreInfoRepository extends JpaRepository<MpStoreInfoEntity, Long>{
  public MpStoreInfoEntity findBySiSeq(Long siSeq);
}
