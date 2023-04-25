package com.fanqie.manage.mapper;

import com.fanqie.manage.entity.AdditionalCoefficients;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fanqie.manage.param.acSure;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fq
 * @since 2023-03-30
 */
@Mapper
@Repository
public interface AdditionalCoefficientsMapper extends BaseMapper<AdditionalCoefficients> {

    AdditionalCoefficients getByAdditionalId(String additional);

    int selectByrAdditionalId(String additional);

    List<acSure> getAdditionalSure();

    //通过的
    int updateIsSureByAdditionalId(String additionalId);
    //不通过 的
    int updateUnSureByAdditionalId(String additionalId);
}
