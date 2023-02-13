package com.greenart.yogio.mypage.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenart.yogio.mypage.store.entity.MpMenuCategoryEntity;
import com.greenart.yogio.mypage.store.entity.MpStoreInfoEntity;

@Repository
public interface MpMenuCategoryRepository extends JpaRepository<MpMenuCategoryEntity, Long> {
  // 가게 정보로 카테고리 정보 찾기
  MpMenuCategoryEntity findByStore(MpStoreInfoEntity store);

  MpMenuCategoryEntity findByMcSeq(Long mcSeq);
}
