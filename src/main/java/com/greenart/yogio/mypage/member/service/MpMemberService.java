package com.greenart.yogio.mypage.member.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.greenart.yogio.mypage.member.entity.MbMemberInfoEntity;
import com.greenart.yogio.mypage.member.repository.MpMemberInfoRepository;
import com.greenart.yogio.mypage.member.vo.MpLoginUserDetailVO;
import com.greenart.yogio.mypage.member.vo.MpLoginUserVO;
import com.greenart.yogio.mypage.member.vo.MpUpdateMemberVO;
import com.greenart.yogio.mypage.uitls.AESAlgorithm;

@Service
public class MpMemberService {
  @Autowired MpMemberInfoRepository mRepo;
  
  // 회원정보 수정(비밀번호, 닉네임, 주소, 전화번호)
  public Map<String, Object> updateMember(MpUpdateMemberVO data, Long miSeq) throws Exception{
    Map<String, Object> map = new LinkedHashMap<String, Object>();
    MbMemberInfoEntity login = mRepo.findByMiSeq(miSeq);

    // miSeq의 loginUser 값을 login으로 저장
    // MbMemberInfoEntity login = (MbMemberInfoEntity) session.getAttribute("loginUser");
    // 로그인 데이터 없음 = 로그인 한 적이 없다.
    if (login == null) {
      map.put("status", false);
      map.put("message", "로그인 후 이용하실 수 있습니다.");
    }
    // 로그인이 된 경우
    else {
      // 새로운 비밀번호 입력을 하지 않는 경우 (비밀번호 변경을 하지 않는 경우)
      if (data.getNewPwd() == null) {
        // 로그인 된 아이디의 패스워드와 입력된 패스워드가 같다면
        if (AESAlgorithm.Decrypt(login.getMiPwd()).equals(data.getPwd())) {
          // null 값인 데이터 없는 경우
          if (data.getNickname() != null && data.getAddress() != null && data.getPhone() != null) {
            // 바로 입력된 데이터 저장
            MbMemberInfoEntity member = mRepo.findByMiId(login.getMiId());
            MbMemberInfoEntity modify = MbMemberInfoEntity.builder()
                .miId(member.getMiId()).miStatus(member.getMiStatus()).miPwd(member.getMiPwd())
                .miSeq(member.getMiSeq()).miAddress(data.getAddress()).miNickname(data.getNickname())
                .miEmail(member.getMiEmail()).miPhone(data.getPhone()).build();
            mRepo.save(modify);
            MpLoginUserDetailVO memberDetail = new MpLoginUserDetailVO(modify);
            map.put("status", true);
            map.put("message", "회원정보를 수정하였습니다.");
            map.put("Member", memberDetail);
          }
          // null 값인 데이터 있는 경우
          else {
            if (data.getNickname() == null) {
              map.put("message", "닉네임을 입력해주세요.");
            } else if (data.getAddress() == null) {
              map.put("message", "주소를 입력해주세요.");
            } else if (data.getPhone() == null) {
              map.put("message", "전화번호를 입력해주세요.");
            }
          }
        }
        // 로그인 된 비밀번호와 입력된 패스워드 불일치
        else {
          map.put("status", false);
          map.put("message", "비밀번호를 확인해주세요");
        }
      }
      // 새로운 비밀번호 입력되는 경우
      else {
        // 새로운 비밀번호와 현재 비밀 번호 일치
        if (AESAlgorithm.Decrypt(login.getMiPwd()).equals(data.getNewPwd())) {
          map.put("status", false);
          map.put("message", "현재 비밀번호와 일치하는 비밀번호로 변경할 수 없습니다.");
        }
        // 새로운 비밀번호와 현재 비밀 번호 일치 하지 않는 경우
        else {
          // 로그인된 비밀번호와 입력된 비밀번호 일치
          if (AESAlgorithm.Decrypt(login.getMiPwd()).equals(data.getPwd())) {
            // null 값인 데이터 없는 경우
            if (data.getNickname() != null && data.getAddress() != null && data.getPhone() != null) {
              // 바로 입력된 데이터 저장
              MbMemberInfoEntity member = mRepo.findByMiId(login .getMiId());
              MbMemberInfoEntity modify = MbMemberInfoEntity.builder()
                  .miId(member.getMiId()).miStatus(member.getMiStatus())
                  .miSeq(member.getMiSeq()).miAddress(data.getAddress()).miNickname(data.getNickname())
                  .miEmail(member.getMiEmail()).miPhone(data.getPhone()).miPwd(AESAlgorithm.Encrypt(data.getNewPwd())).build();
              mRepo.save(modify);
              MpLoginUserDetailVO memberDetail = new MpLoginUserDetailVO(modify);
              map.put("status", true);
              map.put("message", "회원정보를 수정하였습니다.");
              map.put("Member", memberDetail);
            }
            // null 값인 데이터 있는 경우
            else {
              if (data.getNickname() == null) {
                map.put("message", "닉네임을 입력해주세요.");
              } else if (data.getAddress() == null) {
                map.put("message", "주소를 입력해주세요.");
              } else if (data.getPhone() == null) {
                map.put("message", "전화번호를 입력해주세요.");
              }
            }
          }
          // 로그인 된 아이디의 패스워드와 입력된 패스워드 불일치
          else {
            map.put("status", false);
            map.put("message", "비밀번호를 확인해주세요");
          }
        }
      }
    }
    return map;
  }

  // 로그인 서비스
  public Map<String, Object> loginMember(MpLoginUserVO data) {
    Map<String, Object> map = new LinkedHashMap<String, Object>();
    MbMemberInfoEntity loginUser = null;
    try {
      loginUser = mRepo.findByMiIdAndMiPwd(data.getId(), AESAlgorithm.Encrypt(data.getPwd()));
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (loginUser == null) {
      map.put("status", false);
      map.put("message", "아이디 또는 비밀번호 오류입니다.");
      map.put("code", HttpStatus.BAD_REQUEST);
    }
    else if (loginUser.getMiStatus() == 1) {
      map.put("status", false);
      map.put("message", "이용 정지된 회원입니다.");
      map.put("code", HttpStatus.BAD_REQUEST);
    }
    else {
      map.put("status", true);
      map.put("message", "로그인 되었습니다.");
      map.put("code", HttpStatus.ACCEPTED);
      map.put("loginUser", loginUser);
    }
    return map;
  }
  
  // 마이페이지 정보 출력
  public Map<String, Object> showMemberInfo(Long miSeq) {
    Map<String, Object> map = new LinkedHashMap<String, Object>();
    // MbMemberInfoEntity loginMember = (MbMemberInfoEntity) session.getAttribute("loginUser");
    MbMemberInfoEntity loginMember = mRepo.findByMiSeq(miSeq);
    if (loginMember == null) {
      map.put("status", false);
      map.put("message", "로그인 후 이용가능한 서비스입니다.");
    }
    else {
      MpLoginUserDetailVO memberDetail = new MpLoginUserDetailVO(loginMember);
      map.put("status", true);
      map.put("LoginMember", memberDetail);
    }
    return map;
  }
}
