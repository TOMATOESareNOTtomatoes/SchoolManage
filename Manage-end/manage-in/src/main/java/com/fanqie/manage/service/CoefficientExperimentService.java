package com.fanqie.manage.service;

import com.fanqie.manage.entity.CoefficientExperiment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fq
 * @since 2023-03-30
 */
public interface CoefficientExperimentService extends IService<CoefficientExperiment> {

    CoefficientExperiment getByClassNumber(int classRow);
}
