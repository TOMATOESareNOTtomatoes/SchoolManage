package com.fanqie.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanqie.commonutils.param.UserCheckParam;
import com.fanqie.commonutils.utils.R;
import com.fanqie.manage.entity.MainOut;
import com.fanqie.manage.mapper.MainOutMapper;
import com.fanqie.manage.param.userDoInfo;
import com.fanqie.manage.service.MainOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    MainOutMapper mapper;

    @Override
    public userDoInfo seleceByUserId(String userId) {
        return null;
    }

    @Override
    public int Insert(MainOut mainOut) {
        return mapper.Insert(mainOut) ;
    }

    /**
     * 
     * @return
     */
    @Override
    public R AllUser() {
        return R.ok().message("接口正常");
    }

    /**
     * 管理员获取需要确认的用户的信息 。
     * 流程：  直接查看数据库中被院长确认过的数据。这里是通过isSure 为 3 查询出来的。
     *          当然理论可能是通过设置多个字段实现多种情况
     * @return
     */
    @Override
    public R getAllToSure() {
    List<MainOut> mainOutList = mapper.selectList(new QueryWrapper<MainOut>()
            .eq("isSure", 3)
    );
    return R.ok().data("mainOutList", mainOutList).message("接口正常");
}

    @Override
    public R getAllToSureByFaculty(UserCheckParam userCheckParam) {
        return R.ok().message("接口正常");
    }

    @Override
    public R upDataByMainOut(MainOut mainOut) {
        return R.ok().message("接口正常");
    }

    @Override
    public R UserUpDataByMainOut(MainOut mainOut) {
        return R.ok().message("接口正常");
    }
}
