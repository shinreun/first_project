package com.greenart.yogio.mypage.review.entity;

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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Immutable
@Table(name = "store_review_count_view")
public class MpStoreReviewCountViewEntity implements Serializable {
  @Id
  @Column(name = "reviewcnt") private Integer reviewcnt;
  @Column(name = "average") private Double average;
  @Column(name = "si_seq") private Long siSeq;
}
