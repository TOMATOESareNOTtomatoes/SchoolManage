package com.fanqie.manage.service.impl;

import com.fanqie.manage.entity.CoefficientTheory;
import com.fanqie.manage.mapper.CoefficientTheoryMapper;
import com.fanqie.manage.service.CoefficientTheoryService;
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
public class CoefficientTheoryServiceImpl extends ServiceImpl<CoefficientTheoryMapper, CoefficientTheory> implements CoefficientTheoryService {

    @Autowired
    CoefficientTheoryMapper coefficientTheoryMapper;

    @Override
    public CoefficientTheory getByClassNumber(int i) {
        return coefficientTheoryMapper.getByClassNumber(i);
    }
}
