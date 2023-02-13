package com.greenart.yogio.admin.raeeun.vo;

import java.time.LocalDate;
import java.util.Date;

import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
public class OrderAddVO {
  private Long oiSeq;
  private Long oiMniSeq;
  private Long oiMiSeq;  
  private Integer oiMenuAmount;  
  private Integer oiStatus;  
  private String oiOrderNum;  
  private LocalDate oiOrderDt;  
  private LocalDate oiFinishDt;
  private Long pmcSeq;
  private Long pmcPmSeq;
  private Integer pmcAmount;
  private Long pmcOiSeq;
}
