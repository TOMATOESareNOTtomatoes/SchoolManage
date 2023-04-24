package com.fanqie.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanqie.commonutils.utils.JwtUtils;
import com.fanqie.commonutils.utils.PermissionEnum;
import com.fanqie.commonutils.utils.R;
import com.fanqie.manage.entity.PrivilegesUser;
import com.fanqie.manage.mapper.PrivilegesUserMapper;
import com.fanqie.manage.service.PrivilegesUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fq
 * @since 2023-04-03
 */
@Service
public class PrivilegesUserServiceImpl extends ServiceImpl<PrivilegesUserMapper, PrivilegesUser> implements PrivilegesUserService {

    @Autowired
    private PrivilegesUserMapper privilegesUserMapper;

    /**
     * 返回用户的权限信息。
     * @param request
     * @return
     */
    @Override
    public R getPrivilegesByToken(HttpServletRequest request) {
        String memberid= JwtUtils.getMemberIdByJwtToken(request);
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("user_id",memberid);
        PrivilegesUser privilegesUser = privilegesUserMapper.selectOne(queryWrapper);
        if(privilegesUser==null){
            //说明没有权限记录。默认给普通用户。
            PrivilegesUser user=new PrivilegesUser();
            user.setUserId(memberid);
            user.setPrivilegesId("0");
            int i=privilegesUserMapper.insert(user);
            if(i!=1){
                System.out.println("添加用户权限信息失败");
            }
            PrivilegesUser user2=new PrivilegesUser();
            user2=privilegesUserMapper.selectOne(queryWrapper);
            if(user2==null){
                System.out.println("查询用户权限信息失败");
                return R.error();
            }
            System.out.println("---------"+user2);
            return R.ok().data("userInfo",user2);
        }
        System.out.println("--------"+privilegesUser);
        return R.ok().data("userInfo",privilegesUser);
    }

    /**
     * 更新用户权限
     * @param privilegesUser
     * @return
     */
    @Override
    public boolean Update(PrivilegesUser privilegesUser) {
        QueryWrapper<PrivilegesUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", privilegesUser.getUserId());
        int rows = privilegesUserMapper.update(privilegesUser, queryWrapper);
        //System.out.println("rows:"+rows);
        return rows > 0;
    }

    @Override
    public boolean checkPermissionForUser(String userId, PermissionEnum[] permissionEnums) {
        System.out.println("进入了权限匹对方法");
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("user_id",userId);
        PrivilegesUser privilegesUser = privilegesUserMapper.selectOne(queryWrapper);
        if(privilegesUser==null){
            //说明没有权限记录。默认给普通用户。
            PrivilegesUser user=new PrivilegesUser();
            user.setUserId(userId);
            user.setPrivilegesId("0");
            int i=privilegesUserMapper.insert(user);
            if(i!=1){
                System.out.println("添加用户权限信息失败");
            }
            PrivilegesUser user2=new PrivilegesUser();
            user2=privilegesUserMapper.selectOne(queryWrapper);
            if(user2==null){
                System.out.println("查询用户权限信息失败");
                return false;
            }
            String s=user2.getPrivilegesId();
            System.out.println("权限内容；"+permissionEnums);
            return Arrays.equals(s.split(","), permissionEnums);
        }
        return false;
    }

    /**
     * 根据用户的id 查询/返回  用户的权限
     * 没有查询到的也添加了基础权限。TODO：将这个添加基础权限写成一个方法比较好
     * @param userId
     * @return
     */
    @Override
    public String getPrivilegesByUserId(String userId) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("user_id",userId);
        PrivilegesUser privilegesUser = privilegesUserMapper.selectOne(queryWrapper);
        if(privilegesUser==null) {
            //说明没有权限记录。默认给普通用户。
            PrivilegesUser user = new PrivilegesUser();
            user.setUserId(userId);
            user.setPrivilegesId("0");
            int i = privilegesUserMapper.insert(user);
            if (i != 1) {
                System.out.println("添加用户权限信息失败");
            }
            PrivilegesUser user2 = privilegesUserMapper.selectOne(queryWrapper);
            return user2.getPrivilegesId();
        }
        return privilegesUser.getPrivilegesId();
    }

    @Override
    public String getPrivilegesById(String id) {
        return null;
    }
}
