package com.greenart.yogio.mypage.store.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenart.yogio.mypage.store.entity.MpMypageStoreLikesViewEntity;

@Repository
public interface MpMypageStoreLikesViewRepository extends JpaRepository<MpMypageStoreLikesViewEntity, Long>{
  Page<MpMypageStoreLikesViewEntity> findByMiSeq (Long miSeq, Pageable pageable);
}
