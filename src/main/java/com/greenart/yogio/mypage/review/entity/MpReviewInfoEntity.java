package com.greenart.yogio.mypage.review.entity;

import java.time.LocalDate;
import java.util.Date;

import org.hibernate.annotations.ColumnDefault;

import com.greenart.yogio.mypage.order.entity.MpOrderInfoEntity;

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
@Table(name = "review_info")
public class MpReviewInfoEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "re_seq") private Long reSeq;
  @Column(name = "re_reg_dt") @ColumnDefault("current_timestamp") private Date reRegDt;
  @Column(name = "re_score") @ColumnDefault("5") private Integer reScore;
  @Column(name = "re_content") private String reContent;
  @Column(name = "re_taste_score") @ColumnDefault("5") private Integer reTasteScore;
  @Column(name = "re_quantity_score") @ColumnDefault("5") private Integer reQuantityScore;
  @Column(name = "re_delivery_score") @ColumnDefault("5") private Integer reDeliveryScore;
  @ManyToOne @JoinColumn(name = "re_oi_seq") MpOrderInfoEntity order;
  // @Column(name = "re_oi_seq") private Long reOiSeq;
}
