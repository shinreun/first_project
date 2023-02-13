package com.greenart.yogio.mypage.member.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenart.yogio.mypage.member.entity.MbMemberInfoEntity;

@Repository
public interface MbMemberInfoRepository extends JpaRepository<MbMemberInfoEntity, Long>{
    public MbMemberInfoEntity findByMiIdAndMiPwd(String miId, String miPwd);
    public Page<MbMemberInfoEntity> findByMiIdContains(String keyword, Pageable pageable);
    public Integer countByMiId(String miId);
    public Integer countByMiEmail(String miEmail);
    public Integer countByMiPhone(String miPhone);
    public MbMemberInfoEntity findByMiId(String miId);
    public MbMemberInfoEntity findByMiSeq(Long miSeq);
    public MbMemberInfoEntity findTop1ByMiPwd(String miPwd);
    public MbMemberInfoEntity findByMiPhone(String miPhone);
    public MbMemberInfoEntity findTop1ByMiIdAndMiPwd(String miId, String miPwd);
    public MbMemberInfoEntity findByMiIdAndMiPhone(String miId, String miPhone);
  
}
  