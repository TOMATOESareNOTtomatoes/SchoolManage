package com.fanqie.manage.service;

import com.fanqie.manage.entity.CalculationProcess;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fq
 * @since 2023-05-02
 */
public interface CalculationProcessService extends IService<CalculationProcess> {

    /**
     * 添加记录、计算过程保存。
     * @param calculationProcess
     */
    void Insert(CalculationProcess calculationProcess);
}
