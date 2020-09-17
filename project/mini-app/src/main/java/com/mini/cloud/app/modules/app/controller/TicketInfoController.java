package com.mini.cloud.app.modules.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mini.cloud.app.modules.base.entity.TicketInfo;
import com.mini.cloud.app.modules.base.entity.TicketPrizeRel;
import com.mini.cloud.app.modules.base.service.TicketInfoService;
import com.mini.cloud.app.modules.base.service.TicketPrizeRelService;
import com.mini.cloud.common.bean.BasePageParam;
import com.mini.cloud.common.bean.BaseResult;
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
 * @since 2020-09-03
 */
@RestController
@RequestMapping("/ticketInfo")
@Api(tags="商户券信息")
public class TicketInfoController {

	@Autowired
	private TicketInfoService ticketInfoService;
	@Autowired
	private TicketPrizeRelService ticketPrizeRelService;
	
	@PostMapping("/query")
	@ApiOperation(value="列表查询", response=TicketInfo.class)
	public BaseResult query(@RequestBody BasePageParam params) {
		IPage<TicketInfo> data = ticketInfoService.page(new Page<>(params.getPage(), params.getRows()), 
																		new QueryWrapper<TicketInfo>() );
		
		return BaseResult.ok(data);
	}
	
	@PostMapping("/get")
	@ApiOperation(value="详情", response=TicketInfo.class)
	public BaseResult get(@RequestParam("id")Serializable id) {
		TicketInfo result = ticketInfoService.getById(id);
		return BaseResult.ok(result);
	}
	
	@PostMapping("/save")
	@ApiOperation(value="新增", response=BaseResult.class)
	public BaseResult save(@RequestBody TicketInfo params) {
		params.setCreateTime(LocalDateTime.now());
		ticketInfoService.save(params);
		/*List<TicketPrizeRel> list = new ArrayList<>();
		for(int i=1000;i<=10000;i++){
			TicketPrizeRel entry = new TicketPrizeRel();
			entry.setTplId("T001");
			if(i<10000){
				entry.setPrizeId("JP0"+i);
			}else if(i==10000){
				entry.setPrizeId("JP"+i);
			}
			entry.setStatus("0");
			entry.setCreateTime(LocalDateTime.now());
			list.add(entry);
		}
		ticketPrizeRelService.saveBatch(list);


		List<TicketPrizeRel> list2 = new ArrayList<>();
		for(int i=1000;i<=10000;i++){
			TicketPrizeRel entry = new TicketPrizeRel();
			entry.setTplId("T002");
			if(i<10000){
				entry.setPrizeId("JP0"+i);
			}else if(i==10000){
				entry.setPrizeId("JP"+i);
			}
			entry.setStatus("0");
			entry.setCreateTime(LocalDateTime.now());
			list2.add(entry);

		}
		ticketPrizeRelService.saveBatch(list2);*/

		return BaseResult.ok();
	}
	
	@PostMapping("/update")
	@ApiOperation(value="修改", response=TicketInfo.class)
	public BaseResult update(@RequestBody TicketInfo params) {
		params.setUpdateTime(LocalDateTime.now());
		ticketInfoService.updateById(params);
		
		return BaseResult.ok();
	}
	
	@PostMapping("/delete")
	@ApiOperation(value="删除", response=BaseResult.class)
	public BaseResult delete(@RequestParam("id")Serializable id) {
		
		ticketInfoService.removeById(id);
		
		return BaseResult.ok();
	}
}
