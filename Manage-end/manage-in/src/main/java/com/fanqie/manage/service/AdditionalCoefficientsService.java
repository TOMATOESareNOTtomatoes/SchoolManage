package com.fanqie.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fanqie.manage.entity.AdditionalCoefficients;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fq
 * @since 2023-03-30
 */

public interface AdditionalCoefficientsService extends IService<AdditionalCoefficients> {

    /**
     * 通过 acId 返回系数
     * @param additional
     * @return
     */
    AdditionalCoefficients selectByAdditionalId(String additional);

}
