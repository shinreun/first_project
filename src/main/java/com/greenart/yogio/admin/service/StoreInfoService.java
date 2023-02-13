package com.greenart.yogio.admin.service;

import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.greenart.yogio.admin.entity.BusinessInfoEntity;
import com.greenart.yogio.admin.entity.DeliveryInfoEntity;
import com.greenart.yogio.admin.entity.OwnerInfoEntity;
import com.greenart.yogio.admin.entity.OwnerNotinceImgEntity;
import com.greenart.yogio.admin.entity.StoreCateJoinEntity;
import com.greenart.yogio.admin.entity.StoreCategoryEntity;
import com.greenart.yogio.admin.entity.StoreDetailInfoEntity;
import com.greenart.yogio.admin.entity.StoreInfoEntity;
import com.greenart.yogio.admin.repository.BusinessRepostitory;
import com.greenart.yogio.admin.repository.DeliveryInfoRepository;
import com.greenart.yogio.admin.repository.OwnerInfoRepostitory;
import com.greenart.yogio.admin.repository.OwnerNoticeImgRepostiory;
import com.greenart.yogio.admin.repository.StoreCateJoinRepostiory;
import com.greenart.yogio.admin.repository.StoreCategoryRepository;
import com.greenart.yogio.admin.repository.StoreDetailInfoRepostitory;
import com.greenart.yogio.admin.repository.StoreInfoRepository;
import com.greenart.yogio.admin.vo.owner.OwnerInfoVO;
import com.greenart.yogio.admin.vo.owner.OwnerUpdateVO;
import com.greenart.yogio.admin.vo.store.StoreDetailInfoVO;
import com.greenart.yogio.admin.vo.store.StoreInfoVO;


import jakarta.servlet.http.HttpSession;

@Service
public class StoreInfoService  {
    @Autowired OwnerInfoRepostitory ownerInfoRepostitory;
    @Autowired DeliveryInfoRepository deliveryInfoRepository;
    @Autowired StoreInfoRepository storeInfoRepository;
    @Autowired StoreCateJoinRepostiory storeCateJoinRepostiory;
    @Autowired StoreCategoryRepository storeCategoryRepository;
    @Autowired StoreDetailInfoRepostitory storeDetailInfoRepostitory;
    @Autowired OwnerNoticeImgRepostiory ownerNoticeImgRepostiory;
    @Autowired BusinessRepostitory businessRepostitory;
    @Value("${file.image.store}") String store_img_path;
    @Value("${file.image.notice}") String notice_img_path;

    public Map<String,Object> addStore (StoreInfoVO data, HttpSession session){
      Map<String,Object> map = new LinkedHashMap<String, Object>();
    OwnerInfoVO owner = (OwnerInfoVO)session.getAttribute("loginUser");
    StoreCategoryEntity centity = storeCategoryRepository.findByScNameContains(data.getScName());
    Optional <OwnerInfoEntity> oentity = ownerInfoRepostitory.findById(owner.getOwiSeq());
    MultipartFile file = data.getFile();
    Path folderLocation = Paths.get(store_img_path);
    String originFileName = file.getOriginalFilename();
    String [] split = originFileName.split("\\.");
    String ext = split[split.length - 1];
    String filename = "";
    for(int i = 0; i<split.length-1; i++) {
        filename += split[i];
    }
    String saveFilename = "";
    Calendar c = Calendar.getInstance();
    saveFilename += c.getTimeInMillis()+"."+ext;
    Path targetFile = folderLocation.resolve(saveFilename);
    try{
    Files.copy(file.getInputStream(), targetFile,StandardCopyOption.REPLACE_EXISTING);
    } catch (Exception e) {
      e.printStackTrace();
    }
    DeliveryInfoEntity dEntity = new DeliveryInfoEntity(null,data.getDiDistance(),data.getDiDeliveryPrice(),data.getDiTime());
    deliveryInfoRepository.save(dEntity);

    data.setSiDiSeq(dEntity.getDiSeq());
    StoreInfoEntity entity = new StoreInfoEntity(null,data.getSiName(),filename,data.getSiMinOrderPrice(),
    data.getSiDiscountPrice(),data.getSiDiscountCondition(),data.getSiDiSeq(),data.getSiCleanInfo(),saveFilename);
    storeInfoRepository.save(entity);
    OwnerInfoEntity e  =  oentity.get();
     e.setOwiSiSeq(entity.getSiSeq());  
     ownerInfoRepostitory.save(e);
    StoreCateJoinEntity sc = new StoreCateJoinEntity(null,centity.getScSeq(), entity.getSiSeq());
    storeCateJoinRepostiory.save(sc);

    map.put("status", true);
    map.put("message", "가게 추가에 성공했습니다.");
    return map;
  }
  
      public StoreInfoEntity getStoreInfo (Long store_no){
        return storeInfoRepository.findById(store_no).get();
    }
       public DeliveryInfoEntity getDeliveryInfo (Long delivery_no){
        return deliveryInfoRepository.findById(delivery_no).get();
    }
     public Map<String, Object> updateStoreInfo(StoreInfoVO data , HttpSession session) {
        Map <String,Object> resultMap = new LinkedHashMap<String, Object>();
        OwnerInfoVO owner = (OwnerInfoVO)session.getAttribute("loginUser");
        Optional <StoreInfoEntity> entity = storeInfoRepository.findById(owner.getOwiSiSeq());
        Optional <DeliveryInfoEntity> dentity = deliveryInfoRepository.findById(entity.get().getSiDiSeq());
        MultipartFile file = data.getFile();
        Path folderLocation = Paths.get(store_img_path);
        String originFileName = file.getOriginalFilename();
        String [] split = originFileName.split("\\.");
        String ext = split[split.length - 1];
        String filename = "";
        for(int i = 0; i<split.length-1; i++) {
            filename += split[i];
        }
        String saveFilename = "";
        Calendar c = Calendar.getInstance();
        saveFilename += c.getTimeInMillis()+"."+ext;
        Path targetFile = folderLocation.resolve(saveFilename);
        try{
        Files.copy(file.getInputStream(), targetFile,StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
          e.printStackTrace();
        }
            StoreInfoEntity e  =  entity.get();
            e.setSiSeq(data.getSiSeq());
            e.setSiDiscountPrice(data.getSiDiscountPrice());
            e.setSiDiscountCondition(data.getSiDiscountCondition());
            e.setSiCleanInfo(data.getSiCleanInfo());
            e.setSiMinOrderPrice(data.getSiMinOrderPrice());
            e.setSiUri(filename);
            e.setSiFileName(saveFilename);
            storeInfoRepository.save(e);
            DeliveryInfoEntity d = dentity.get();
            d.setDiTime(data.getDiTime());
            d.setDiDeliveryPrice(data.getDiDeliveryPrice());
            d.setDiDistance(data.getDiDistance());
            deliveryInfoRepository.save(d);
            resultMap.put("status", true);
        
        return resultMap;
    }
   public Map<String,Object> getStoreList(Pageable pageable){
        Page<StoreInfoEntity> page= storeInfoRepository.findAll(pageable);
        Map<String,Object> resultMap = new LinkedHashMap<String, Object>();
        resultMap.put("status", true);
        resultMap.put("list", page.getContent());
        resultMap.put("total", page.getTotalElements());
        resultMap.put("totalpage", page.getTotalPages());
        resultMap.put("currentPage", page.getNumber());
        resultMap.put("storeList", page);
        return resultMap;
    }
      public void deleteStore (Long store_no) {
        storeInfoRepository.deleteById(store_no);
    }

    public Map<String,Object> addStoreDetail (StoreDetailInfoVO data, HttpSession session){
      Map<String,Object> map = new LinkedHashMap<String, Object>();
    OwnerInfoVO owner = (OwnerInfoVO)session.getAttribute("loginUser");
    // StoreCategoryEntity centity = storeCategoryRepository.findByScNameContains(data.getScName());
    // Optional <OwnerInfoEntity> oentity = ownerInfoRepostitory.findById(owner.getOwiSeq());
    MultipartFile file = data.getFile();
    Path folderLocation = Paths.get(notice_img_path);
    String originFileName = file.getOriginalFilename();
    String [] split = originFileName.split("\\.");
    String ext = split[split.length - 1];
    String filename = "";
    for(int i = 0; i<split.length-1; i++) {
        filename += split[i];
    }
    String saveFilename = "";
    Calendar c = Calendar.getInstance();
    saveFilename += c.getTimeInMillis()+"."+ext;
    Path targetFile = folderLocation.resolve(saveFilename);
    try{
    Files.copy(file.getInputStream(), targetFile,StandardCopyOption.REPLACE_EXISTING);
    } catch (Exception e) {
      e.printStackTrace();
    }
    BusinessInfoEntity bentity = new BusinessInfoEntity(null, data.getBiName(), data.getBiOwner(), data.getBiBusinessNumber());
    businessRepostitory.save(bentity);
    StoreDetailInfoEntity entity =  new StoreDetailInfoEntity(null, owner.getOwiSiSeq(),data.getSdiOpenClose(), data.getSdiPhone(),data.getSdiAdress(), 
    bentity.getBiSeq(), data.getSdiPayment(), data.getSdiPacking(), data.getSdiOrigin(), data.getSdiOwnerNotice());
    storeDetailInfoRepostitory.save(entity);
    OwnerNotinceImgEntity ownerentity = new OwnerNotinceImgEntity(null, entity.getSdiSeq(), filename, null);
    ownerNoticeImgRepostiory.save(ownerentity);
    map.put("status", true);
    map.put("message", "가게 추가에 성공했습니다.");
    return map;
  }

}
