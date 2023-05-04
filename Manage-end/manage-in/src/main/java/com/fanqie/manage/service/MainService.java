package com.fanqie.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fanqie.commonutils.utils.R;
import com.fanqie.manage.entity.Main;
import com.fanqie.manage.param.acSure;
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

    R AdditionalSure(acSure ac);

    R AdditionalUnSure(acSure ac);

    R addMain(Main main);

    R upDataByMain(Main main);

    R getCExperiment();

    R getCTheory();

    R getCPractice();

    R getAdditionalSureA();

    R AdditionalSureA(acSure acSure);

    R AdditionalUnSureA(acSure acSure);
}


