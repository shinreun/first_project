package com.greenart.yogio.mypage.store.entity;

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
@Table(name = "store_category")
public class MpStoreCategoryEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column (name = "sc_seq") private Long scSeq;
  @Column(name = "sc_name") private String scName;
  @Column(name = "sc_image") private String scImage;
}
