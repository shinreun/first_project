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
@Table(name = "owner_notice_img")
public class OwnerNotinceImgEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="oni_seq") private Long oniSeq;
    @Column(name="oni_sdi_seq") private Long oniSdiSeq;
    @Column(name="oni_img_path") private String oniImgPath;
    @Column(name="oni_img_seq") private Integer oniImgSeq;

    
}
