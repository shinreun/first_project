package com.greenart.yogio.mypage.store.entity;

import java.io.Serializable;

import org.hibernate.annotations.Immutable;

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
@Table(name = "mypage_store_likes_view")
public class MpMypageStoreLikesViewEntity implements Serializable {
  @Id
  @Column (name = "si_seq") private Long siSeq;
  @Column (name = "si_name") private String siName;
  @Column (name = "si_uri") private String siUri;
  @Column(name = "reviewcnt") private Integer reviewCnt;
  @Column(name = "average") private Double average;
  @Column(name = "owner_review_cnt") private Integer ownerReviewCnt;
  @Column (name = "si_clean_info") private Integer siCleanInfo;
  @Column (name = "si_min_order_price") private Integer siMinOrderPrice;
  @Column (name = "di_time") private String diTime;
  @Column (name = "sl_status") private Integer slStatus;
  @Column(name = "mi_seq") private Long miSeq;
}
