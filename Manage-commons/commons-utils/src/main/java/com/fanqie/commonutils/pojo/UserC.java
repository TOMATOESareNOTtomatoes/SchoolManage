//package com.fanqie.pojo;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableField;
//import com.baomidou.mybatisplus.annotation.TableId;
//import com.baomidou.mybatisplus.annotation.TableName;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.experimental.Accessors;
//import org.hibernate.validator.constraints.Length;
//
//import javax.validation.constraints.NotBlank;
//import java.io.Serializable;
//
///**
// * <p>
// *
// * </p>
// *
// * @author fq
// * @since 2023-03-07
// */
//@Data
//@EqualsAndHashCode(callSuper = false)
//@Accessors(chain = true)
//@TableName("user")
//public class UserC implements Serializable {
//
//    private static final long serialVersionUID=1L;
//
//    @TableId(value = "user_id", type = IdType.AUTO)
//    private Integer userId;
//
//    @Length(min=6)//显然，限制长度的 有max可选
//    private String userName;
//
//    //忽略属性 不生成json 不接受json数据  @JsonIgnore
//    // @JsonInclude(JsonInclude.Include.NON_NULL)  + null 当这个值不为null的时候生成json,为null不生成
//    // 不影响接收json
//
//    @NotBlank
//    @TableField("PASSWORD")
//    private String password;
//
//    @NotBlank
//    private String userPhonenumber;
//
//
//}
