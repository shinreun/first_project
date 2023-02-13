package com.greenart.yogio.admin.controller;


import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenart.yogio.admin.service.AdminInfoService;
import com.greenart.yogio.admin.service.OwnerInfoService;
import com.greenart.yogio.admin.vo.admin.AdminAddVO;
import com.greenart.yogio.admin.vo.admin.AdminLoginVO;
import com.greenart.yogio.admin.vo.admin.AdminUpateVO;
import com.greenart.yogio.admin.vo.admin.AdminVO;

import io.micrometer.common.lang.Nullable;
import jakarta.servlet.http.HttpSession;

@Controller
// @RequestMapping(value = "/admin", method = RequestMethod.POST)
@RequestMapping("/admin")
public class AdminController {
    @Autowired AdminInfoService adminInfoService;
    @GetMapping("/list")
    public String getAdminList(Model model,Pageable pageable){
        model.addAttribute("result",adminInfoService.getAdminList(pageable));
        return "/admin/list";

    }
    @GetMapping("/add")
    public String getAdminAdd() {
        return "/admin/add";
    }
    @PostMapping("/login")
    public String postAdminLogin(AdminLoginVO login , HttpSession session, Model model){
        Map<String,Object> map = adminInfoService.loginAdmin(login);
         if((boolean)map.get("status")) {
            session.setAttribute("loginUser", map.get("login"));
            return "redirect:/main";
        }
        else{
            model.addAttribute("message", map.get("message"));
            return "/index"; 
        }
    }
    @GetMapping("/logout")
    public String getAdminLoging (HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
    @PostMapping("/add")
    public String postAddAdmin(AdminAddVO data, Model model) {
       Map<String,Object> map = adminInfoService.addAdmin(data);
       if((boolean)map.get("status")) {
        return "redirect:/";
       }
       model.addAttribute("inputdata",data);
       model.addAttribute("message",map.get("message"));
        return "/admin/add";
        }

    @GetMapping("/update/status")
    public String getAdminUpdateStatus (@RequestParam Integer value,@RequestParam Long admin_no,
    @RequestParam Integer page, @RequestParam @Nullable String keyword , HttpSession session) {
         AdminVO admin = (AdminVO)session.getAttribute("loginUser");
        if(admin == null) { // 로그인 상태가 아니라면
            return "redirect:/"; // 로그인 페이지로
        }
        else if (admin.getAi_grade() != 99) { // 로그인 했는데 마스터가 아니라면
            return "redirect:/main"; // 메인페이지로
        }
        adminInfoService.updateAdminStatus(value,admin_no);
        String returnValue = "";
        if(keyword == null || keyword.equals("")) returnValue = "redirect:/admin/list?page="+page;
        else returnValue = "redirect:/admin/list?page="+page+"&keyword="+keyword;
        return returnValue;
    }
    @GetMapping ("/delete") 
    public String getAdminDelete(@RequestParam Long admin_no,@RequestParam Integer page, @RequestParam @Nullable String keyword,HttpSession session) {
        AdminVO admin = (AdminVO)session.getAttribute("loginUser");
        if(admin == null) { // 로그인 상태가 아니라면
            return "redirect:/"; // 로그인 페이지로
        }
        else if (admin.getAi_grade() != 99) { // 로그인 했는데 마스터가 아니라면
            return "redirect:/main"; // 메인페이지로
        }
        adminInfoService.deleteAdmin(admin_no);
        String returnValue = "";
        if(keyword == null || keyword.equals("")) returnValue = "redirect:/admin/list?page="+page;
        else returnValue = "redirect:/admin/list?page="+page+"&keyword="+keyword;
        return returnValue;
    }
    @GetMapping("/detail")
    public String getAdminDetail (@RequestParam Long admin_no, Model model,HttpSession session) {
        model.addAttribute("admin_detail", adminInfoService.getAdminInfo(admin_no));
        AdminVO admin = (AdminVO)session.getAttribute("loginUser");
        if(admin == null) { // 로그인 상태가 아니라면
            return "redirect:/"; // 로그인 페이지로
        }
        else{
        return "/admin/detail";
        }
    }
    @PostMapping("/update")
        public String postAdminUpdate(HttpSession session, AdminUpateVO data) {
        System.out.println(data);
        Map <String,Object> map = adminInfoService.updateAdminInfo(data);
        if((boolean) map.get("status")) {
            return "redirect:/";
        }
        session.setAttribute("update_result", map);
        return "redirect:/admin/detail?admin_no="+data.getSeq();

    }
}
    

