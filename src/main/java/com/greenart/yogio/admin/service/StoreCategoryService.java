package com.greenart.yogio.admin.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.greenart.yogio.admin.entity.StoreCategoryEntity;
import com.greenart.yogio.admin.repository.StoreCategoryRepository;
import com.greenart.yogio.admin.vo.store.StoreCategoryVO;

@Service
public class StoreCategoryService {
    @Value("${file.image.cate}") String cate_img_path;
    @Autowired StoreCategoryRepository storeCategoryRepository;
    public Map<String,Object> getCateList(Pageable pageable){
        Page<StoreCategoryEntity> page= storeCategoryRepository.findAll(pageable);
        // List<StoreCategoryEntity> cateList = storeCategoryRepository.findAll();
        Map<String,Object> resultMap = new LinkedHashMap<String, Object>();
        resultMap.put("status", true);
        resultMap.put("cateList", page.getContent());
        resultMap.put("total", page.getTotalElements());
        resultMap.put("totalpage", page.getTotalPages());
        resultMap.put("currentPage", page.getNumber());
        return resultMap;
    }

    public void addCate (StoreCategoryVO data){
    MultipartFile file = data.getFile();
    Path folderLocation = Paths.get(cate_img_path);
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
    StoreCategoryEntity entity = StoreCategoryEntity.builder().scName(data.getScName()).scImage(filename).scFileName(saveFilename).build();
    storeCategoryRepository.save(entity);

    }

}
