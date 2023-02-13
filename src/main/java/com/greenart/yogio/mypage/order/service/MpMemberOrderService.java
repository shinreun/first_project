package com.greenart.yogio.mypage.order.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import com.greenart.yogio.mypage.member.entity.MbMemberInfoEntity;
import com.greenart.yogio.mypage.member.repository.MpMemberInfoRepository;
import com.greenart.yogio.mypage.order.entity.MpMypageMenuChoiceEntity;
import com.greenart.yogio.mypage.order.entity.MpMypageOptionChoiceEntity;
import com.greenart.yogio.mypage.order.entity.MpMypageOrderPriceByOiSeqEntity;
import com.greenart.yogio.mypage.order.entity.MpMypageOrderPriceByOrderNumEntity;
import com.greenart.yogio.mypage.order.entity.MpOrderInfoEntity;
import com.greenart.yogio.mypage.order.entity.MpPlusMenuChoiceEntity;
import com.greenart.yogio.mypage.order.repository.MpMypageMenuChoiceRepository;
import com.greenart.yogio.mypage.order.repository.MpPlusMenuChoiceRepository;
import com.greenart.yogio.mypage.order.repository.MpMypageOptionChoiceRepository;
import com.greenart.yogio.mypage.order.repository.MpMypageOrderPriceByOiSeqRepository;
import com.greenart.yogio.mypage.order.repository.MpMypageOrderPriceByOrderNumRepository;
import com.greenart.yogio.mypage.order.repository.MpOrderInfoRepository;
import com.greenart.yogio.mypage.order.vo.MpOrderInfoVO;
import com.greenart.yogio.mypage.order.vo.MpWishListVO;
import com.greenart.yogio.mypage.store.entity.MpMenuCategoryEntity;
import com.greenart.yogio.mypage.store.entity.MpStoreInfoEntity;
import com.greenart.yogio.mypage.store.repository.MpMenuCategoryRepository;
import com.greenart.yogio.mypage.store.repository.MpStoreInfoRepository;


@Service
public class MpMemberOrderService {
  @Autowired MpPlusMenuChoiceRepository plusRepo;
  @Autowired MpOrderInfoRepository oRepo;
  @Autowired MpMemberInfoRepository memberRepo;
  @Autowired MpMypageOptionChoiceRepository optionChoiceRepo;
  @Autowired MpMypageMenuChoiceRepository menuChoiceRepo;
  @Autowired MpStoreInfoRepository storeRepo;
  @Autowired MpMenuCategoryRepository menuCateRepo;
  @Autowired MpMypageOrderPriceByOrderNumRepository priceRepo;
  @Autowired MpMypageOrderPriceByOiSeqRepository priceOiSeqRepo;

  // 멤버별 상세한 주문 내역 출력
  public Map<String, Object> showOrderList(Long miSeq, Pageable pageable) {
    Map<String, Object> map = new LinkedHashMap<>();
     MbMemberInfoEntity member = memberRepo.findByMiSeq(miSeq);
    // MbMemberInfoEntity member = (MbMemberInfoEntity) session.getAttribute("loginUser");
    if (member == null) {
      map.put("status", false);
      map.put("message", "로그인 후 이용하실 수 있습니다.");
    }
    // 로그인 정보가 있다면
    else {
      // 주문 정보를 출력할 map 생성
      Map<String, Object> map2 = new LinkedHashMap<>();

      // 멤버로 주문 내역 찾아서 메뉴 출력시, 주문 번호 별로 묶어야 함
      // 메뉴 옵션은 메뉴 주문 번호 별로 출력 해야함 

      // map을 저장할 수 있는 리스트 생성
      List<Map<String, Object>> orderlist = new ArrayList<Map<String, Object>>();
      
      // 멤버 변수를 통해 멤버가 주문한 메뉴 정보를 List에 저장
      List<MpMypageMenuChoiceEntity> mList = menuChoiceRepo.findByMiSeq(member.getMiSeq());
      // mList.sort(Comparator.comparing(MpMypageMenuChoiceEntity::getDate).reversed());
      // 만약 주문한 메뉴 정보 리스트가 없다면, 주문내역이 없다는 메세지 출력
      if (mList.isEmpty()) {
        map2.put("status", false);
        map2.put("message", "주문내역 없음");
      } 
      // 주문 내역이 있다면,
      else {
        // 반복문을 통해 멤버가 주문한 메뉴 정보 mList에서 주문 번호를 가져와서 (주문 멤버별로 엮여있음)
        for (int m = 0; m < mList.size(); m++) {
          if (mList.get(m).getOiStatus() == 2) {
            // 주문번호를 통해 주문한 메뉴정보를 조회해서 meList에 다시 저장 (주문 번호 별로 엮여있음)
            List<MpMypageMenuChoiceEntity> meList = menuChoiceRepo.findByOiOrderNum(mList.get(m).getOiOrderNum());
            // 임의의 변수를 지정
            int count = 0;
            // 반복문을 통해, 리스트 안에 저장된 값의 주문번호가 새로 저장될 값의 주문번호가 일치하는 경우가 있다면,
            // 변수의 값을 1높이고 반복문을 멈추게 설정
            for (int i = 0; i < m; i++) {
              if (mList.get(i).getOiOrderNum().equals(mList.get(m).getOiOrderNum())) {
                count++;
                break;
              }
            }
            // 반복문을 다 돌고 나온 후, 만약 변수의 값이 1이 아니라면, 같은 주문번호의 주문이 없는 것이므로 
            // list에 저장
            if (count != 1) {
              List<MpOrderInfoVO> orderMenu = new ArrayList<MpOrderInfoVO>();

              // 반복문을 통해서 하나의 메뉴를 vo에 저장하고, 그 메뉴의 옵션을 저장
              for (int j = 0; j < meList.size(); j++) {
                List<MpMypageOptionChoiceEntity> option = optionChoiceRepo.findByOiSeq(meList.get(j).getOiSeq());
                MpOrderInfoVO vo = new MpOrderInfoVO();
                if (option.isEmpty()) {
                  vo = MpOrderInfoVO.builder()
                  .oiSeq(meList.get(j).getOiSeq())
                  .oiOrderNum(meList.get(j).getOiOrderNum())
                  .miSeq(meList.get(j).getMiSeq())
                  .mniName(meList.get(j).getMniName())
                  .menuAmount(meList.get(j).getMenuAmount())
                  .menuPrice(meList.get(j).getMenuPrice())
                  .mcSeq(meList.get(j).getMcSeq())
                  .oiStatus(meList.get(j).getOiStatus())
                  .optionList(null)
                  .build();
                }
                else {
                  vo = MpOrderInfoVO.builder()
                  .oiSeq(meList.get(j).getOiSeq())
                  .oiOrderNum(meList.get(j).getOiOrderNum())
                  .miSeq(meList.get(j).getMiSeq())
                  .mniName(meList.get(j).getMniName())
                  .menuAmount(meList.get(j).getMenuAmount())
                  .menuPrice(meList.get(j).getMenuPrice())
                  .mcSeq(meList.get(j).getMcSeq())
                  .oiStatus(meList.get(j).getOiStatus())
                  .optionList(optionChoiceRepo.findByOiSeq(meList.get(j).getOiSeq()))
                  .build();
                }
                // 리스트에 메뉴와 옵션을 저장
                orderMenu.add(vo);
               
                // 만약 주문번호별 리스트의 마지막 주문이라면, orderlist에 저장함으로써 주문번호별로 구분해줌.
                if (j == meList.size() - 1) {
                  // 반복문이 돌때 마다 map3가 새로 생성될 수 있도록, 반복문안에 생성문 작성
                  Map<String, Object> map3 = new LinkedHashMap<String, Object>();
                  // map에 메뉴와 옵션정보가 저장된 list를 저장
                  map3.put("orderMenu", orderMenu);

                  // 주문 번호 별 가게 이름, 주문 일자, 주문 금액
                  // 메뉴 리스트의 카테고리 번호를 조회해서 메뉴 카테고리 정보를 가져오고
                  MpMenuCategoryEntity menuCate = menuCateRepo.findByMcSeq(meList.get(j).getMcSeq());
                  // 메뉴 카테고리 정보를 조회해서 가게 정보를 가져옴
                  MpStoreInfoEntity store = storeRepo.findBySiSeq(menuCate.getStore().getSiSeq());
                  // 가게 정보를 map에 저장
                  map3.put("storeName", store.getSiName());
                  // 주문정보번호 조회를 통해 주문 정보를 가져옴
                  MpOrderInfoEntity order = oRepo.findByOiSeq(meList.get(j).getOiSeq());
                  // 주문 번호의 주문날짜와 배달완료 날짜 map에 저장
                  map3.put("orderDate", order.getOiOrderDt());
                  map3.put("finishDate", order.getOiFinishDt());
                  // 주문번호를 조회해서 가격정보를 가져와서 
                  MpMypageOrderPriceByOrderNumEntity price = priceRepo.findByOiOrderNum(meList.get(j).getOiOrderNum());
                  // 옵션 가격이 존재한다면, 옵션을 선택한 것이므로
                  if (price.getTotalOptionPrice() != null) {
                    // map에 가격정보 저장
                    map3.put("price", price.getTotalMenuPrice() + price.getTotalOptionPrice());
                  } else {
                    map3.put("price", price.getTotalMenuPrice());
                  }
                  // 리스트에 map정보를 저장
                  orderlist.add(map3);
                }
              }
              // 다시 반복문 시작으로 돌아가서
              // 두번째 mlist의 값의 주문번호에 따른 리스트 값을 melist에 저장
              // 저장된 정보의 주문번호가 이미 저장된 주문 번호가 아니라면, 주문 번호가 같은 주문 메뉴와 옵션리스트를 저장할 리스트를 새로 생성
              // 위 과정을 반복함
            }
            map2.put("list", orderlist);
          }
          else {
            map2.put("status", false);
            map2.put("message", "주문내역 없음");
          }
        }
      }
      return map2;
    }
    return map;
  }
  

  // 멤버별 간단한 주문 내역 출력
  public Map<String, Object> showBriefOrderList( Long miSeq, @PageableDefault (size = 8) Pageable page) {
    Map<String, Object> map = new LinkedHashMap<>();
    // MbMemberInfoEntity member = (MbMemberInfoEntity) miSeq.getAttribute("loginUser");
    MbMemberInfoEntity member = memberRepo.findByMiSeq(miSeq);
    if (member == null) {
      map.put("status", false);
      map.put("message", "로그인 후 이용하실 수 있습니다.");
    } else {
      Map<String, Object> map2 = new LinkedHashMap<>();
      // 멤버로 주문 내역 찾아서 메뉴 출력시, 주문 번호 별로 묶어야 함
      // 메뉴 옵션은 메뉴 주문 번호 별로 출력 해야함 

      // map을 저장할 수 있는 list 생성
      List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

      // 멤버 변수를 통해 멤버가 주문한 메뉴 정보를 List에 저장
      List<MpMypageMenuChoiceEntity> mList = menuChoiceRepo.findByMiSeq(member.getMiSeq());

      if (mList.isEmpty()) {
        map2.put("status", false);
        map2.put("message", "주문내역 없음");
      } 
      else {
        // 반복문을 통해 멤버가 주문한 메뉴 정보 mList에서 주문 번호를 가져와서 
        for (int m = 0; m < mList.size(); m++) {
          // 만약 리스트의 상태값이 배송완료된 상태라면
          if (mList.get(m).getOiStatus() == 2) {
            // 주문번호를 통해 주문한 메뉴정보를 조회해서 meList에 다시 저장
            List<MpMypageMenuChoiceEntity> meList = menuChoiceRepo.findByOiOrderNum(mList.get(m).getOiOrderNum());

            // 임의의 변수를 지정
            int count = 0;
            // 반복문을 통해, 리스트 안에 저장된 값의 주문번호가 새로 저장될 값의 주문번호가 일치하는 경우가 있다면,
            // 변수의 값을 1높이고 반복문을 멈추게 설정
            for (int i = 0; i < m; i++) {
              if (mList.get(i).getOiOrderNum().equals(mList.get(m).getOiOrderNum())) {
                count++;
                break;
              }
            }
            // 반복문을 다 돌고 나온 후, 만약 변수의 값이 1이 아니라면, 같은 주문번호의 주문이 없는 것이므로 
            // map 에 저장
            if (count != 1) {
              for (int j = 0; j < meList.size(); j++) {
                // 주문번호별 주문 리스트의 마지막 메뉴 정보를 대표로 들고 오기위해, 임의의 변수에 주문 리스트의 마지막 index값을 준다.
                if (j == meList.size() - 1) {
                  Map<String, Object> map3 = new LinkedHashMap<>();
                  // 주문 번호 별 가게 이름, 주문메뉴, 메뉴 수량, 주문 일자, 주문 금액
                  MpMenuCategoryEntity menuCate = menuCateRepo.findByMcSeq(meList.get(j).getMcSeq());
                  MpStoreInfoEntity store = storeRepo.findBySiSeq(menuCate.getStore().getSiSeq());
                  MpOrderInfoEntity order = oRepo.findByOiSeq(meList.get(j).getOiSeq());
                  // 주문 번호 map에 저장
                  map3.put("orderNum", order.getOiOrderNum());
                  // 가게 이름 map에 저장
                  map3.put("storeName", store.getSiName());
                  // 주문한 메뉴 중 대표 1개만 map에 저장
                  map3.put("menuName", meList.get(j).getMniName());
                  // 주문한 메쥬 종류 수 
                  map3.put("menuTotal", meList.size());
                  // 주문일자, 배달완료일자 저장
                  map3.put("orderDate", order.getOiOrderDt());
                  map3.put("finishDate", order.getOiFinishDt());
                  // 주문 가격 저장
                  MpMypageOrderPriceByOrderNumEntity price = priceRepo.findByOiOrderNum(meList.get(j).getOiOrderNum());
                  if (price.getTotalOptionPrice() != null) {
                    map3.put("price", price.getTotalMenuPrice() + price.getTotalOptionPrice());
                  } else {
                    map3.put("price", price.getTotalMenuPrice());
                  }
                  list.add(map3);
                }
              }
            }
            map2.put("list", list);
            map2.put("status", true);
            map2.put("message", "주문내역 조회완료");
          }
          else if(list.size() == 0) {
            map2.put("status", false);
            map2.put("message", "주문내역 없음");
          }
        }
        // 다시 반복문 시작으로 돌아가서
        // 두번째 mlist의 값의 주문번호에 따른 리스트 값을 melist에 저장
        // 저장된 정보의 주문번호가 이미 저장된 주문 번호가 아니라면, 주문 번호가 같은 주문 메뉴와 옵션리스트를 저장할 리스트를 새로 생성
        // 위 과정을 반복함
      }
      return map2;
    }
    return map;
  }
  
  
  // 주문 번호를 통해 주문내역 출력
  public Map<String, Object> showOrder(String orderNum) {
    Map<String, Object> map = new LinkedHashMap<>();
    List<Object> list = new ArrayList<Object>();

    // 입력된 주문번호와 일치하는 메뉴 선택 리스트로 저장
    List<MpMypageMenuChoiceEntity> mlist = menuChoiceRepo.findByOiOrderNum(orderNum);

    if (mlist.isEmpty()) {
      map.put("status", false);
      map.put("message", "주문번호를 다시 확인해주세요.");
    }

    else {
      // 주문 번호 별 가게 이름, 주문 일자, 주문 금액
      // 첫번째 주문의 메뉴 카테고리를 들고와서 카테고리 정보찾고,
      MpMenuCategoryEntity menuCate = menuCateRepo.findByMcSeq(mlist.get(0).getMcSeq());
      // 카테고리 정보를 통해 가게 정보 저장
      MpStoreInfoEntity store = storeRepo.findBySiSeq(menuCate.getStore().getSiSeq());
      map.put("storeName", store.getSiName());
      MpOrderInfoEntity order = oRepo.findByOiSeq(mlist.get(0).getOiSeq());
      map.put("orderDate", order.getOiOrderDt());
      map.put("finishDate", order.getOiFinishDt());
      // 가격 정보를 출력할 수 있는 엔터티 들고와서
      MpMypageOrderPriceByOrderNumEntity price = priceRepo.findByOiOrderNum(mlist.get(0).getOiOrderNum());
      // 옵션가격이 있는 경우
      if (price.getTotalOptionPrice() != null) {
        // 합계 금액을 출력
        map.put("price", price.getTotalMenuPrice() + price.getTotalOptionPrice());
      }
      // 옵션가격이 없는 경우
      else {
        // 메뉴 가격만 출력
        map.put("price", price.getTotalMenuPrice());
      }
      // 반복문을 통해 주문한 메뉴 아래에 옵션 저장
      for (int i = 0; i < mlist.size(); i++) {
        List<MpMypageOptionChoiceEntity> option = optionChoiceRepo.findByOiSeq(mlist.get(i).getOiSeq());
        MpOrderInfoVO vo = new MpOrderInfoVO();
        if (option.isEmpty()) {
          vo = MpOrderInfoVO.builder()
          .oiSeq(mlist.get(i).getOiSeq())
          .oiOrderNum(mlist.get(i).getOiOrderNum())
          .miSeq(mlist.get(i).getMiSeq())
          .mniName(mlist.get(i).getMniName())
          .menuAmount(mlist.get(i).getMenuAmount())
          .menuPrice(mlist.get(i).getMenuPrice())
          .mcSeq(mlist.get(i).getMcSeq())
          .oiStatus(mlist.get(i).getOiStatus())
          .optionList(null)
          .build();
        }
        else {
          vo = MpOrderInfoVO.builder()
          .oiSeq(mlist.get(i).getOiSeq())
          .oiOrderNum(mlist.get(i).getOiOrderNum())
          .miSeq(mlist.get(i).getMiSeq())
          .mniName(mlist.get(i).getMniName())
          .menuAmount(mlist.get(i).getMenuAmount())
          .menuPrice(mlist.get(i).getMenuPrice())
          .mcSeq(mlist.get(i).getMcSeq())
          .oiStatus(mlist.get(i).getOiStatus())
          .optionList(optionChoiceRepo.findByOiSeq(mlist.get(i).getOiSeq()))
          .build();
        }
        // 리스트에 메뉴와 옵션을 저장
        list.add(vo);
      }
      // 주문메뉴와 옵션이 저장된 리스트 map 에 저장
      map.put("menu", list);
      map.put("status", true);
    }
    return map;
  }


  // 로그인된 멤버의 주문표 출력
  public Map<String, Object> showWishList (Long miSeq) {
    Map<String, Object> map = new LinkedHashMap<>();
    // MbMemberInfoEntity member = (MbMemberInfoEntity) miSeq.getAttribute("loginUser");
    MbMemberInfoEntity member = memberRepo.findByMiSeq(miSeq);
    // System.out.println(member);
    if (member == null) {
      map.put("status", false);
      map.put("message", "로그인 후 이용하실 수 있습니다.");
    } 
    else {
      Map<String, Object> map2 = new LinkedHashMap<>();
      List<Object> list = new ArrayList<Object>();

      // 회원번호와 일치하는 메뉴 선택 리스트로 저장
      List<MpMypageMenuChoiceEntity> mlist = menuChoiceRepo.findByMiSeq(member.getMiSeq());
      map.put("miseq", member.getMiSeq());
      if (mlist.isEmpty()) {
        map.put("status", false);
        map.put("message", "주문표가 비어있습니다.");
      } 
      else {
        Integer totalPrice = 0;
        // 반복문으로 메뉴 선택 리스트를 돌면서
        for (MpMypageMenuChoiceEntity menu : mlist) {
          // 만약 메뉴 선택된 상태가 장바구니 상태라면
          if (menu.getOiStatus() == 0) {
            // 첫번째 주문의 메뉴 카테고리를 들고와서 카테고리 정보찾고,
            MpMenuCategoryEntity menuCate = menuCateRepo.findByMcSeq(menu.getMcSeq());
            // 카테고리 정보를 통해 가게 정보 저장
            MpStoreInfoEntity store = storeRepo.findBySiSeq(menuCate.getStore().getSiSeq());
            map2.put("storeName", store.getSiName());
            // 첫번째 주문의 oiSeq를 통해 옵션을 찾아서 리스트에 저장하고
            List<MpMypageOptionChoiceEntity> option = optionChoiceRepo.findByOiSeq(menu.getOiSeq());
            // 첫번째 주문의 oiSeq를 통해 가격정보를 찾아서 변수에 저장한다
            MpMypageOrderPriceByOiSeqEntity price = priceOiSeqRepo.findByOiSeq(menu.getOiSeq());
            map.put("option", option);
            map.put("price", price);
            MpWishListVO wish = new MpWishListVO();
            // 만약 옵션리스트가 비어있다면 옵션이 선택되지 않았으므로, 
            // 옵션은 null로 저장하고, 가격도 메뉴 가격만 저장한다
            if (option.isEmpty()) {
              wish = MpWishListVO.builder().oiSeq(menu.getOiSeq())
                  .oiOrderNum(menu.getOiOrderNum())
                  .mniName(menu.getMniName())
                  .menuPrice(menu.getMenuPrice())
                  .menuAmount(menu.getMenuAmount())
                  .option(null)
                  .menuOrderPrice(price.getOiSeqPrice()).build();
            }
            // 만약 옵션리스트가 있다면 저장하고
            // 가격은 메뉴와 옵션의 가격이 더해진 가격을 저장한다.
            else {
              wish = MpWishListVO.builder().oiSeq(menu.getOiSeq())
                  .oiOrderNum(menu.getOiOrderNum())
                  .mniName(menu.getMniName())
                  .menuPrice(menu.getMenuPrice())
                  .menuAmount(menu.getMenuAmount())
                  .option(optionChoiceRepo.findByOiSeq(menu.getOiSeq()))
                  .menuOrderPrice(price.getOiSeqPrice()).build();
            }
            // 리스트에 저장한다.
            list.add(wish);
            // 합계 금액을 출력
            totalPrice += price.getOiSeqPrice();
          }

          // map에 주문정보를 저장한다.
          map2.put("menu", list);
        }
        // 만약 list가 비어있다면, 장바구니상태인 주문 내역이 없는 것이므로 
        if (list.isEmpty()) {
          Map<String, Object> map3 = new LinkedHashMap<String, Object>();
          map3.put("status", false);
          map3.put("message", "주문표가 비어있습니다2.");
          return map3;
        }
        map2.put("totalPrice", totalPrice);
        // 주문메뉴와 옵션이 저장된 리스트 map 에 저장
        return map2;
      }
    }
    return map;
  }
  

  // 주문 상태 수정 - 주문완료에서 배달 완료로
  public Map<String, Object> updateOrderStatus2(String orderNum) {
    Map<String, Object> map = new LinkedHashMap<String, Object>();
    List<MpOrderInfoEntity> entity = oRepo.findAllByOiOrderNum(orderNum);
    if (entity.isEmpty()) {
      map.put("status", false);
      map.put("message", "주문완료된 주문이 없습니다.");
    } else {
      for (int i = 0; i < entity.size(); i++) {
        entity.get(i).setOiStatus(2);
        entity.get(i).setOiFinishDt(LocalDate.now());
        oRepo.save(entity.get(i));
      }
      map.put("status", true);
      map.put("message", "배달완료되었습니다.");
    }
    return map;
  }


  // 주문 상태 수정 - 장바구니에서 주문 완료로
  public Map<String, Object> updateOrderStatus1(Long miSeq) {
    Map<String, Object> map = new LinkedHashMap<>();
    // MbMemberInfoEntity member = (MbMemberInfoEntity) miSeq.getAttribute("loginUser");
    MbMemberInfoEntity member = memberRepo.findByMiSeq(miSeq);
    if (member == null) {
      map.put("status", false);
      map.put("message", "로그인 후 이용하실 수 있습니다.");
    } else {
      // 로그인한 회원과 일치하는 메뉴 선택 리스트로 저장
      List<MpOrderInfoEntity> order = oRepo.findAllByMember(member);
      if (order.isEmpty()) {
        map.put("status", false);
        map.put("message", "장바구니가 비어있습니다.");
      }
      LocalDate today = LocalDate.now();
      // 숫자의 경우 아스키코드로 변환시 48~57이 0~9로 표현.
      // 로직의 randim.ints() 메소드의 첫번째 파라미터를 48로 지정
      int leftLimit = 48; // numeral '0'
      // 두번째 파라미터는 알파벳의 제일끝이 122이므로 알파벳만 출력할때와 같이 122+1로 셋팅
      int rightLimit = 122; // letter 'z'
      // 길이 제한
      int length = 10;
      Random random = new Random();
      String orderNum = random.ints(leftLimit, rightLimit + 1)
          // 알파벳과 숫자만 출력하기위해 filter() 메소드를 활용해서 아스키코드의 범위를 제한
          .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
          // 문자열 길이를 limit()메소드로 제한
          .limit(length)
          //  collect() 메소드로 StringBuilder 객체를 생성
          .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
          //  StringBuilder 객체를 toString() 으로 문자열로 변환
          .toString();
      int check = 0;
      // 반복문으로 메뉴 선택 리스트를 돌면서
      for (int m = 0; m < order.size(); m++) {
        // 만약 선택된 주문정보의 상태가 장바구니 상태라면
        if (order.get(m).getOiStatus() == 0) {
          // 선택된 주문 정보의 상태를 주문완료로 고친후
          order.get(m).setOiStatus(1);
          order.get(m).setOiOrderDt(today);
          order.get(m).setOiOrderNum(orderNum);
          // 저장
          oRepo.save(order.get(m));
          check++;
        }
      }
      if (check != 0) {
        map.put("status", true);
        map.put("message", "주문 완료되었습니다.");
      } else {
        map.put("status", false);
        map.put("message", "장바구니가 비어있습니다.");
      }
    }
    return map;
  }


  // 주문 메뉴 수량 업데이트
  public Map<String, Object> updateOrderAmount(Long oiSeq, Integer amount, Long miSeq) {
    Map<String, Object> map = new LinkedHashMap<String, Object>();
    MbMemberInfoEntity member = memberRepo.findByMiSeq(miSeq);
    MpOrderInfoEntity orderMenu = oRepo.findByOiSeq(oiSeq);
    if (orderMenu == null) {
      map.put("status", false);
      map.put("message", "해당 메뉴는 주문에 존재하지 않습니다.");
    } else if (orderMenu.getMember().getMiSeq() != member.getMiSeq()) {
      map.put("status", false);
      map.put("message", "로그인한 사용자의 장바구니 메뉴만 수정 가능합니다.");
    } else {
      orderMenu.setOiMenuAmount(amount);
      oRepo.save(orderMenu);
      map.put("status", true);
      map.put("message", "수량이 수정되었습니다");
      map.put("oiSeq", orderMenu.getOiSeq());
    }
    return map;
  }


  // 주문 옵션 수량 업데이트
  public Map<String, Object> updateOptionAmount(Long pmcSeq, Integer amount, Long miSeq) {
    Map<String, Object> map = new LinkedHashMap<String, Object>();
    MbMemberInfoEntity member = memberRepo.findByMiSeq(miSeq);
    MpPlusMenuChoiceEntity optionMenu = plusRepo.findByPmcSeq(pmcSeq);
    if (optionMenu == null) {
      map.put("status", false);
      map.put("message", "해당 옵션은 주문에 존재하지 않습니다.");
    } else if (optionMenu.getOrder().getMember().getMiSeq() != member.getMiSeq()) {
      map.put("status", false);
      map.put("message", "로그인한 사용자의 장바구니 메뉴만 수정 가능합니다.");
    } else {
      optionMenu.setPmcAmount(amount);
      plusRepo.save(optionMenu);
      map.put("status", true);
      map.put("message", "수량이 수정되었습니다");
      map.put("pmcSeq", optionMenu.getPmcSeq());
    }
    return map;
  }


  // 장바구니 내역 전체 삭제
  public Map<String, Object> deleteWishListAll(Long miSeq) {
    MbMemberInfoEntity member = memberRepo.findByMiSeq(miSeq);
    Map<String, Object> map = new LinkedHashMap<String, Object>();
    // 로그인한 회원과 일치하는 메뉴 선택 리스트로 저장
    List<MpOrderInfoEntity> order = oRepo.findAllByMember(member);
    // 만약 리스트가 비어있다면, 장바구니가 없으므로 메시지 출력
    if (order.isEmpty()) {
      map.put("status", false);
      map.put("message", "장바구니가 비어있습니다.");
    }
    Integer check = 0;
    // 반복문으로 메뉴 선택 리스트를 돌면서
    for (int m = 0; m < order.size(); m++) {
      // 만약 선택된 주문정보의 상태가 장바구니 상태라면
      if (order.get(m).getOiStatus() == 0) {
        // 선택된 주문 정보의 옵션정보를 리스트에 저장
        List<MpPlusMenuChoiceEntity> option = plusRepo.findByOrder(order.get(m));
        if (!option.isEmpty()) {
          for (int i = 0; i < option.size(); i++) {
            // 메뉴에 걸린 옵션 정보를 삭제
            plusRepo.delete(option.get(i));
          }
        }
        // 선택된 주문 정보를 삭제
        oRepo.deleteById(order.get(m).getOiSeq());
        check++;
      }
    }
    if (check != 0) {
      map.put("status", true);
      map.put("message", "장바구니를 삭제했습니다.");
    } else {
      map.put("status", false);
      map.put("message", "장바구니가 비어있습니다.");
    }
    return map;
  }
  
  // 장바구니 내역 일부 삭제
  public Map<String, Object> deleteWishList(Long miSeq, Long oiSeq) {
    MbMemberInfoEntity member = memberRepo.findByMiSeq(miSeq);
    // 주문 seq와 일치하는 주문 메뉴 정보 저장
    MpOrderInfoEntity orderMenu = oRepo.findByOiSeq(oiSeq);
    Map<String, Object> map = new LinkedHashMap<String, Object>();
    if (orderMenu == null) {
      map.put("status", false);
      map.put("message", "장바구니가 비어있습니다.");
    }
    // 만약 선택된 주문정보의 상태가 장바구니 상태이고, 주문한 사람과 로그인한 사람이 일치한다면
    else if (orderMenu.getOiStatus() == 0 && orderMenu.getMember().getMiSeq() == member.getMiSeq()) {
      // 주문된 메뉴의 옵션 정보 저장
      List<MpPlusMenuChoiceEntity> option = plusRepo.findByOrder(orderMenu);
      // 옵션 정보가 있다면
      if (!option.isEmpty()) {
        for (int i = 0; i < option.size(); i++) {
          // 메뉴에 걸린 옵션 정보를 삭제
          plusRepo.delete(option.get(i));
        }
      }
      // 선택된 주문 정보를 삭제
      oRepo.deleteById(orderMenu.getOiSeq());
      map.put("status", true);
      map.put("message", "장바구니를 삭제했습니다.");
    }
    else if (orderMenu.getMember().getMiSeq() != member.getMiSeq()) {
      map.put("status", false);
      map.put("message", "장바구니에 담은 회원만 장바구니를 삭제할 수 있습니다.");
    }
    else {
      map.put("status", false);
      map.put("message", "장바구니가 비어있습니다.");
    }
    return map;
  }
  
  // 주문 내역 삭제
  public Map<String, Object> deleteOrder(String orderNum) {
     Map<String, Object> map = new LinkedHashMap<String, Object>();
    // 주문번호가 일치하는 내역을 찾아서 저장
    List<MpOrderInfoEntity> entity = oRepo.findAllByOiOrderNum(orderNum);
    if (entity.isEmpty()) {
      map.put("status", false);
      map.put("message", "주문번호가 일치하는 주문내역이 없습니다.");
    }
    else {
      // 내역 안의 메뉴정보를 전부 삭제
      for (int i = 0; i < entity.size(); i++) {
        oRepo.deleteById(entity.get(i).getOiSeq());
        // 메뉴정보에 걸린 옵션 정보를 찾아서 저장
        List<MpPlusMenuChoiceEntity> option = plusRepo.findByOrder(entity.get(i));
        // 만약 옵션 선택이 있다면,
        if (!option.isEmpty()) {
          // 옵션의 수만큼 반복문을 돌려 옵션 삭제
          for (int j = 0; j < option.size(); j++) {
            plusRepo.delete(option.get(j));
          }
        }
      }
      map.put("status", true);
      map.put("message", "주문내역이 삭제되었습니다.");
    }
    return map;
  }
  
}