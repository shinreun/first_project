package com.greenart.yogio.admin.raeeun.controller;

import java.util.Map;

import org.hibernate.dialect.function.ModeStatsModeEmulation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.greenart.yogio.admin.raeeun.service.OdOrderService;
import com.greenart.yogio.admin.raeeun.vo.OrderAddVO;

import io.micrometer.common.lang.Nullable;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/order")
public class OdOrderController {
  @Autowired OdOrderService order;
  
  @GetMapping("/list")
  public String getOrderList(Model model, @RequestParam @Nullable String keyword, Long miSeq, Pageable pageable) {
    if (keyword == null) {
      keyword = "";
    }
    model.addAttribute("result", order.showBriefOrderList(keyword, pageable));
    model.addAttribute("keyword", keyword);
    return "/order/list";
  }

  @GetMapping("/detail")
  public String getDetailOrder(Model model, @RequestParam @Nullable String keyword, Long miSeq,
      String orderNum) {
    if (keyword == null) {
      keyword = "";
    }
    model.addAttribute("result", order.showOrder(orderNum));
    model.addAttribute("keyword", keyword);

    return "/order/detail";
  }
  
  @GetMapping("/menu/add")
  public String getMenuAdd() {
    return "/order/menuadd";
  }

  @GetMapping("/option/add")
  public String getOptionAdd() {
    return "/order/optionadd";
  }

  @PostMapping("/menu/add")
  public String postMenuAdd(OrderAddVO data, Model model) {
    Map<String, Object> map = order.addMenu(data);
    if ((boolean) map.get("status")) {
      model.addAttribute("order", map);
      return "redirect:/order/list";
    } else {
      model.addAttribute("message", map.get("message"));
      return "/order/menuadd";
    }
  }

  @PostMapping("/option/add")
  public String postOptionAdd(OrderAddVO data, Model model) {
    Map<String, Object> map = order.addOption(data);
    if ((boolean) map.get("status")) {
      model.addAttribute("order", map);
      return "redirect:/order/menu/add";
    } else {
      model.addAttribute("message", map.get("message"));
      return "order/optionadd";
    }
  }
  
  @GetMapping("/update/status")
  public String getAdminUpdateStatus(@RequestParam Integer value, @RequestParam String orderNum,
      @RequestParam @Nullable String keyword) {
    order.updateOrderStatus(value, orderNum);
    String returnValue = "";
    if (keyword == null) {
      returnValue = "redirect:/order/list";
    } else {
      returnValue = "redirect:/order/list?keyword=" + keyword;
    }
    return returnValue;
  }
  
  @GetMapping("/delete")
  public String getDeleteOrder(@RequestParam String orderNum, @RequestParam @Nullable String keyword) {
    if (keyword == null) {
      keyword = "";
    }
    order.deleteOrder(orderNum);
    return "redirect:/order/list?keyword=" + keyword;
  }
  
}
