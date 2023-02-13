package com.greenart.yogio.mypage.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenart.yogio.mypage.order.entity.MpMypageOrderPriceByOrderNumEntity;


@Repository
public interface MpMypageOrderPriceByOrderNumRepository extends JpaRepository<MpMypageOrderPriceByOrderNumEntity, String> {
    MpMypageOrderPriceByOrderNumEntity findByOiOrderNum (String oiOrderNum);
}
