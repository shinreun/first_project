package com.greenart.yogio.mypage.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "plus_cate_join")
public class MpPlusCateJoinEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "pcj_seq") private Long pcjSeq;
  @OneToOne @JoinColumn(name = "pcj_pc_seq") MpPlusCategoryEntity plusCate;
  // @Column(name = "pcj_pc_seq") private Long pcjPcSeq;
  @OneToOne @JoinColumn(name = "pcj_pm_seq") MpPlusMenuEntity plusMenu;
  // @Column(name = "pcj_pm_seq") private Long pcjPmSeq;
}
