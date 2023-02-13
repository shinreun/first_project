package com.greenart.yogio.mypage.store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenart.yogio.mypage.store.entity.MpMenuInfoEntity;

@Repository
public interface MpMenuInfoRepository extends JpaRepository<MpMenuInfoEntity, Long> {
  MpMenuInfoEntity findByMniSeq(Long mniSeq);

  
}
