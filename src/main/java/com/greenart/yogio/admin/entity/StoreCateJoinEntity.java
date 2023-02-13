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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "store_cate_join")
@Builder
public class StoreCateJoinEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scs_seq") private Long scsSeq;
    @Column(name = "scs_sc_seq") private Long scsScSeq;
    @Column(name = "scs_si_seq") private Long scsSiSeq;
    
}
