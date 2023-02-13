package com.greenart.yogio.admin.raeeun.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import com.greenart.yogio.admin.raeeun.vo.ListVO;
import com.greenart.yogio.admin.raeeun.vo.OptionVO;
import com.greenart.yogio.admin.raeeun.vo.OrderAddVO;
import com.greenart.yogio.mypage.member.entity.MbMemberInfoEntity;
import com.greenart.yogio.mypage.member.repository.MpMemberInfoRepository;
import com.greenart.yogio.mypage.order.entity.MpMypageMenuChoiceEntity;
import com.greenart.yogio.mypage.order.entity.MpMypageOptionChoiceEntity;
import com.greenart.yogio.mypage.order.entity.MpMypageOrderPriceByOrderNumEntity;
import com.greenart.yogio.mypage.order.entity.MpOrderInfoEntity;
import com.greenart.yogio.mypage.order.entity.MpPlusMenuChoiceEntity;
import com.greenart.yogio.mypage.order.entity.MpPlusMenuEntity;
import com.greenart.yogio.mypage.order.repository.MpMypageMenuChoiceRepository;
import com.greenart.yogio.mypage.order.repository.MpMypageOptionChoiceRepository;
import com.greenart.yogio.mypage.order.repository.MpMypageOrderPriceByOrderNumRepository;
import com.greenart.yogio.mypage.order.repository.MpOrderInfoRepository;
import com.greenart.yogio.mypage.order.repository.MpPlusMenuChoiceRepository;
import com.greenart.yogio.mypage.order.repository.MpPlusMenuRepository;
import com.greenart.yogio.mypage.store.entity.MpMenuCateJoinEntity;
import com.greenart.yogio.mypage.store.entity.MpMenuCategoryEntity;
import com.greenart.yogio.mypage.store.entity.MpMenuInfoEntity;
import com.greenart.yogio.mypage.store.entity.MpStoreInfoEntity;
import com.greenart.yogio.mypage.store.repository.MpMenuCateJoinRepository;
import com.greenart.yogio.mypage.store.repository.MpMenuCategoryRepository;
import com.greenart.yogio.mypage.store.repository.MpMenuInfoRepository;
import com.greenart.yogio.mypage.store.repository.MpStoreInfoRepository;

@Service
public class OdOrderService {
  @Autowired MpPlusMenuChoiceRepository plusChoiceRepo;
  @Autowired MpOrderInfoRepository oRepo;
  @Autowired MpMypageOptionChoiceRepository optionChoiceRepo;
  @Autowired MpMypageMenuChoiceRepository menuChoiceRepo;
  @Autowired MpStoreInfoRepository storeRepo;
  @Autowired MpMenuCategoryRepository menuCateRepo;
  @Autowired MpMenuCateJoinRepository menuCateJoinRepo;
  @Autowired MpMypageOrderPriceByOrderNumRepository priceRepo;
  @Autowired MpMenuInfoRepository menuRepo;
  @Autowired MpPlusMenuRepository plustRepo;
  @Autowired MpMemberInfoRepository memberRepo;
  
  public Map<String, Object> showBriefOrderList(String keyword, @PageableDefault(size = 8) Pageable pageable) {
    // 멤버로 주문 내역 찾아서 메뉴 출력시, 주문 번호 별로 묶어야 함
    // 메뉴 옵션은 메뉴 주문 번호 별로 출력 해야함 
    Map<String, Object> map2 = new LinkedHashMap<>();

    // map을 저장할 수 있는 list 생성
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

    if (keyword == null) {
      keyword = "";
    }
    // 모든 주문 메뉴 정보를 List에 저장
    List<MpMypageMenuChoiceEntity> mList = menuChoiceRepo.findOrderList(keyword);
    if (mList.isEmpty()) {
      map2.put("status", false);
    } else {
      for (int m = 0; m < mList.size(); m++) {
        Map<String, Object> map = new LinkedHashMap<>();
        // 메뉴 리스트의 메뉴 카테고리 값 저장
        MpMenuCategoryEntity menuCate = menuCateRepo.findByMcSeq(mList.get(m).getMcSeq());
        // 메뉴 카테고리의 값을 통해 가게 정보 저장 
        MpStoreInfoEntity store = storeRepo.findBySiSeq(menuCate.getStore().getSiSeq());
        MpOrderInfoEntity order = oRepo.findByOiSeq(mList.get(m).getOiSeq());
        // 주문 번호 map에 저장
        map.put("orderNum", order.getOiOrderNum());
        map.put("oiStatus", order.getOiStatus());
        // 가게 이름 map에 저장
        map.put("storeName", store.getSiName());
        // 주문한 메뉴 정보 저장
        map.put("menuName", mList.get(m).getMniName());
        // 주문일자, 배달완료일자 저장
        map.put("orderDate", order.getOiOrderDt());
        map.put("finishDate", order.getOiFinishDt());
        // 주문 가격 저장
        MpMypageOrderPriceByOrderNumEntity price = priceRepo.findByOiOrderNum(mList.get(m).getOiOrderNum());
        if (price.getTotalOptionPrice() != null) {
          map.put("price", price.getTotalMenuPrice() + price.getTotalOptionPrice());
        } else {
          map.put("price", price.getTotalMenuPrice());
        }
        list.add(map);
      }
      map2.put("status", true);
      map2.put("list", list);
    }
    return map2;
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
        List<OptionVO> optionList = new ArrayList<OptionVO>();
        for (int j = 0; j < option.size(); j++) {
          OptionVO optionVo = OptionVO.builder().oiSeq(option.get(j).getOiSeq())
              .pmName(option.get(j).getPmName())
              .pmcAmount(option.get(j).getPmcAmount())
              .pmPrice(option.get(j).getPmPrice()).build();
          optionList.add(optionVo);
        }
        ListVO vo = new ListVO();
        if (optionList.isEmpty()) {
          vo = ListVO.builder()
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
          vo = ListVO.builder()
          .oiSeq(mlist.get(i).getOiSeq())
          .oiOrderNum(mlist.get(i).getOiOrderNum())
          .miSeq(mlist.get(i).getMiSeq())
          .mniName(mlist.get(i).getMniName())
          .menuAmount(mlist.get(i).getMenuAmount())
          .menuPrice(mlist.get(i).getMenuPrice())
          .mcSeq(mlist.get(i).getMcSeq())
          .oiStatus(mlist.get(i).getOiStatus())
          .optionList(optionList)
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


  // 주문 내역 추가
  public Map<String, Object> addMenu(OrderAddVO data) {
    Map<String, Object> map = new LinkedHashMap<String, Object>();
    if (data.getOiMniSeq() == null) {
      map.put("status", false);
      map.put("message", "메뉴를 입력해 주세요.");
    } else if (data.getOiMiSeq() == null) {
      map.put("status", false);
      map.put("message", "회원번호를 입력해 주세요.");
    } else if (data.getOiMenuAmount() == null) {
      map.put("status", false);
      map.put("message", "메뉴 수량을 입력해 주세요.");
    } else if (data.getOiStatus() == null) {
        map.put("status", false);
        map.put("message", "주문 상태를 입력해 주세요.");
    }
      //  모든 정보가 다 입력이 되었다면,
      else {
        // 입력된 데이터의 가게정보와 이미 입력된 데이터의 가게정보가 같은지 체크 필요
          // 입력된 데이터의 가게 정보
            // 입력한 메뉴 번호로 메뉴 정보를 저장
            MpMenuInfoEntity menu = menuRepo.findByMniSeq(data.getOiMniSeq());
            // 입력한 회원 번호로 회원 정보를 저장
            MbMemberInfoEntity member = memberRepo.findByMiSeq(data.getOiMiSeq());
            // 입력된 메뉴 정보의 카테고리 조인 정보 저장
            MpMenuCateJoinEntity menuCateJoin = menuCateJoinRepo.findByMenu(menu);
            // 입력된 메뉴 정보의 카테고리 정보 저장
            MpMenuCategoryEntity mecuCate = menuCateRepo.findByMcSeq(menuCateJoin.getMenuCate().getMcSeq());
            // 입력된 메뉴 정보의 가게 정보 저장
            MpStoreInfoEntity storeInfo = storeRepo.findBySiSeq(mecuCate.getStore().getSiSeq());
          // 이미 존재하는 가게 정보 
            // 입력된 데이터의 회원 번호와 일치하는 메뉴 선택 정보 찾기
            List<MpMypageMenuChoiceEntity> existMenuchoice = menuChoiceRepo.findByMiSeq(data.getOiMiSeq());
        Boolean check = true;
        // 이미 회원 번호로 등록된 주문 정보가 있을 때
        if (!existMenuchoice.isEmpty()) {
          for (int i = 0; i < existMenuchoice.size(); i++) {
            if (existMenuchoice.get(i).getOiStatus() == 0) {
              // 주문의 메뉴 카테고리를 들고와서 카테고리 정보찾고,
              MpMenuCategoryEntity existMenuCate = menuCateRepo.findByMcSeq(existMenuchoice.get(i).getMcSeq());
              // 카테고리 정보를 통해 가게 정보 저장
              MpStoreInfoEntity existStore = storeRepo.findBySiSeq(existMenuCate.getStore().getSiSeq());
              // 입력된 메뉴의 가게 번호와 회원에게 이미 입력되있는 가게 번호가 일치하지 않는다면
              if (storeInfo.getSiSeq() != existStore.getSiSeq()) {
                // false 값을 반환
                check = false;
                // 반목문에서 빠져나감
                break;
              }
            }
          }
        }
        else {check = true;}
        // oiStatus가 0이라면 장바구니 상태이고, 가게 번호가 일치한다면
        if (data.getOiStatus() == 0 && check) {
          // 주문 entity 새로 생성
          MpOrderInfoEntity order = MpOrderInfoEntity.builder()
              .menu(menu).member(member)
              .oiStatus(data.getOiStatus())
              .oiMenuAmount(data.getOiMenuAmount())
              .oiOrderNum(null)
              .oiOrderDt(null)
              .oiFinishDt(null).build();

          // 저장
          oRepo.save(order);

          // 저장된 메뉴 map에 저장
          map.put("status", true);
          map.put("message", "장바구니가 저장되었습니다.");
          map.put("oiSeq", order.getOiSeq());
        }
        else if (data.getOiStatus() == 0 && !check) {
          map.put("status", false);
          map.put("message", "같은 가게의 메뉴만 장바구니에 담을 수 있습니다.");
        }
        // 만약 oiStatus가 1이고, 결제 완료 상태라면
        else if (data.getOiStatus() == 1) {
          // 주문 entity 새로 생성
          MpOrderInfoEntity order = MpOrderInfoEntity.builder()
          .menu(menu).member(member)
          .oiStatus(data.getOiStatus())
          .oiMenuAmount(data.getOiMenuAmount())
          .oiOrderNum(data.getOiOrderNum())
          .oiOrderDt(data.getOiOrderDt())
          .oiFinishDt(null).build();

          // 저장
          oRepo.save(order);

          // 저장된 메뉴 map에 저장
          map.put("status", true);
          map.put("message", "주문완료 정보가 저장되었습니다.");
          map.put("oiSeq", order.getOiSeq());
        }
        // 나머지 경우 -> 즉, 배달까지 완료된 상태라면
        else {
          // 주문 entity 새로 생성
          MpOrderInfoEntity order = MpOrderInfoEntity.builder()
          .menu(menu).member(member)
          .oiStatus(data.getOiStatus())
          .oiMenuAmount(data.getOiMenuAmount())
          .oiOrderNum(data.getOiOrderNum())
          .oiOrderDt(data.getOiOrderDt())
          .oiFinishDt(data.getOiFinishDt()).build();

          // 저장
          oRepo.save(order);
          map.put("status", true);
          map.put("message", "배달완료 정보가 저장되었습니다.");
          map.put("oiSeq", order.getOiSeq());  
        }
      }
    return map;
  }

  // 주문 옵션 추가
  public Map<String, Object> addOption(OrderAddVO data) {
    Map<String, Object> map = new LinkedHashMap<String, Object>();

    if (data.getPmcOiSeq() == null) {
      map.put("status", false);
      map.put("message", "주문번호를 입력해주세요");
    }
    else if (data.getPmcPmSeq() == null) {
      map.put("status", false);
      map.put("message", "옵션번호를 입력해주세요");
    }
    else {
      MpOrderInfoEntity order = oRepo.findByOiSeq(data.getPmcOiSeq());
      // 선택한 옵션 번호로 옵션 메뉴의 정보를 저장
      MpPlusMenuEntity optionMenu = plustRepo.findByPmSeq(data.getPmcPmSeq());
      // 옵션 선택 테이블 새로 생성
      MpPlusMenuChoiceEntity option = MpPlusMenuChoiceEntity.builder().
      plusMenu(optionMenu).pmcAmount(1).order(order).build();
      // 옵션 선택 저장
      plusChoiceRepo.save(option);
      // 저장된 메뉴 map에 저장
      map.put("status", true);
      map.put("message", "옵션이 저장되었습니다.");
      map.put("pmcSeq", option.getPmcSeq());
    }
    return map;
    }

  // 주문 상태 수정
  public void updateOrderStatus(Integer value, String orderNum) {
    List<MpOrderInfoEntity> entity = oRepo.findAllByOiOrderNum(orderNum);
    // 장바구니로 정보 수정
    if (value == 0) {
      for (int i = 0; i < entity.size(); i++) {
        entity.get(i).setOiStatus(value);
        entity.get(i).setOiOrderNum(null);
        entity.get(i).setOiOrderDt(null);
        entity.get(i).setOiFinishDt(null);
        oRepo.save(entity.get(i));
      }
    } 
    // 주문 완료로 정보 수정 
    else if (value == 1) {
      LocalDate today = LocalDate.now();
        // 숫자의 경우 아스키코드로 변환시 48~57이 0~9로 표현.
        // 로직의 randim.ints() 메소드의 첫번째 파라미터를 48로 지정
        int leftLimit = 48; // numeral '0'
        // 두번째 파라미터는 알파벳의 제일끝이 122이므로 알파벳만 출력할때와 같이 122+1로 셋팅
        int rightLimit = 122; // letter 'z'
        // 길이 제한
        int length = 10;
        Random random = new Random();
        String newOrderNum = random.ints(leftLimit, rightLimit + 1)
        // 알파벳과 숫자만 출력하기위해 filter() 메소드를 활용해서 아스키코드의 범위를 제한
        .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
        // 문자열 길이를 limit()메소드로 제한
        .limit(length)
        //  collect() 메소드로 StringBuilder 객체를 생성
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        //  StringBuilder 객체를 toString() 으로 문자열로 변환
        .toString();
      for (int i = 0; i < entity.size(); i++) {
        entity.get(i).setOiStatus(value);
        entity.get(i).setOiOrderDt(today);
        entity.get(i).setOiOrderNum(newOrderNum);
        oRepo.save(entity.get(i));
      }
    }
    // 배달 완료로 정보수정
    else {
      for (int i = 0; i < entity.size(); i++) {
        entity.get(i).setOiStatus(value);
        entity.get(i).setOiFinishDt(LocalDate.now());
        oRepo.save(entity.get(i));
      }
    }
  }

  // 주문 내역 삭제
  public void deleteOrder(String orderNum) {
    // 주문번호가 일치하는 내역을 찾아서 저장
    List<MpOrderInfoEntity> entity = oRepo.findAllByOiOrderNum(orderNum);
    // 내역 안의 메뉴정보를 전부 삭제
    for (int i = 0; i < entity.size(); i++) {
      oRepo.deleteById(entity.get(i).getOiSeq());
      // 메뉴정보에 걸린 옵션 정보를 찾아서 저장
      List<MpPlusMenuChoiceEntity> option = plusChoiceRepo.findByOrder(entity.get(i));
      // 만약 옵션 선택이 있다면,
      if (!option.isEmpty()) {
        // 옵션의 수만큼 반복문을 돌려 옵션 삭제
        for (int j = 0; j < option.size(); j++) {
          plusChoiceRepo.delete(option.get(j));
        }
      }
    }
  }
}