package com.greenart.yogio.mypage.order.entity;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Immutable
@Table(name = "mypage_option_choice")
public class MpMypageOptionChoiceEntity {
  @Id
  @Column(name = "pmc_seq") @JsonIgnore private Long pmc_seq;
  @Column (name = "oi_seq") @JsonIgnore private Long oiSeq;
  @Column (name = "pm_name") private String pmName;
  @Column (name = "pmc_amount") private Integer pmcAmount;
  @Column (name = "pm_price") private Integer pmPrice;
}
