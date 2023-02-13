package com.greenart.yogio.mypage.order.entity;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "mypage_order_price_by_oi_seq")
public class MpMypageOrderPriceByOiSeqEntity implements Serializable {
  @Id
  @Column (name = "oi_seq") @JsonIgnore private Long oiSeq;
  @Column (name = "oi_order_num") @JsonIgnore private String oiOrder_Num;
  @Column (name = "total_menu_price") @JsonIgnore private Integer totalMenuPrice;
  @Column (name = "total_option_price") @JsonIgnore private Integer totalOptionPrice;
  @Column (name = "oi_status") @JsonIgnore private Integer oiStatus;
  @Column (name = "oi_order_dt") @JsonIgnore private Date oiOrderDt;
  @Column (name = "oi_finish_dt") @JsonIgnore private Date oiFinishDt;
  @Column (name = "oi_seq_price") private Integer oiSeqPrice;
}
