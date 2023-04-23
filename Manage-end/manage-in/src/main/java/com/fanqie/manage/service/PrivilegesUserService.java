package com.fanqie.manage.service;

import com.fanqie.commonutils.utils.PermissionEnum;
import com.fanqie.commonutils.utils.R;
import com.fanqie.manage.entity.PrivilegesUser;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fq
 * @since 2023-04-03
 */
public interface PrivilegesUserService extends IService<PrivilegesUser> {

    R getPrivilegesByToken(HttpServletRequest request);

    boolean Update(PrivilegesUser privilegesUser);

    boolean checkPermissionForUser(String token, PermissionEnum[] permissionEnums);

    String getPrivilegesByUserId(String userId);
}
