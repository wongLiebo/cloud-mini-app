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
@TableName("shop_ticket_info")
public class TicketInfo  {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
     @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    /**
     * 券模板id
     */
     @ApiModelProperty(value = "券模板id")
      private String tplId;

    /**
     * 业务系统券标识：用于前后端接口券标识
     */
     @ApiModelProperty(value = "业务系统券标识：用于前后端接口券标识")
      private String tplMark;

    /**
     * 券模板名称
     */
     @ApiModelProperty(value = "券模板名称")
      private String tplName;

    /**
     * 动态参数传值，模板中定义的占位动态参数，应全部通过此参数传入实际值 key-value map 格式，key 和 value 都必须为 String 类型
     */
     @ApiModelProperty(value = "动态参数传值，模板中定义的占位动态参数，应全部通过此参数传入实际值 key-value map 格式，key 和 value 都必须为 String 类型")
      private String tplParams;

    /**
     * 发券（用户信息识别）类型，“2”为基于用户信息识别
     */
     @ApiModelProperty(value = "发券（用户信息识别）类型，“2”为基于用户信息识别")
      private String recognitionType;

    /**
     * 备注
     */
     @ApiModelProperty(value = "备注")
      private String remark;

    /**
     * 创建时间
     */
     @ApiModelProperty(value = "创建时间")
      private LocalDateTime createTime;

    /**
     * 修改时间
     */
     @ApiModelProperty(value = "修改时间")
      private LocalDateTime updateTime;


}
