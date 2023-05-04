package com.fanqie.manage.service;

import com.fanqie.manage.entity.AdditionalMain;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fanqie.manage.param.acSure;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fq
 * @since 2023-05-04
 */
public interface AdditionalMainService extends IService<AdditionalMain> {

    /**
     * 是否有效、 有效就返回、无效就null  有效：is_Sure=1 或者5 或者9 而且不被删除
     * @param additional
     * @return
     */
    AdditionalMain getByAdditionalId(String additional);

    /**
     * 返回院长确认的特殊情况列表  is_sure值为 1
     * @return
     */
    List<acSure> getAdditionalSure();
}
