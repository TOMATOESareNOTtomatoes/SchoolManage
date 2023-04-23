package com.fanqie.commonutils.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PermissionEnum {
   /**
    * 管理员
    */
   ADMIN("2", "管理员权限"),

   /**
    * userPlus
    */
   UserPlus("1", "院长权限"),

   /**
    * 无需校验,
    */
   NO("0", "无需权限"),
   ;
   /**
    * 权限编码
    */
   private String code;
   /**
    * 权限名称
    */
   private String msg;
}