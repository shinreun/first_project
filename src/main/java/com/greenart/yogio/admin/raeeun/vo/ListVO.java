package com.greenart.yogio.admin.raeeun.vo;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greenart.yogio.mypage.order.entity.MpMypageMenuChoiceEntity;
import com.greenart.yogio.mypage.order.entity.MpMypageOptionChoiceEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListVO {
  private Long oiSeq;
  private String oiOrderNum;
  private @JsonIgnore Long miSeq;
  private String mniName;
  private Integer menuAmount;
  private Integer menuPrice;
  private @JsonIgnore Long mcSeq;
  private Integer oiStatus;
  private List<OptionVO> optionList;
  
  public ListVO (MpMypageMenuChoiceEntity menu) {
    this.oiSeq = menu.getOiSeq(); 
    this.oiOrderNum = menu.getOiOrderNum(); 
    this.miSeq = menu.getMiSeq();
    this.mniName = menu.getOiOrderNum();
    this.menuAmount = menu.getMenuAmount();
    this.menuPrice = menu.getMenuPrice();
    this.mcSeq = menu.getMcSeq(); 
    this.oiStatus = menu.getOiStatus(); 
  }
}


