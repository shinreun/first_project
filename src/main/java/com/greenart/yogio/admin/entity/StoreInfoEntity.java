package com.greenart.yogio.admin.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "store_info")
@DynamicInsert
@Builder
public class StoreInfoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "si_seq") private Long siSeq;
    @Column(name = "si_name") private String siName;
    @Column(name = "si_uri") private String siUri;
    @Column(name = "si_min_order_price") private Integer siMinOrderPrice;
    @Column(name = "si_discount_price") private Integer siDiscountPrice;
    @Column(name = "si_discount_condition") private String siDiscountCondition;
    @Column(name = "si_di_seq") private Long siDiSeq;
    // @OneToOne @JoinColumn(name="si_di_seq") DeliveryInfoEntity delivery;
    @Column(name = "si_clean_info") @ColumnDefault("1") private Integer siCleanInfo;
    @Column(name = "si_file_name") @JsonIgnore private String siFileName;

    // public StoreInfoEntity(StoreInfoEntity data){
    //     this.siSeq = data.getSiSeq();
    //     this.siName = data.getSiName();
    //     this.siUri = data.getSiUri();
    //     this.siMinOrderPrice = data.getSiMinOrderPrice();
    //     this.siDiscountPrice = data.getSiDiscountPrice();
    //     this.siDiscountCondition = data.getSiDiscountCondition();
    //     this.siCleanInfo = data.getSiCleanInfo();
    //     this.siFileName = data.getSiFileName();
    //     this.delivery = data.getDelivery();

    // }
}

