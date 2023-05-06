package com.fanqie.manage.service;

import com.fanqie.manage.entity.MyView;
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
public interface MyViewService extends IService<MyView> {

    /**
     * 获取全部信息
     * @return
     */
    List<MyView> getList();
}
