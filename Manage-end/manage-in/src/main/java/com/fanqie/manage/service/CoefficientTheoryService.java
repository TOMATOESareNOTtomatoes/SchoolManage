package com.fanqie.manage.service;

import com.fanqie.manage.entity.CoefficientTheory;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fq
 * @since 2023-03-30
 */
public interface CoefficientTheoryService extends IService<CoefficientTheory> {

    CoefficientTheory getByClassNumber(int i);
}
