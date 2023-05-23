package com.fanqie.manage.service.impl;

import com.fanqie.manage.entity.CalculationProcess;
import com.fanqie.manage.mapper.CalculationProcessMapper;
import com.fanqie.manage.service.CalculationProcessService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fq
 * @since 2023-05-02
 */
@Service
public class CalculationProcessServiceImpl extends ServiceImpl<CalculationProcessMapper, CalculationProcess> implements CalculationProcessService {

    @Autowired
    CalculationProcessMapper mapper;

    @Override
    public int Insert(CalculationProcess calculationProcess) {
        return mapper.insert(calculationProcess);
    }
}
