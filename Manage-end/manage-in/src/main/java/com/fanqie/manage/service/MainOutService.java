package com.fanqie.manage.service;

import com.fanqie.commonutils.utils.R;
import com.fanqie.manage.entity.MainOut;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fanqie.manage.param.userDoInfo;

/**
 * <p>
 * 'ssm23javatest.main_all' is not BASE TABLE 服务类
 * </p>
 *
 * @author fq
 * @since 2023-04-10
 */
public interface MainOutService extends IService<MainOut> {

    userDoInfo seleceByUserId(String userId);

    int Insert(MainOut mainOut);

    //返回所有已确认的工作量信息。
    R AllUser();
}
