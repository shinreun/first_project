package com.greenart.yogio.admin.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenart.yogio.admin.entity.OwnerInfoEntity;
import com.greenart.yogio.admin.repository.OwnerInfoRepostitory;
import com.greenart.yogio.admin.service.OwnerInfoService;
import com.greenart.yogio.admin.vo.admin.AdminVO;
import com.greenart.yogio.admin.vo.owner.OwnerAddVO;
import com.greenart.yogio.admin.vo.owner.OwnerInfoVO;
import com.greenart.yogio.admin.vo.owner.OwnerLoginVO;
import com.greenart.yogio.admin.vo.owner.OwnerUpdateVO;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpSession;
@Controller
@RequestMapping("/owner")
public class OwnerController {
    @Autowired OwnerInfoService ownerInfoService;
    @Autowired OwnerInfoRepostitory ownerInfoRepostitory;
    @GetMapping("/list")
    public String getOwnerList (Model model,Pageable pageable) {
        model.addAttribute("result", ownerInfoService.getOwnerList(pageable));
        return "/owner/list";
    }
     @GetMapping("/add")
    public String getOwnerAdd() {
        return "/owner/add";
    }

  @PostMapping("/login")
    public String postOwnerLogin(OwnerLoginVO login , HttpSession session, Model model){
        Map<String,Object> map = ownerInfoService.loginOwner(login);
         if((boolean)map.get("status")) {
            session.setAttribute("loginUser", map.get("login"));
            return "redirect:/omain";
        }
        else{
            model.addAttribute("message", map.get("message"));
            return "/index";
        }
    }
    @GetMapping("/logout")
    public String getOwnerLoging (HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
    @PostMapping("/add")
    public String postAddOwner(OwnerAddVO data, Model model) {
       Map<String,Object> map = ownerInfoService.addOwner(data);
       if((boolean)map.get("status")) {
        return "redirect:/";
       }
       else{
       model.addAttribute("inputdata",data);
       model.addAttribute("message",map.get("message"));
       }
        return "/owner/add";
        }
    @GetMapping("/update/status")
    public String getOwnerUpdateStatus (@RequestParam Integer value,@RequestParam Long owner_no,
    @RequestParam Integer page, @RequestParam @Nullable String keyword , HttpSession session) {
        //  OwnerInfoVO owner = (OwnerInfoVO)session.getAttribute("loginUser");
         AdminVO admin = (AdminVO)session.getAttribute("loginUser");
        if(admin == null) { // 로그인 상태가 아니라면
            return "redirect:/"; // 로그인 페이지로
        }
        else if (admin.getAi_grade() != 99) { // 로그인 했는데 마스터가 아니라면
            return "redirect:/main"; // 메인페이지로
        }
        ownerInfoService.updateOwnerStatus(value,owner_no);
        String returnValue = "";
        if(keyword == null || keyword.equals("")) returnValue = "redirect:/owner/list?page="+page;
        else returnValue = "redirect:/owner/list?page="+page+"&keyword="+keyword;
        return returnValue;
    }
    @GetMapping ("/delete") 
    public String getOwnerDelete(@RequestParam Long owner_no,@RequestParam Integer page, @RequestParam @Nullable String keyword,HttpSession session) {
        AdminVO admin = (AdminVO)session.getAttribute("loginUser");
        if(admin == null) { // 로그인 상태가 아니라면
            return "redirect:/"; // 로그인 페이지로
        }
        else if (admin.getAi_grade() != 99) { // 로그인 했는데 마스터가 아니라면
            return "redirect:/main"; // 메인페이지로
        }
        ownerInfoService.deleteOwner(owner_no);
        String returnValue = "";
        if(keyword == null || keyword.equals("")) returnValue = "redirect:/owner/list?page="+page;
        else returnValue = "redirect:/owner/list?page="+page+"&keyword="+keyword;
        return returnValue;
    }
    @GetMapping("/detail")
    public String getOwnerDetail (@RequestParam Long owner_no, Model model,HttpSession session) {
        model.addAttribute("owner_detail", ownerInfoService.getOwnerInfo(owner_no));
        OwnerInfoVO owner = (OwnerInfoVO)session.getAttribute("loginUser");
        if(owner == null) { // 로그인 상태가 아니라면
            return "redirect:/"; // 로그인 페이지로
        }
        else{
        return "/owner/detail";
        }
    }
    @PostMapping("/update")
        public String postOwnerUpdate(HttpSession session, OwnerUpdateVO data) {
        System.out.println(data);
        Map <String,Object> map = ownerInfoService.updateOwnerInfo(data);
        if((boolean) map.get("status")) {
            return "redirect:/";
        }
        session.setAttribute("update_result", map);
        return "redirect:/owner/detail?owner_no="+data.getSeq();

    }
}
