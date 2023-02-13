package com.greenart.yogio.admin.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenart.yogio.admin.entity.OwnerInfoEntity;
import com.greenart.yogio.admin.entity.StoreInfoEntity;
import com.greenart.yogio.admin.service.OwnerInfoService;
import com.greenart.yogio.admin.service.StoreCategoryService;
import com.greenart.yogio.admin.service.StoreInfoService;
import com.greenart.yogio.admin.vo.admin.AdminVO;
import com.greenart.yogio.admin.vo.owner.OwnerInfoVO;
import com.greenart.yogio.admin.vo.owner.OwnerLoginVO;
import com.greenart.yogio.admin.vo.store.StoreDetailInfoVO;
import com.greenart.yogio.admin.vo.store.StoreInfoVO;

import io.micrometer.common.lang.Nullable;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/store")
public class StoreInfoController {
    @Autowired StoreCategoryService storeCategoryService;
    @Autowired StoreInfoService storeInfoService;
    @Autowired OwnerInfoService ownerInfoService;
      @GetMapping("/add")
    public String getAdminAdd(Model model, HttpSession session, Pageable pageable) {
        // Map<String,Object> map = new LinkedHashMap<String, Object>();
        model.addAttribute("result", storeCategoryService.getCateList(pageable));
        return "/store/add";
    }
    @PostMapping("/add")
    public String postAddAdmin(StoreInfoVO data, Model model, HttpSession session,Pageable pageable) {
        model.addAttribute("result", storeCategoryService.getCateList(pageable));
       Map<String,Object> map = storeInfoService.addStore(data, session);
       if((boolean)map.get("status")) {
        return "redirect:/";
       }
       model.addAttribute("inputdata",data);
       model.addAttribute("message",map.get("message"));
        return "/store/add";
        }
      @GetMapping("/list/admin")
    public String getOwnerDetail (@RequestParam Long store_no, Model model,HttpSession session) {
        StoreInfoEntity map = storeInfoService.getStoreInfo(store_no);
        model.addAttribute("store_detail", storeInfoService.getStoreInfo(store_no));
        model.addAttribute("delivery_detail", storeInfoService.getDeliveryInfo(map.getSiDiSeq()));
        OwnerInfoVO store = (OwnerInfoVO)session.getAttribute("loginUser");
        if(store == null) { // 로그인 상태가 아니라면
            return "redirect:/"; // 로그인 페이지로
        }
        else{
        return "/store/list";
        }
    }
    @GetMapping("/update")
    public String getUpdate (@RequestParam Long store_no,HttpSession session, Model model){
          model.addAttribute("store_detail", storeInfoService.getStoreInfo(store_no));
        OwnerInfoVO owner = (OwnerInfoVO)session.getAttribute("loginUser");
        if(owner == null) { // 로그인 상태가 아니라면
            return "redirect:/"; // 로그인 페이지로
        }
        else{
        return "/store/update";
        }
    }
     @PostMapping("/update")
    public String postUpdate (StoreInfoVO data, HttpSession session, Model model){
       model.addAttribute("store_detail", storeInfoService.updateStoreInfo(data, session));
       model.addAttribute("inputdata",data);
        // OwnerInfoVO owner = (OwnerInfoVO)session.getAttribute("loginUser");
        Map <String,Object> store = storeInfoService.updateStoreInfo(data,session);
      // model.addAttribute("store_update", map);
        // if((boolean) map.get("status")) {
        //     return "redirect:/";
        // }
        session.setAttribute("store_update", store);
        return "redirect:/";
    }   
        @GetMapping("/all/list")
    public String getStoreList (Model model,Pageable pageable) {
        model.addAttribute("result", storeInfoService.getStoreList(pageable));
        return "/store/allList";
    }
    // ,@RequestParam Integer page, @RequestParam @Nullable String keyword,
    @GetMapping ("/delete") 
    public String getStoreDelete(@RequestParam Long store_no,HttpSession session) {
        AdminVO admin = (AdminVO)session.getAttribute("loginUser");
        if(admin == null) { // 로그인 상태가 아니라면
            return "redirect:/"; // 로그인 페이지로
        }
        else if (admin.getAi_grade() != 99) { // 로그인 했는데 마스터가 아니라면
            return "redirect:/main"; // 메인페이지로
        }
        storeInfoService.deleteStore(store_no);
        return "redirect:/store/all/list";
    }
     @GetMapping("/detail/add")
    public String getAdminAdd() {
        return "/store/detail";
    }
     @PostMapping("/detail/add")
    public String postAddAdmin(StoreDetailInfoVO data, Model model, HttpSession session,Pageable pageable) {
        // model.addAttribute("result", storeCategoryService.getCateList(pageable));
        OwnerInfoVO owner = (OwnerInfoVO)session.getAttribute("loginUser");
       Map<String,Object> map = storeInfoService.addStoreDetail(data, session);
        if (owner.getOwiSiSeq() == null) {
            return "redirect:/";
        }
    
       model.addAttribute("inputdata",data);
       model.addAttribute("message",map.get("message"));
        return "redirect:/omain";
        }
}
