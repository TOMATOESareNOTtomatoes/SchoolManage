package com.fanqie.manage.service;

import com.fanqie.manage.entity.CoefficientPractice;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fq
 * @since 2023-03-30
 */
public interface CoefficientPracticeService extends IService<CoefficientPractice> {

    CoefficientPractice getBYClassNumber(int i);
}
