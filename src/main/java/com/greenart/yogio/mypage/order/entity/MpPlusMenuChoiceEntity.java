package com.greenart.yogio.mypage.order.entity;

import org.hibernate.annotations.ColumnDefault;

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
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "plus_menu_choice")
public class MpPlusMenuChoiceEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column (name = "pmc_seq") private Long pmcSeq;
  @ManyToOne @JoinColumn(name = "pmc_pm_seq") MpPlusMenuEntity plusMenu;
  // @Column (name = "pmc_pm_seq") private Long pmcPmSeq;
  @Column(name = "pmc_amount") @ColumnDefault("1") private Integer pmcAmount;
  @ManyToOne @JoinColumn(name = "pmc_oi_seq") MpOrderInfoEntity order;
  // @Column (name = "pmc_oi_seq") private Long pmcOiSeq;
}
