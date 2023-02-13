package com.greenart.yogio.mypage.order.entity;

import java.io.Serializable;

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
@Table(name = "mypage_menu_choice")
public class MpMypageMenuChoiceEntity implements Serializable{
  @Id
  @Column (name = "oi_seq") private Long oiSeq;
  @Column (name = "oi_order_num") private String oiOrderNum;
  // @ManyToOne @JoinColumn(name = "oi_mi_seq") MpMemberInfoEntity member;
  @Column (name = "mi_seq") @JsonIgnore private Long miSeq;
  @Column (name = "mni_name") private String mniName;
  @Column (name = "menu_amount") private Integer menuAmount;
  @Column (name = "menu_price") private Integer menuPrice;
  @Column (name = "mc_seq") @JsonIgnore private Long mcSeq;
  @Column (name = "oi_status") @JsonIgnore private Integer oiStatus;
}
