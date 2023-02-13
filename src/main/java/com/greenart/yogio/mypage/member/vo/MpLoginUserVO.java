package com.greenart.yogio.mypage.member.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MpLoginUserVO {
  private String id;
  private String pwd;
  // private Integer status;
}
