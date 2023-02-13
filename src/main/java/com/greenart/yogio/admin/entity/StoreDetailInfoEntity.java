package com.greenart.yogio.admin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "store_detail_info")
public class StoreDetailInfoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sdi_seq") private Long sdiSeq;
    @Column(name="sdi_si_seq") private Long sdiSiSeq;
    @Column(name="sdi_open_close") private String sdiOpenClose;
    @Column(name="sdi_phone") private String sdiPhone;
    @Column(name="sdi_adress") private String sdiAdress;
    @Column(name="sdi_bi_seq") private Long sdiBiSeq;
    @Column(name="sdi_payment") private Integer sdiPayment;
    @Column(name="sdi_packing") private Integer sdiPacking;
    @Column(name="sdi_origin") private String sdiOrigin;
    @Column(name="sdi_owner_notice") private String sdiOwnerNotice;
    
}
