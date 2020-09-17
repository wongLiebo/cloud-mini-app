package com.mini.cloud.app.modules.app.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mini.cloud.app.modules.base.entity.CarInfo;
import com.mini.cloud.app.modules.base.service.CarInfoService;
import com.mini.cloud.app.vo.req.CarUserIdVo;
import com.mini.cloud.common.bean.BasePageParam;
import com.mini.cloud.common.bean.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


/**
 * <p>
 *  Controller
 * </p>
 *
 * @author twang
 * @since 2020-09-03
 */
@RestController
@RequestMapping("/carInfo")
@Api(tags = "爱车信息")
public class CarInfoController {

	@Autowired
	private CarInfoService carInfoService;
	
	@PostMapping("/query")
	@ApiOperation(value="列表查询", response=CarInfo.class)
	public BaseResult query(@RequestBody BasePageParam params) {
		IPage<CarInfo> data = carInfoService.page(new Page<>(params.getPage(), params.getRows()), 
			new QueryWrapper<CarInfo>()
		);
		return BaseResult.ok(data);
	}
	
	@PostMapping("/get")
	@ApiOperation(value="详情",response=CarInfo.class)
	public BaseResult get(@RequestBody CarUserIdVo params) {
		if(StrUtil.isBlank(params.getUserId())){
			return BaseResult.error("用户id不能为空");
		}
		CarInfo result = carInfoService.getOne(new QueryWrapper<CarInfo>().eq("user_id",params.getUserId()));
		return BaseResult.ok(result);
	}
	
	@PostMapping("/save")
	@ApiOperation(value="新增",response=BaseResult.class)
	public BaseResult save(@RequestBody CarInfo params) {
		if(StrUtil.isBlank(params.getUserId())){
			return BaseResult.error("用户id不能为空");
		}
		CarInfo result = carInfoService.getOne(new QueryWrapper<CarInfo>().eq("user_id",params.getUserId()));
		if(null == result){
			result = params;
			result.setCreateTime(LocalDateTime.now());
		}else{
			int id = result.getId();
			BeanUtil.copyProperties(params,result);
			result.setId(id);
			result.setUpdateTime(LocalDateTime.now());
		}
		carInfoService.saveOrUpdate(result);

		return BaseResult.ok();
	}
	

	@PostMapping("/delete")
	@ApiOperation(value="删除",response=BaseResult.class)
	public BaseResult delete(@RequestBody CarUserIdVo params) {
		if(StrUtil.isBlank(params.getUserId())){
			return BaseResult.error("用户id不能为空");
		}
		carInfoService.remove(new QueryWrapper<CarInfo>().eq("user_id",params.getUserId()));
		return BaseResult.ok();
	}
}
