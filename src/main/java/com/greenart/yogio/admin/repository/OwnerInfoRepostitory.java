package com.greenart.yogio.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenart.yogio.admin.entity.OwnerInfoEntity;

public interface OwnerInfoRepostitory extends JpaRepository<OwnerInfoEntity,Long>{
    public OwnerInfoEntity findByOwiIdAndOwiPwd(String owiId, String owiPwd);
    public Integer countByOwiId(String owiId);
    
}
