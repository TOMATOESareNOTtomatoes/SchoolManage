package com.fanqie.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanqie.manage.entity.AdditionalCoefficients;
import com.fanqie.manage.mapper.AdditionalCoefficientsMapper;
import com.fanqie.manage.service.AdditionalCoefficientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fq
 * @since 2023-03-30
 */
@Service
public class AdditionalCoefficientsServiceImpl extends ServiceImpl<AdditionalCoefficientsMapper, AdditionalCoefficients> implements AdditionalCoefficientsService {

    @Autowired
    AdditionalCoefficientsMapper mapper;

    /**
     * 通过 acId 返回系数
     *
     * @param additional_coefficients_id
     * @return
     */
    @Override
    public AdditionalCoefficients selectByAdditionalId(String additional_coefficients_id) {
        QueryWrapper<AdditionalCoefficients> wrapper = new QueryWrapper<>();
        wrapper.eq("additional_coefficients_id", additional_coefficients_id);
        return mapper.selectOne(wrapper);
    }
}
