package com.fanqie.manage.service.impl;

import com.fanqie.manage.entity.CoefficientPractice;
import com.fanqie.manage.mapper.CoefficientPracticeMapper;
import com.fanqie.manage.service.CoefficientPracticeService;
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
public class CoefficientPracticeServiceImpl extends ServiceImpl<CoefficientPracticeMapper, CoefficientPractice> implements CoefficientPracticeService {

    @Autowired
    CoefficientPracticeMapper coefficientPracticeMapper;
    @Override
    public CoefficientPractice getBYClassNumber(int classNumber) {
        return coefficientPracticeMapper.getByClassNumber(classNumber);
    }
}
