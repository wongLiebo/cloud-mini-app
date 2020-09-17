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
@TableName("shop_ticket_prize_rel")
public class TicketPrizeRel  {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
     @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

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
     * 使用状态 1是0否
     */
     @ApiModelProperty(value = "使用状态 1是0否")
      private String status;

    /**
     * 创建时间
     */
     @ApiModelProperty(value = "创建时间")
      private LocalDateTime createTime;


}
