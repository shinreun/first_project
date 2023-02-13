package com.greenart.yogio.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenart.yogio.admin.entity.StoreCateJoinEntity;


@Repository
public interface StoreCateJoinRepostiory extends JpaRepository <StoreCateJoinEntity, Long> {

    // void save(StoreCateJoinEntity sc);
    
}
