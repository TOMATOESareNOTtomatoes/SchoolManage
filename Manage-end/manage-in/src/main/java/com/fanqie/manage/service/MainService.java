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

    /**
     * 院长同意 新添加的 课程信息
     * @param soleAndUser
     * @return
     */
    R sureAddMain(soleAndUser soleAndUser);

    /**
     * 管理员获取 新添加课程 列表
     * @return
     */
    R getAddMainListA();

    /**
     * 管理员同意课程信息
     * @param soleAndUser
     * @return
     */
    R sureAddMainA(soleAndUser soleAndUser);

    /**
     * 用户  修改  课程信息
     * @param userDoInfo
     * @return
     */
    R UserReviseDo(userDoInfo userDoInfo);

    /**
     * 管理员 获取 简单的 工作量统计 总表
     * @return 返回的结果只有 4 个
     */
    R simpleAllDo();

    /**
     * 查询系数的接口
     * @return 全部系数表
     */
    R getCoefficient();
}


