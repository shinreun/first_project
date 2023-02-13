package com.greenart.yogio.mypage.member.vo;

import lombok.Data;

@Data
public class MpUpdateMemberVO {
  private String id;
  private String pwd;
  private String newPwd;
  private String phone;
  private String nickname;
  private String address;
}
