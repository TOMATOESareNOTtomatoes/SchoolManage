package com.fanqie.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author fq
 * @since 2023-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MyView implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 学期（时间）
     */
    private String term;

    /**
     * 名字
     */
    private String userName;

    private String additionals;

    private String className;

    private String teachNames;

    private Double totalTheoreticalHours;

    private Double totalPracticalHours;

    private Long mergedCount;


}
