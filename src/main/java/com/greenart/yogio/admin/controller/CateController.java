package com.greenart.yogio.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.greenart.yogio.admin.service.StoreCategoryService;
import com.greenart.yogio.admin.vo.store.StoreCategoryVO;

@Controller
public class CateController {
    @Autowired StoreCategoryService storeCategoryService;
    @GetMapping("/cate/add")
    public String getCateAdd () {
    return "/cate/add";
}
// @RequestMapping(value = "/cate", method = RequestMethod.POST)
@PostMapping("/cate/add")
public String postCateadd(StoreCategoryVO data ) {
 storeCategoryService.addCate(data);
    return "redirect:/cate/list";
}
@GetMapping("/cate/list") 
public String getCateList (Model model , @PageableDefault(size=5)Pageable pageable) {
       model.addAttribute("result", storeCategoryService.getCateList(pageable));
        return "/cate/list";
    }
}
