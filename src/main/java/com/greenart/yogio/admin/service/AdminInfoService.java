package com.greenart.yogio.admin.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.greenart.yogio.admin.entity.AdminInfoEntity;
import com.greenart.yogio.admin.entity.StoreCategoryEntity;
import com.greenart.yogio.admin.repository.AdminInfoRepository;
import com.greenart.yogio.admin.repository.StoreCategoryRepository;
import com.greenart.yogio.admin.vo.admin.AdminAddVO;
import com.greenart.yogio.admin.vo.admin.AdminLoginVO;
import com.greenart.yogio.admin.vo.admin.AdminUpateVO;
import com.greenart.yogio.admin.vo.admin.AdminVO;
import com.greenart.yogio.admin.vo.store.StoreCategoryVO;
import com.greenart.yogio.admin.vo.admin.AdminLoginVO;
import com.greenart.yogio.admin.vo.admin.AdminUpateVO;
import com.greenart.yogio.admin.vo.admin.AdminVO;

@Service
public class AdminInfoService {
    @Value("${file.image.cate}") String cate_img_path;
    @Autowired StoreCategoryRepository storeCategoryRepository;
    @Autowired AdminInfoRepository adminInfoRepository;
    public Map<String, Object> loginAdmin(AdminLoginVO login) {
        Map<String,Object> resultMap= new LinkedHashMap<String, Object>();
        AdminInfoEntity entity =  adminInfoRepository.findByAiIdAndAiPwd(login.getAi_id(), login.getAi_pwd());
        if (entity == null) {
            resultMap.put("status", false);
            resultMap.put("message","아이디 혹은 비밀번호 오류입니다.");
        }
        else if(entity.getAiStatus() == 2 ) {
            resultMap.put("status", false);
            resultMap.put("message", "등록대기중인 계정입니다.");
        }
        else if(entity.getAiStatus() == 3 ) {
            resultMap.put("status", false);
            resultMap.put("message", "이용정지된 계정입니다.");
        }
        else {
            resultMap.put("status", true);
            resultMap.put("message", "로그인되었습니다.");
            resultMap.put("login", new AdminVO(entity));
        }
        
        return resultMap;
}
 public Map<String, Object> addAdmin(AdminAddVO data) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();

        if (data.getId() == null || data.getId().equals("")) {
            map.put("status", false);
            map.put("message", "아이디를 입력하세요");
        }
        else if(adminInfoRepository.countByAiId(data.getId()) !=0){
            map.put("status", false);
            map.put("message", data.getId()+"은/는 이미 사용중입니다.");
        }
        else if (data.getPwd() == null || data.getPwd().equals("")) {
            map.put("status", false);
            map.put("message", "비밀번호를 입력하세요");
        }
        else if (data.getName() == null || data.getName().equals("")) {
            map.put("status", false);
            map.put("message", "이름을 입력하세요");
        }
        else {
            AdminInfoEntity entity = AdminInfoEntity.builder().aiId(data.getId()).aiPwd(data.getPwd()).
            aiName(data.getName()).aiGrade(data.getType()).aiGrade(1).build();
            adminInfoRepository.save(entity);
            map.put("status", true);
            map.put("message", "관리자 계정 등록 신청 완료");
        }

        return map;
    }

    public Map<String,Object> getAdminList(Pageable pageable){
        Page<AdminInfoEntity> page= adminInfoRepository.findAll(pageable);
        Map<String,Object> resultMap = new LinkedHashMap<String, Object>();
        resultMap.put("status", true);
        resultMap.put("list", page.getContent());
        resultMap.put("total", page.getTotalElements());
        resultMap.put("totalpage", page.getTotalPages());
        resultMap.put("currentPage", page.getNumber());
        resultMap.put("adminList", page);
        return resultMap;
    }
      public void updateAdminStatus(Integer value, Long admin_no) {
        AdminInfoEntity entity = adminInfoRepository.findById(admin_no).get();
        entity.setAiStatus(value);
        adminInfoRepository.save(entity);
    }
      public void deleteAdmin (Long admin_no) {
        adminInfoRepository.deleteById(admin_no);
    }
     public AdminInfoEntity getAdminInfo (Long admin_no){
        return adminInfoRepository.findById(admin_no).get();
    }
     public Map<String, Object> updateAdminInfo(AdminUpateVO data) {
        Map <String,Object> resultMap = new LinkedHashMap<String, Object>();
        Optional <AdminInfoEntity> entity = adminInfoRepository.findById(data.getSeq());
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
        else if (data.getName().replaceAll(" ","").length() == 0 || !Pattern.matches(pattern,data.getName())) {
            resultMap.put("status", false);
            resultMap.put("message", "관리자이름에 특수문자 또는 공백을 사용 할 수 없습니다.");  
        }
        else { 
            AdminInfoEntity e  =  entity.get();
            e.setAiPwd(data.getPwd());
            e.setAiName(data.getName());
            adminInfoRepository.save(e);
            resultMap.put("status", true);
        }
        return resultMap;
    }
}
