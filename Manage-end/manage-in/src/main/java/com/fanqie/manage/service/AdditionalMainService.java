package com.fanqie.manage.service;

import com.fanqie.manage.entity.AdditionalMain;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fanqie.manage.param.acSure;
import com.fanqie.manage.param.soleAndUser;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fq
 * @since 2023-05-04
 */
public interface AdditionalMainService extends IService<AdditionalMain> {

    /**
     * 是否有效、 有效就返回、无效就null  有效：is_Sure=1 或者5 或者9 而且不被删除
     * @param additional
     * @return
     */
    AdditionalMain getByAdditionalId(String additional);

    /**
     * 返回  管理员  确认的特殊情况列表  is_sure值为 1
     * @return
     */
    List<acSure> getAdditionalSure();

    /**
     * 通过院系，查询对应的特殊情况确认列表
     * @param faculty
     * @return  特殊情况列表
     */
    List<acSure> getByFaculty(String faculty);

    /**
     * 修改表的数据
     * @param soleAndUser 有三个属性  id userId faculty
     * @return 修改的数量
     */
    int updateByAdditionalId(soleAndUser soleAndUser);
}
