package com.greenart.yogio.mypage.member.entity;

import org.hibernate.annotations.DynamicInsert;

import com.greenart.yogio.mypage.member.entity.MbMemberInfoEntity;
import com.greenart.yogio.mypage.store.entity.MpStoreInfoEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "store_likes")
@DynamicInsert
@Builder
public class MpStoreLikesEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "sl_seq")    private Long slSeq;
  @ManyToOne @JoinColumn(name = "sl_mi_seq") MbMemberInfoEntity member;
  // @Column(name = "sl_mi_seq") private Long slMiSeq;
  @ManyToOne @JoinColumn(name = "sl_si_seq") MpStoreInfoEntity store;
  // @Column(name = "sl_si_seq") private Long slSiSeq;
  @Column(name = "sl_status") private Integer slStatus;
  
}
