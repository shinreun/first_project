package com.greenart.yogio.mypage.store.entity;

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
@Table(name = "delivery_info")
public class MpDeliveryEntity {
  @Id
  @Column(name = "di_seq") private Long diSeq;
  @Column(name = "di_distance") private Integer diDistance;
  @Column(name = "di_delivery_price") private Integer diDieliveryPrice;
  @Column(name = "di_time") private String diTime;
}
