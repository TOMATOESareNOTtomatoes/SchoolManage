package com.fanqie.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanqie.manage.entity.AdditionalMain;
import com.fanqie.manage.mapper.AdditionalMainMapper;
import com.fanqie.manage.param.acSure;
import com.fanqie.manage.service.AdditionalMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fq
 * @since 2023-05-04
 */
@Service
public class AdditionalMainServiceImpl extends ServiceImpl<AdditionalMainMapper, AdditionalMain> implements AdditionalMainService {

    @Autowired
    AdditionalMainMapper mapper;

    /**
     * 是否有效、 有效就返回、无效就null  有效：is_Sure=1 或者5 或者9 而且不被删除
     *
     * @param additional
     * @return
     */
    @Override
    public AdditionalMain getByAdditionalId(String additional) {
        return mapper.selectOne(
                new QueryWrapper<AdditionalMain>()
                        .eq("additional_id", additional)
                        .eq("is_delete", 0)
                        .and(wrapper -> wrapper
                                .eq("is_sure", 1)
                                .or()
                                .eq("is_sure", 5)
                                .or()
                                .eq("is_sure", 9)
                        )
        );
    }

    /**
     * 返回院长确认的特殊情况列表  is_sure值为 1
     *
     * @return
     */
    @Override
    public List<acSure> getAdditionalSure() {
        return mapper.getAdditionalSure();
    }

    /**
     * 通过院系，查询对应的特殊情况确认列表
     *
     * @param faculty
     * @return 特殊情况列表
     */
    @Override
    public List<acSure> getByFaculty(String faculty) {
        return mapper.getAdditionalSureByFaculty(faculty);
    }


}
