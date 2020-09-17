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

import com.mini.cloud.common.auth.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author twang
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_oper_log")
public class OperLog  {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
     @ApiModelProperty(value = "编号")
    @TableId(value = "oper_id", type = IdType.AUTO)
      private Integer operId;

    /**
     * 支付宝用户id
     */
     @ApiModelProperty(value = "支付宝用户id")
     @Excel(name = "支付宝用户id")
     private String userId;

    /**
     * 请求方法名称
     */
     @ApiModelProperty(value = "请求方法名称")
     private String operMethod;

    /**
     * 请求url
     */
     @ApiModelProperty(value = "请求url")
     @Excel(name = "请求url")
     private String operUrl;

    /**
     * 主机地址
     */
     @ApiModelProperty(value = "主机地址")
     @Excel(name = "主机地址")
     private String operIp;

    /**
     * 操作地点
     */
     @ApiModelProperty(value = "操作地点")
     @Excel(name = "操作地点")
     private String operLocation;

    /**
     * session_id
     */
     @ApiModelProperty(value = "session_id")
     @Excel(name = "session_id")
     private String sessionId;

    /**
     * 请求参数
     */
     @ApiModelProperty(value = "请求参数")
     @Excel(name = "请求参数")
     private String operParam;

    /**
     * 备注
     */
     @ApiModelProperty(value = "备注")
     @Excel(name = "备注")
     private String remark;

    /**
     * 操作时间
     */
     @ApiModelProperty(value = "操作时间")
     @Excel(name = "操作时间")
     private LocalDateTime operTime;


}
