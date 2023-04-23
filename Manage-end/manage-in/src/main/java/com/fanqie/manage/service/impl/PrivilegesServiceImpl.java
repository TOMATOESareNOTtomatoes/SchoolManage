package com.fanqie.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanqie.manage.entity.Privileges;
import com.fanqie.manage.mapper.PrivilegesMapper;
import com.fanqie.manage.service.PrivilegesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fq
 * @since 2023-04-03
 */
@Service
public class PrivilegesServiceImpl extends ServiceImpl<PrivilegesMapper, Privileges> implements PrivilegesService {

    @Autowired
    PrivilegesMapper privilegesMapper;

    /**
     * 根据 权限名  返回  权限id
     * @param pName
     * @return
     */
    @Override
    public String getPrivilegesIdByName(String pName) {
        QueryWrapper<Privileges> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("permissions", pName);
        Privileges privileges = privilegesMapper.selectOne(queryWrapper);
        if (privileges != null) {
            return privileges.getPrivilegesId();
        }
        return null;
    }
}
