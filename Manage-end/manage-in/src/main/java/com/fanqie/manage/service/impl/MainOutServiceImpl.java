package com.fanqie.manage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanqie.commonutils.utils.R;
import com.fanqie.manage.entity.MainOut;
import com.fanqie.manage.mapper.MainOutMapper;
import com.fanqie.manage.param.userDoInfo;
import com.fanqie.manage.service.MainOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 'ssm23javatest.main_all' is not BASE TABLE 服务实现类
 * </p>
 *
 * @author fq
 * @since 2023-04-10
 */
@Service
public class MainOutServiceImpl extends ServiceImpl<MainOutMapper, MainOut> implements MainOutService {

    @Autowired
    MainOutMapper mainOutMapper;

    @Override
    public userDoInfo seleceByUserId(String userId) {
        return null;
    }

    @Override
    public int Insert(MainOut mainOut) {
        return mainOutMapper.Insert(mainOut) ;
    }

    /**
     * 
     * @return
     */
    @Override
    public R AllUser() {
        return null;
    }
}
