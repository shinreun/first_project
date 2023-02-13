package com.greenart.yogio.mypage.store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "menu_cate_join")
public class MpMenuCateJoinEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "mcj_seq") private Long mcjSeq;
  @ManyToOne @JoinColumn (name = "mcj_mc_seq") MpMenuCategoryEntity menuCate;
  // @Column (name = "mcj_mc_seq") private Long mcjMcSeq;
  @ManyToOne @JoinColumn (name = "mcj_mni_seq") MpMenuInfoEntity menu;
  // @Column (name = "mcj_mni_seq") private Long mcjMniSeq;
}
