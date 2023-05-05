package com.fanqie.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanqie.manage.entity.MainAllView;
import com.fanqie.manage.mapper.MainAllViewMapper;
import com.fanqie.manage.service.MainAllViewService;
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
public class MainAllViewServiceImpl extends ServiceImpl<MainAllViewMapper, MainAllView> implements MainAllViewService {


    @Autowired
    MainAllViewMapper mapper;

    /**
     * 查询视图全部
     *
     * @return
     */
    public List<MainAllView> queryAll() {
        return baseMapper.selectList(null); //返回查询结果
    }

    /**
     * 根据用户id查询用户的授课信息
     *
     * @param userId 用户Id
     * @return
     */
    @Override
    public List<MainAllView> queryByUserId(String userId) {
        QueryWrapper<MainAllView> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId)
                .and(i -> i.isNull("is_delete").or().ne("is_delete", 1));
        return mapper.selectList(wrapper);
    }

    /**
     * 根据条件查询
     *
     * @param wrapper
     * @return
     */
    @Override
    public List<MainAllView> selectList(QueryWrapper<MainAllView> wrapper) {
        return mapper.selectList(wrapper);
    }

}
