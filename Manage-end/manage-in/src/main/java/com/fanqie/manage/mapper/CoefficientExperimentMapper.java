package com.fanqie.manage.mapper;

import com.fanqie.manage.entity.CoefficientExperiment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fq
 * @since 2023-03-30
 */
public interface CoefficientExperimentMapper extends BaseMapper<CoefficientExperiment> {

    CoefficientExperiment getByClassNumber(int classNumber);
    
}
