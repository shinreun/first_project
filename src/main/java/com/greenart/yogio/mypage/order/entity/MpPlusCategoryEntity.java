package com.greenart.yogio.mypage.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "plus_category")
public class MpPlusCategoryEntity {
  @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
  @Column(name = "pc_seq") private Long pcSeq;
  @Column(name = "pc_name") private String pcName;
  @Column(name = "pc_multi_choice") private Integer pcMultiChoice;
  @Column(name = "pc_essential_choice") private Integer pcEssentialChoice;
}
