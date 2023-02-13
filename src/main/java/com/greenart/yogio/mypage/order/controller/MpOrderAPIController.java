package com.greenart.yogio.mypage.order.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greenart.yogio.admin.raeeun.service.OdOrderService;
import com.greenart.yogio.admin.raeeun.vo.OrderAddVO;
import com.greenart.yogio.mypage.order.service.MpMemberOrderService;

@RestController
@RequestMapping("/order")
public class MpOrderAPIController {
  @Autowired OdOrderService order;
  @Autowired MpMemberOrderService mOrder;

  // 메뉴 추가
  @PostMapping("/add/menu")
  public ResponseEntity<Object> postMenuAdd(@RequestBody OrderAddVO data) {
    Map<String, Object> map = order.addMenu(data);
      return new ResponseEntity<>(map, HttpStatus.OK);
    }
  
  // 옵션 추가
  @PostMapping("/add/option")
  public ResponseEntity<Object> postOptionAdd(@RequestBody OrderAddVO data) {
    Map<String, Object> map = order.addOption(data);
    return new ResponseEntity<>(map, HttpStatus.OK);
  }
  
  // 주문완료 -> 배달완료로 상태 변경
  @GetMapping("/update/status2")
  public ResponseEntity<Object> getOrderUpdateStatus2(@RequestParam String orderNum) {
    Map<String, Object> map = mOrder.updateOrderStatus2(orderNum);
    return new ResponseEntity<>(map, HttpStatus.OK);
  }
  
  // 장바구니 -> 주문완료 상태 변경
  @GetMapping("/update/status1")
  public ResponseEntity<Object> getOrderUpdateStatus1(@RequestParam Long miSeq) {
    Map<String, Object> map = mOrder.updateOrderStatus1(miSeq);
    return new ResponseEntity<>(map, HttpStatus.OK);
  }

  // 메뉴 수량 수정
  @GetMapping("/update/menu/amount")
  public ResponseEntity<Object> getOrderAmountUpdate(@RequestParam Long miSeq, @RequestParam Long oiSeq,
      @RequestParam Integer amount) {
    Map<String, Object> map = mOrder.updateOrderAmount(oiSeq, amount, miSeq);
    return new ResponseEntity<>(map, HttpStatus.OK);
  }
 
  // 옵션 수량 수정
  @GetMapping("/update/option/amount")
  public ResponseEntity<Object> getOptionAmountUpdate(@RequestParam Long miSeq, @RequestParam Long pmcSeq, 
      @RequestParam Integer amount) {
    Map<String, Object> map = mOrder.updateOptionAmount(pmcSeq, amount, miSeq);
    return new ResponseEntity<>(map, HttpStatus.OK);
  }
  
  // 장바구니 내역 전부 삭제
  @GetMapping("/delete/wishListAll")
  public ResponseEntity<Object> getdeletewishListAll(@RequestParam Long miSeq) {
    Map<String, Object> map = mOrder.deleteWishListAll(miSeq);
    return new ResponseEntity<>(map, HttpStatus.OK);
  }
 
  // 장바구니 내역 일부 삭제
  @GetMapping("/delete/wishList")
  public ResponseEntity<Object> getdeletewishList(@RequestParam Long miSeq, @RequestParam Long oiSeq) {
    Map<String, Object> map = mOrder.deleteWishList(miSeq, oiSeq);
    return new ResponseEntity<>(map, HttpStatus.OK);
  }
  
  // 주문내역 삭제
  @GetMapping("/delete/order")
  public ResponseEntity<Object> getDeleteOrder(@RequestParam String orderNum) {
    Map<String, Object> map = mOrder.deleteOrder(orderNum);
    return new ResponseEntity<>(map, HttpStatus.OK);
  }
}
