package com.fanqie.manage.mapper;

import com.fanqie.manage.entity.CoefficientTheory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fq
 * @since 2023-03-30
 */
public interface CoefficientTheoryMapper extends BaseMapper<CoefficientTheory> {

    CoefficientTheory getByClassNumber(int classNumber);
}
