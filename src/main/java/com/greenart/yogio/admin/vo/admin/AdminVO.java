
package com.greenart.yogio.admin.vo.admin;



import com.greenart.yogio.admin.entity.AdminInfoEntity;

import lombok.Data;

@Data
public class AdminVO {
private  Long ai_seq;
private  String ai_id;
private  String ai_name;
private  Integer ai_status;
private  Integer ai_grade;

public AdminVO (AdminInfoEntity entity) {
this.ai_seq= entity.getAiSeq();
this.ai_id= entity.getAiId();
this.ai_name= entity.getAiName();
this.ai_status= entity.getAiStatus();
this.ai_grade= entity.getAiGrade();

}


    
}
