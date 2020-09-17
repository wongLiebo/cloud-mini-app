package com.mini.cloud.app.modules.base.entity;

//
//import com.baomidou.mybatisplus.annotation.TableName;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//
//import com.mini.cloud.common.bean.BaseEntity;
//
//import com.baomidou.mybatisplus.annotation.TableId;
//
//import java.time.LocalDateTime;
//
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author twang
 * @since 2020-09-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("custom_user")
public class User  {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
     @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    /**
     * 支付宝小程序用户id
     */
     @ApiModelProperty(value = "支付宝小程序用户id")
      private String userId;

    /**
     * 用户头像地址
     */
     @ApiModelProperty(value = "用户头像地址")
      private String avatar;

    /**
     * 省份名称
     */
     @ApiModelProperty(value = "省份名称")
      private String province;

    /**
     * 市名称
     */
     @ApiModelProperty(value = "市名称")
      private String city;

    /**
     * 用户昵称
     */
     @ApiModelProperty(value = "用户昵称")
      private String nickName;

    /**
     * 性别（F：女性；M：男性）
     */
     @ApiModelProperty(value = "性别（F：女性；M：男性）")
      private String gender;

    /**
     * 手机号
     */
     @ApiModelProperty(value = "手机号")
      private String mobile;

      private LocalDateTime createTime;

      private LocalDateTime updateTime;


}
