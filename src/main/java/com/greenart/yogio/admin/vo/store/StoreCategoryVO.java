package com.greenart.yogio.admin.vo.store;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreCategoryVO {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scSeq;
    private String scName;
    private MultipartFile file;
}
