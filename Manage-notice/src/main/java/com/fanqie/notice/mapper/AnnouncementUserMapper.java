package com.fanqie.notice.mapper;

import com.fanqie.notice.entity.AnnouncementUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fq
 * @since 2023-04-18
 */
@Mapper
@Repository
public interface AnnouncementUserMapper extends BaseMapper<AnnouncementUser> {

    AnnouncementUser selectOneByIdId(String announcementId, String userId);

    int updateByIdId(Map<String, Object> params);
}
