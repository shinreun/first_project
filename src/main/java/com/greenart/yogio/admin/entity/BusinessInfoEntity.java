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
@Table(name = "business_info")
public class BusinessInfoEntity {
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY )
        @Column(name = "bi_seq") private Long biSeq;
        @Column(name = "bi_name") private String biName;
        @Column(name = "bi_owner") private String biOwner;
        @Column(name = "bi_business_number") private String biBusinessNumber;
    
}
