package com.fanqie.manage.listener;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * 自动填充实现类
 */

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {

        //this.strictInsertFill(metaObject, "gmt_create", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)
        this.setFieldValByName("is_delete",0000,metaObject);
        this.setFieldValByName("gmt_create",new Date(),metaObject);
        this.fillStrategy(metaObject,"is_delete",0);

        //this.fillStrategy(metaObject,"is_delete",0);
        log.info("start insert fill ....");
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName( "gmt_modified",new Date(),metaObject); // 起始版本 3.3.0(推荐)
        log.info("start update fill ....");
    }

}