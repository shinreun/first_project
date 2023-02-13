package com.greenart.yogio.mypage.order.vo;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greenart.yogio.mypage.order.entity.MpMypageMenuChoiceEntity;
import com.greenart.yogio.mypage.order.entity.MpMypageOptionChoiceEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MpWishListVO {
  private Long oiSeq;
  @JsonIgnore private String oiOrderNum;
  private String mniName;
  private Integer menuPrice;
  private Integer menuAmount;
  private List<MpMypageOptionChoiceEntity> option;
  private Integer menuOrderPrice;

  public MpWishListVO(MpMypageMenuChoiceEntity menu) {
    this.oiSeq = menu.getOiSeq();
    this.oiOrderNum = menu.getOiOrderNum();
    this.mniName = menu.getMniName();
    this.menuPrice = menu.getMenuPrice();
    this.menuAmount = menu.getMenuAmount();
  }
}
