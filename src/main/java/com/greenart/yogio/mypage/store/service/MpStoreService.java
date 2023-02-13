package com.greenart.yogio.mypage.store.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.greenart.yogio.mypage.member.entity.MbMemberInfoEntity;
import com.greenart.yogio.mypage.member.repository.MpMemberInfoRepository;
import com.greenart.yogio.mypage.member.repository.MpStoreLikesRepository;
import com.greenart.yogio.mypage.store.entity.MpMypageStoreLikesViewEntity;
import com.greenart.yogio.mypage.store.entity.MpStoreCategoryEntity;
import com.greenart.yogio.mypage.store.repository.MpMypageStoreLikesViewRepository;
import com.greenart.yogio.mypage.store.repository.MpStoreCategoryRepository;


@Service
public class MpStoreService {
  @Autowired MpStoreLikesRepository sLikeRepo;
  @Autowired MpMypageStoreLikesViewRepository sLikeViewRepo;
  @Autowired MpStoreCategoryRepository storeCateRepo;
  @Autowired MpMemberInfoRepository memberRepo;
  
  // 찜한 가게 목록 출력하는 메서드
  public Map<String, Object> showLikedStore(Long miSeq, Pageable pageable) {
    Map<String, Object> map = new LinkedHashMap<String, Object>();
    // MbMemberInfoEntity login = (MbMemberInfoEntity) session.getAttribute("loginUser");
    MbMemberInfoEntity login = memberRepo.findByMiSeq(miSeq);
    if (login == null) {
      map.put("stauts", false);
      map.put("message", "로그인 후 이용하실 수 있습니다.");
    } 
    else {
      // 멤버 seq로 찜한 가게 목록 들고오기
      Page<MpMypageStoreLikesViewEntity> store = sLikeViewRepo.findByMiSeq(login.getMiSeq(), pageable);
      // 찜한 가게가 없다면, 메세지 출력
      if (store.isEmpty()) {
        map.put("status", false);
        map.put("message", "찜한 가게가 없습니다.");
      }
      // 만약 좋아요 테이블의 멤버와 로그인한 멤버가 같다면 
      // 좋아요 테이블의 status값을 체크하고,
      // 좋아요 테이블의 si_seq를 가진 가게 정보 출력
      else {
        map.put("status", true);
        map.put("likedStore", store.getContent());
        map.put("storeCnt", store.getTotalElements());
        map.put("totalPage", store.getTotalPages());
        map.put("currentPage", store.getNumber());
      }
    }
    return map;
  }
  
  public Map<String, Object> showStoreCate (Pageable pageable) {
    Map<String, Object> map = new LinkedHashMap<String, Object>();
    Page<MpStoreCategoryEntity> page = storeCateRepo.findAll(pageable);
    map.put("storeCate", page.getContent());
    return map;
  }
}
