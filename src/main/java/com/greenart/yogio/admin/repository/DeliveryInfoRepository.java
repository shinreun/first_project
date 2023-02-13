package com.greenart.yogio.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenart.yogio.admin.entity.DeliveryInfoEntity;
@Repository
public interface DeliveryInfoRepository extends JpaRepository<DeliveryInfoEntity,Long> {
    
}
