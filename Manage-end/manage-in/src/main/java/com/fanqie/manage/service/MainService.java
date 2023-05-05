package com.fanqie.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fanqie.commonutils.param.UserCheckParam;
import com.fanqie.commonutils.utils.R;
import com.fanqie.manage.entity.Main;
import com.fanqie.manage.param.acSure;
import com.fanqie.manage.param.soleAndUser;
import com.fanqie.manage.param.userDoInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fq
 * @since 2023-03-30
 */

public interface MainService extends IService<Main> {

    R uploadExcel(MultipartFile file);

    R getUserDoSimple();

    R getUserDoInfo(String userId);

    R addAdditional(userDoInfo userDoInfo);

    R UserSureDo(userDoInfo userDoInfo);

    R getAdditionalSure();

    /**
     * 院长 同意 特殊情况
     * @param soleAndUser
     * @return
     */
    R AdditionalSure(soleAndUser soleAndUser);

    R AdditionalUnSure(acSure ac);

    /**
     * 教师添加课程信息
     * @param userDoInfo
     * @return
     */
    R addMain(userDoInfo userDoInfo);

    R upDataByMain(Main main);

    R getCExperiment();

    R getCTheory();

    R getCPractice();

    R getAdditionalSureA();

    /**
     * 管理员 同意教师的特殊情况 申请接口
     * @param soleAndUser
     * @return
     */
    R AdditionalSureA(soleAndUser soleAndUser);

    R AdditionalUnSureA(acSure acSure);

    /**
     * 获取教师特殊情况  院长的
     * @param userCheckParam
     * @return
     */
    R getAdditionalListByF(UserCheckParam userCheckParam);

    /**
     * 获取教师 新添加 的课程信息
     * @param userCheckParam
     * @return
     */
    R getAddMainList(UserCheckParam userCheckParam);
}


