package com.greenart.yogio.admin.raeeun.vo;

import com.greenart.yogio.mypage.order.entity.MpMypageOptionChoiceEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OptionVO {
  private Long oiSeq;
  private String pmName;
  private Integer pmcAmount;
  private Integer pmPrice;

  public OptionVO(MpMypageOptionChoiceEntity data) {
    this.oiSeq = data.getOiSeq();
    this.pmName = data.getPmName();
    this.pmcAmount = data.getPmcAmount();
    this.pmPrice = data.getPmPrice();
  }
}

