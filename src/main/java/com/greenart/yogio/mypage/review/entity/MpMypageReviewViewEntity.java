package com.greenart.yogio.mypage.review.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.Immutable;

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
@Immutable
@Table (name = "mypage_review_view")
public class MpMypageReviewViewEntity implements Serializable {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column (name = "re_seq") private Long reSeq;
  @Column (name = "si_name") private String siName;
  @Column (name = "re_score") private Integer reScore;
  @Column (name = "re_taste_score") private Integer reTasteScore;
  @Column (name = "re_quantity_score") private Integer reQuantityScore;
  @Column (name = "re_delivery_score") private Integer reDeliveryScore;
  @Column (name = "re_reg_dt") private Date reRegDt;
  @Column (name = "re_content") private String reContent;
  @Column (name = "ro_content") private String roContent;
  @Column (name = "ro_reg_dt") private Date roRegDt;
  @Column (name = "si_uri") private String siUri;
  @Column (name = "mni_name") private String mniName;
  @Column (name = "mi_seq") private Long miSeq;
  @Column (name = "oi_order_num") private String oiOrderNum;
  @Column (name = "ri_name") private String riName;
  @Column (name = "ri_order") private Integer riOrder;
}
