package com.greenart.yogio.mypage.order.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greenart.yogio.mypage.order.entity.MpMypageOptionChoiceEntity;

@Repository
public interface MpMypageOptionChoiceRepository extends JpaRepository<MpMypageOptionChoiceEntity, Long> {
  List<MpMypageOptionChoiceEntity> findByOiSeq(Long oiSeq);
}