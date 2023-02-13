// package com.greenart.yogio.admin.api;

// import java.util.LinkedHashMap;
// import java.util.Map;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.greenart.yogio.admin.entity.OwnerInfoEntity;
// import com.greenart.yogio.admin.repository.OwnerInfoRepostitory;
// import com.greenart.yogio.admin.service.OwnerInfoService;
// import com.greenart.yogio.admin.vo.owner.OwnerLoginVO;

// import lombok.RequiredArgsConstructor;

// @RestController
// @RequestMapping("/owner")
// @RequiredArgsConstructor
// public class OwnerInfoAPIController {
//     private final OwnerInfoRepostitory ownerInfoRepostitory;
//     // @Autowired OwnerInfoService ownerInfoService;
//     // @Autowired OwnerInfoRepostitory ownerInfoRepostitory;

//     @PostMapping("/login")
//     public Map <String,Object> postOwnerLogin(@RequestBody OwnerLoginVO login){
//         Map<String, Object> resultmap =  new LinkedHashMap<String, Object>();
//         OwnerInfoEntity owner = ownerInfoRepostitory.findByOwiIdAndOwiPwd(login.getOwi_id(), login.getOwi_pwd());
//         System.out.println(owner);
//         if (owner == null) {
//             resultmap.put("status", false);
//             resultmap.put("message", "잘못된 로그인 정보입니다.");
//         }
//         else {
//             resultmap.put("status", true);
//             resultmap.put("login", owner);
//         }
//         return resultmap;  
//     }
    
// }
