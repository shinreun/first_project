package com.greenart.yogio.admin.vo.store;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreInfoVO {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long    siSeq;
   private MultipartFile file;
   private Integer siMinOrderPrice;
   private Integer siDiscountPrice;
   private String  siDiscountCondition;
   private Long    siDiSeq;
   private Integer siCleanInfo;
   private String  siName;
   private Integer diDistance;
   private Integer diDeliveryPrice;
   private String  diTime;
   private Long    owiSiSeq;
   private String   scName;
   

    
}
