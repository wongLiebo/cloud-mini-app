package com.mini.cloud.app.modules.app.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mini.cloud.app.modules.app.mapper.UserPrizeMapper;
import com.mini.cloud.app.modules.base.entity.PrizeRel;
import com.mini.cloud.app.modules.base.entity.TicketInfo;
import com.mini.cloud.app.modules.base.entity.TicketPrizeRel;
import com.mini.cloud.app.modules.base.entity.User;
import com.mini.cloud.app.modules.base.service.PrizeRelService;
import com.mini.cloud.app.modules.base.service.TicketInfoService;
import com.mini.cloud.app.modules.base.service.TicketPrizeRelService;
import com.mini.cloud.app.modules.base.service.UserService;
import com.mini.cloud.app.vo.req.PrizesVo;
import com.mini.cloud.app.vo.req.UserParamVo;
import com.mini.cloud.app.vo.resp.UserPrizeVo;
import com.mini.cloud.common.bean.BasePageParam;
import com.mini.cloud.common.bean.BaseResult;
import com.mini.cloud.common.controller.AbstractController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  Controller
 * </p>
 *
 * @author twang
 * @since 2020-09-05
 */
@RestController
@RequestMapping("/prizeRel")
@Api(tags="用户领券")
public class PrizeRelController extends AbstractController {

	@Autowired
	private PrizeRelService prizeRelService;
	@Autowired
	private UserPrizeMapper userPrizeMapper;
	@Autowired
	private TicketInfoService ticketInfoService;
	@Autowired
	private TicketPrizeRelService ticketPrizeRelService;
	@Autowired
	private UserService userService;
	
	@PostMapping("/query")
	@ApiOperation(value="用户领券查询", response=UserPrizeVo.class)
	public BaseResult query(@RequestBody UserParamVo vo) {
		String userId = vo.getUserId();
		if(StrUtil.isBlank(userId)){
			return BaseResult.error("用户id不能为空");
		}
		BasePageParam params = new BasePageParam();

		IPage<UserPrizeVo> data = userPrizeMapper.pageQueryUserPointInfo(
				new Page<UserPrizeVo>(params.getPage(), params.getRows()),
				new QueryWrapper<PrizeRel>()
						.eq("a.user_id",userId)
		);
		return BaseResult.ok(data);
	}
	
	@PostMapping("/get")
	@ApiOperation(value="详情",response=PrizeRel.class)
	public BaseResult get(@RequestParam("id")Serializable id) {
		PrizeRel result = prizeRelService.getById(id);
		return BaseResult.ok(result);
	}
	
	@PostMapping("/save")
	@ApiOperation(value="新增",response=BaseResult.class)
	public BaseResult save(@RequestBody PrizesVo model) {
		if(null==model&&null==model.getTplIds()||model.getTplIds().size()==0){
			return BaseResult.error("券模板id不能为空");
		}
		if(StrUtil.isBlank(model.getUserId())){
			return BaseResult.error("用户id不能为空");
		}
		User user = userService.getOne(new QueryWrapper<User>().eq("user_id",model.getUserId()));
		if(null == user){
			return BaseResult.error("没有此用户");
		}

		String returnMsg = null;
		List<PrizeRel> list = new ArrayList<>();
		List<TicketPrizeRel> listPrize = new ArrayList<>();
		for (String tplId:model.getTplIds()){
			//TODO 集合部分continue，部分继续执行的情况优化
			TicketInfo ticketInfo =  ticketInfoService.getOne(new QueryWrapper<TicketInfo>().eq("tpl_id",tplId));
			if(null == ticketInfo){
				continue;
			}

			TicketPrizeRel ticketPrizeRel =  ticketPrizeRelService.getOne(new QueryWrapper<TicketPrizeRel>()
					.eq("tpl_id",ticketInfo.getTplId())
					.eq("status","0")
					.orderByAsc("id")
					.last(" limit 1")
			);
			if(null == ticketPrizeRel){
				if(null == returnMsg){
					returnMsg = ticketInfo.getTplName()+"此券已发完";
				}else{
					returnMsg = returnMsg +"\n"+ ticketInfo.getTplName()+"此券已发完";
				}
				logger.info("券模板：{},此券已发完",ticketInfo.getTplId());
				continue;
			}

			PrizeRel entry = new PrizeRel();
			entry.setTplId(ticketPrizeRel.getTplId());
			entry.setPrizeId(ticketPrizeRel.getPrizeId());
			entry.setUserId(model.getUserId());
			entry.setCreateTime(LocalDateTime.now());
			list.add(entry);

			ticketPrizeRel.setStatus("1");
			listPrize.add(ticketPrizeRel);
		}
		//TODO 建立service，启用事务
		if(null!=list&&list.size()>0){
			prizeRelService.saveBatch(list);
			if(null!=listPrize&&listPrize.size()>0){
				//更新已发放券码
				ticketPrizeRelService.saveOrUpdateBatch(listPrize);
			}
		}

		if(null!=returnMsg){
			return BaseResult.ok(returnMsg+"\n其它券发放成功");
		}
		return BaseResult.ok("券发放成功");
	}
	
}
