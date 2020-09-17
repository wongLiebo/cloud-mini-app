package com.mini.cloud.app.modules.app.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mini.cloud.app.vo.resp.UserPrizeVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 用户领券查询
 */
public interface UserPrizeMapper {

    /**
     * 分页查询用户积分信息
     *
     * @param pageRequest 分页参数
     * @param wrapper     条件参数
     * @return IPage<UserPrizeVo>
     */
    @Select("SELECT" +
            " a.user_id," +
            " c.prize_id," +
            " d.tpl_id," +
            " d.tpl_name," +
            " d.recognition_type," +
            " d.remark," +
            " b.create_time" +
            " FROM " +
            " custom_user a" +
            " INNER JOIN custom_prize_rel b ON a.user_id = b.user_id" +
            " INNER JOIN shop_ticket_prize_rel c ON b.prize_id = c.prize_id" +
            " INNER JOIN shop_ticket_info d ON c.tpl_id = d.tpl_id" +
            " ${ew.customSqlSegment}")
    IPage<UserPrizeVo> pageQueryUserPointInfo(Page<UserPrizeVo> pageRequest, @Param(Constants.WRAPPER) Wrapper wrapper);


}
