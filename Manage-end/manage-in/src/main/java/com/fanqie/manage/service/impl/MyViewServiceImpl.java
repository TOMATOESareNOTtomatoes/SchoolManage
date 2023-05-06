package com.fanqie.manage.service.impl;

import com.fanqie.manage.entity.MyView;
import com.fanqie.manage.mapper.MyViewMapper;
import com.fanqie.manage.service.MyViewService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * VIEW 服务实现类
 * </p>
 *
 * @author fq
 * @since 2023-04-10
 */
@Service
public class MyViewServiceImpl extends ServiceImpl<MyViewMapper, MyView> implements MyViewService {

    @Autowired
    MyViewMapper mapper;
    /**
     * 获取全部信息
     *
     * @return
     */
    @Override
    public List<MyView> getList() {
        return mapper.selectList(null);
    }
}
