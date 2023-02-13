package com.greenart.yogio.admin.vo.store;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreDetailInfoVO {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sdiSeq;
    private String sdiOpenClose;  
    private String sdiPhone; 
    private String sdiAdress; 
    private Integer sdiPayment; 
    private Integer sdiPacking; 
    private String sdiOrigin; 
    private String sdiOwnerNotice; 
    private String biName;
    private String biOwner; 
    private String biBusinessNumber;
    private MultipartFile file;
    
}
