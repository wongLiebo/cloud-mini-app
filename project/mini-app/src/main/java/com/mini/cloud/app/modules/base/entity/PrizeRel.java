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
@TableName("custom_prize_rel")
public class PrizeRel  {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    /**
     * 支付宝小程序用户id
     */
     @ApiModelProperty(value = "支付宝小程序用户id")
      private String userId;

    /**
     * 模板id
     */
     @ApiModelProperty(value = "模板id")
      private String tplId;

    /**
     * 奖品id
     */
     @ApiModelProperty(value = "奖品id")
      private String prizeId;

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
