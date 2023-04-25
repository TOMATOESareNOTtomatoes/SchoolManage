package com.fanqie.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fanqie.manage.entity.AdditionalCoefficients;
import com.fanqie.manage.param.acSure;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fq
 * @since 2023-03-30
 */

public interface AdditionalCoefficientsService extends IService<AdditionalCoefficients> {

    AdditionalCoefficients getByAdditionalId(String additional);

    int addAdditional(AdditionalCoefficients ac);

    //通过additionalId查询是否存在未确认的特殊情况申请，存在的话，不能通过确认。
    int sureUnSureByAdditionalId(String additional);

    List<acSure> getAdditionalSure();

    //通过的
    int updateIsSureByAdditionalId(String additionalId);

    //不通过的
    int updateNoSureByAdditionalId(String additionalId);
}
