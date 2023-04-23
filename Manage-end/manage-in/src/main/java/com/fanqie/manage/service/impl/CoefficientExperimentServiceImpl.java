package com.fanqie.manage.service.impl;

import com.fanqie.manage.entity.CoefficientExperiment;
import com.fanqie.manage.mapper.CoefficientExperimentMapper;
import com.fanqie.manage.service.CoefficientExperimentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fq
 * @since 2023-03-30
 */
@Service
public class CoefficientExperimentServiceImpl extends ServiceImpl<CoefficientExperimentMapper, CoefficientExperiment> implements CoefficientExperimentService {

    @Autowired
    CoefficientExperimentMapper coefficientExperimentMapper;

    @Override
    public CoefficientExperiment getByClassNumber(int classRow) {
        return coefficientExperimentMapper.getByClassNumber(classRow);
    }
}
