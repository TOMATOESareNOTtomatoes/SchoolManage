package com.fanqie.manage.service;

import com.fanqie.manage.entity.Privileges;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fq
 * @since 2023-04-03
 */
public interface PrivilegesService extends IService<Privileges> {

    /*
     * 根据权限名返回权限id
     */
    String getPrivilegesIdByName(String pName);
}
