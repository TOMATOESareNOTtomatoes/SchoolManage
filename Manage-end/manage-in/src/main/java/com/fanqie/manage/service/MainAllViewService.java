package com.fanqie.manage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fanqie.manage.entity.MainAllView;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * VIEW 服务类
 * </p>
 *
 * @author fq
 * @since 2023-04-10
 */

public interface MainAllViewService extends IService<MainAllView> {

     List<MainAllView> queryAll();
    /**
 * 根据UserId查询，获得MainAllView对象的列表
 * @param userId 用户Id
 * @return MainAllView对象的列表
 */
 List<MainAllView> queryByUserId(String userId);

    /**
     * 根据条件查询
     * @param wrapper
     * @return
     */
    List<MainAllView> selectList(QueryWrapper<MainAllView> wrapper);

}
