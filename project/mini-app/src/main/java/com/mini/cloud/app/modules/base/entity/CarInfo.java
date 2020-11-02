package com.mini.cloud.app.modules.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

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
@TableName("custom_car_info")
public class CarInfo  {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
     @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.INPUT)
      private Integer id;

    /**
     * 支付宝用户id
     */
     @ApiModelProperty(value = "支付宝用户id")
      private String userId;

    /**
     * 车牌号
     */
     @ApiModelProperty(value = "车牌号")
      private String license;

    /**
     * 车架号
     */
     @ApiModelProperty(value = "车架号")
      private String vinno;

    /**
     * 发动机号
     */
     @ApiModelProperty(value = "发动机号")
      private String engineno;

    /**
     * 注册日期
     */
     @ApiModelProperty(value = "注册日期")
      private String registeredDate;

    /**
     * 车辆品牌
     */
     @ApiModelProperty(value = "车辆品牌")
      private String brandname;

    /**
     * 车辆型号
     */
     @ApiModelProperty(value = "车辆型号")
      private String vichecode;

    /**
     * 座位数
     */
     @ApiModelProperty(value = "座位数")
      private Integer sitnum;

    /**
     * 车系
     */
     @ApiModelProperty(value = "车系")
      private String farmilyName;

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
