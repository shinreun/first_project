package com.greenart.yogio.admin.service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.greenart.yogio.admin.entity.OwnerInfoEntity;
import com.greenart.yogio.admin.repository.OwnerInfoRepostitory;
import com.greenart.yogio.admin.vo.owner.OwnerAddVO;
import com.greenart.yogio.admin.vo.owner.OwnerInfoVO;
import com.greenart.yogio.admin.vo.owner.OwnerLoginVO;
import com.greenart.yogio.admin.vo.owner.OwnerUpdateVO;


@Service
public class OwnerInfoService {
    @Autowired OwnerInfoRepostitory ownerInfoRepostitory;
       public Map<String, Object> loginOwner(OwnerLoginVO login) {
        Map<String,Object> resultMap= new LinkedHashMap<String, Object>();
        OwnerInfoEntity entity =  ownerInfoRepostitory.findByOwiIdAndOwiPwd(login.getOwi_id(), login.getOwi_pwd());
        if (entity == null) {
            resultMap.put("status", false);
            resultMap.put("message","아이디 혹은 비밀번호 오류입니다.");
        }
        else if(entity.getOwiStatus() == 2 ) {
            resultMap.put("status", false);
            resultMap.put("message", "등록대기중인 계정입니다.");
        }
        else if(entity.getOwiStatus() == 3 ) {
            resultMap.put("status", false);
            resultMap.put("message", "이용정지된 계정입니다.");
        }
        else {
            resultMap.put("status", true);
            resultMap.put("message", "로그인되었습니다.");
            resultMap.put("login", new OwnerInfoVO(entity));
        }
        return resultMap;
}
 public Map<String, Object> addOwner(OwnerAddVO data) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();

        if (data.getOwiId() == null || data.getOwiId().equals("")) {
            map.put("status", false);
            map.put("message", "아이디를 입력하세요");
        }
        else if(ownerInfoRepostitory.countByOwiId(data.getOwiId()) !=0){
            map.put("status", false);
            map.put("message", data.getOwiId()+"은/는 이미 사용중입니다.");
        }
        else if (data.getOwiPwd() == null || data.getOwiPwd().equals("")) {
            map.put("status", false);
            map.put("message", "비밀번호를 입력하세요");
        }

           else if (data.getOwiNickName() == null || data.getOwiNickName().equals("")) {
            map.put("status", false);
            map.put("message", "이름을 입력하세요");
        }
           else if (data.getOwiEmail() == null || data.getOwiEmail().equals("")) {
            map.put("status", false);
            map.put("message", "이름을 입력하세요");
        }
        else {
            OwnerInfoEntity entity = OwnerInfoEntity.builder().owiId(data.getOwiId()).owiPwd(data.getOwiPwd()).
            owiNickName(data.getOwiNickName()).owiEmail(data.getOwiEmail()).owiPhone(data.getOwiPhone()).build();
            ownerInfoRepostitory.save(entity);
            map.put("status", true);
            map.put("message", "사장님 등록 신청 완료");
        }

        return map;
    }

    public Map<String,Object> getOwnerList(Pageable pageable){
        Page<OwnerInfoEntity> page= ownerInfoRepostitory.findAll(pageable);
        Map<String,Object> resultMap = new LinkedHashMap<String, Object>();
        resultMap.put("status", true);
        resultMap.put("list", page.getContent());
        resultMap.put("total", page.getTotalElements());
        resultMap.put("totalpage", page.getTotalPages());
        resultMap.put("currentPage", page.getNumber());
        resultMap.put("ownerList", page);
        return resultMap;
    }
      public void updateOwnerStatus(Integer value, Long owner_no) {
        OwnerInfoEntity entity = ownerInfoRepostitory.findById(owner_no).get();
        entity.setOwiStatus(value);
        ownerInfoRepostitory.save(entity);
    }
      public void deleteOwner (Long Owner_no) {
        ownerInfoRepostitory.deleteById(Owner_no);
    }
     public OwnerInfoEntity getOwnerInfo (Long Owner_no){
        return ownerInfoRepostitory.findById(Owner_no).get();
    }
     public Map<String, Object> updateOwnerInfo(OwnerUpdateVO data) {
        Map <String,Object> resultMap = new LinkedHashMap<String, Object>();
        Optional <OwnerInfoEntity> entity = ownerInfoRepostitory.findById(data.getSeq());
        String pattern = "^[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힣]*$"; // 특수문자 ,공백제외하고 허용(정규표현식)
 

        if(entity.isEmpty()) {
            resultMap.put("status", false);
            resultMap.put("message", "잘못된 사용자 번호가 입력되었습니다.");
        }
        else if (data.getPwd().length() > 16 || data.getPwd().length() < 8) {
            resultMap.put("status", false);
            resultMap.put("message", "비밀번호는  8 ~16자로 입력해주세요");
        }
        else if(data.getPwd().replaceAll(" ","").length() == 0 || !Pattern.matches(pattern,data.getPwd())) {
            resultMap.put("status", false);
            resultMap.put("message", "비밀번에 특수문자 또는 공백을 사용 할 수 없습니다.");  
        }
        else if (data.getNickname().replaceAll(" ","").length() == 0 || !Pattern.matches(pattern,data.getNickname())) {
            resultMap.put("status", false);
            resultMap.put("message", "관리자이름에 특수문자 또는 공백을 사용 할 수 없습니다.");  
        }
        else { 
            OwnerInfoEntity e  =  entity.get();
            e.setOwiPwd(data.getPwd());
            e.setOwiNickName(data.getNickname());
            e.setOwiEmail(data.getEmail());
            e.setOwiPhone(data.getPhone());
            ownerInfoRepostitory.save(e);
            resultMap.put("status", true);
        }
        return resultMap;
    }
}
