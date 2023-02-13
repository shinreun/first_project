package com.greenart.yogio.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenart.yogio.admin.entity.StoreCategoryEntity;

@Repository
public interface StoreCategoryRepository  extends JpaRepository <StoreCategoryEntity ,Long>{
    public StoreCategoryEntity findByScNameContains(String scName);
}
