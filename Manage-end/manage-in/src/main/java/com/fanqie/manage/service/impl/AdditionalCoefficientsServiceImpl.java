package com.fanqie.manage.service.impl;

import com.fanqie.manage.entity.AdditionalCoefficients;
import com.fanqie.manage.mapper.AdditionalCoefficientsMapper;
import com.fanqie.manage.param.acSure;
import com.fanqie.manage.service.AdditionalCoefficientsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
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
     * 通过唯一id 返回特殊情况表。
     * @param additional
     * @return
     */
    @Override
    public AdditionalCoefficients getByAdditionalId(String additional) {
        return mapper.getByAdditionalId(additional);
    }

    /**
     * 添加记录
     * @param ac
     * @return
     */
    @Override
    public int addAdditional(AdditionalCoefficients ac) {
        return mapper.insert(ac);
    }

    //通过additionalId查询是否存在未确认的特殊情况申请，存在的话，不能通过确认。
    @Override
    public int sureUnSureByAdditionalId(String additional) {
        return mapper.selectByrAdditionalId(additional);
    }

    //获取用户的特殊情况申请，返回列表
    @Override
    public List<acSure> getAdditionalSure() {
        return mapper.getAdditionalSure();
    }
}
