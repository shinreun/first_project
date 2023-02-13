package com.greenart.yogio.mypage.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenart.yogio.mypage.store.entity.MpMenuCateJoinEntity;
import com.greenart.yogio.mypage.store.entity.MpMenuCategoryEntity;
import com.greenart.yogio.mypage.store.entity.MpMenuInfoEntity;

@Repository
public interface MpMenuCateJoinRepository extends JpaRepository<MpMenuCateJoinEntity, Long> {
  // 메뉴 카테고리 정보로 메뉴 카테고리 조인 정보 찾기
  MpMenuCateJoinEntity findByMenuCate(MpMenuCategoryEntity menuCate);

  // 메뉴 정보로 메뉴 카테고리 조인 정보 찾기
  MpMenuCateJoinEntity findByMenu (MpMenuInfoEntity menu);
}
