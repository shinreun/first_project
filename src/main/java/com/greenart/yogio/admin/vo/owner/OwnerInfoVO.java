
package com.greenart.yogio.admin.vo.owner;



import com.greenart.yogio.admin.entity.OwnerInfoEntity;

import lombok.Data;

@Data
public class OwnerInfoVO {
private Long owiSeq;
private String owiId;
private String owiPwd;
private String owiEmail;
private String owiPhone;
private String owiNickName;
private Long owiSiSeq;

public OwnerInfoVO(OwnerInfoEntity entity){
this.owiSeq = entity.getOwiSeq();
this.owiId = entity.getOwiId();
this.owiPwd = entity.getOwiId();
this.owiEmail = entity.getOwiEmail();
this.owiPhone = entity.getOwiPhone();
this.owiNickName = entity.getOwiNickName();
this.owiSiSeq = entity.getOwiSiSeq();

}
}
