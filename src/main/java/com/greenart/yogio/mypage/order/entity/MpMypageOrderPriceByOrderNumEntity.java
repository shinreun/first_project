package com.greenart.yogio.mypage.order.entity;

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
@Table(name = "mypage_order_price_by_order_num")
public class MpMypageOrderPriceByOrderNumEntity implements Serializable {
    @Id
    @Column (name = "oi_order_num") private String oiOrderNum;
    @Column (name = "total_menu_price") private Integer totalMenuPrice;
    @Column (name = "total_option_price") private Integer totalOptionPrice;
}
