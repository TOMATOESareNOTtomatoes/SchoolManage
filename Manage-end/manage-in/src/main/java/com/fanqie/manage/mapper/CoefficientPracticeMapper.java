package com.fanqie.manage.mapper;

import com.fanqie.manage.entity.CoefficientPractice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fq
 * @since 2023-03-30
 */
public interface CoefficientPracticeMapper extends BaseMapper<CoefficientPractice> {
    CoefficientPractice getByClassNumber(int i);
}
