package com.mini.cloud.app.modules.app.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mini.cloud.app.modules.base.entity.User;
import com.mini.cloud.app.modules.base.service.UserService;
import com.mini.cloud.app.vo.req.UserParamVo;
import com.mini.cloud.common.bean.BasePageParam;
import com.mini.cloud.common.bean.BaseResult;
import com.mini.cloud.common.controller.AbstractController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
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
@RequestMapping("/user")
@Api(tags = "支付宝用户信息")
public class UserController extends AbstractController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/query")
	@ApiOperation(value="列表查询", response=User.class)
	public BaseResult query(@RequestBody BasePageParam params) {
		IPage<User> data = userService.page(new Page<>(params.getPage(), params.getRows()), 
																		new QueryWrapper<User>() );
		
		return BaseResult.ok(data);
	}

	@PostMapping("/save")
	@ApiOperation(value="新增", response=BaseResult.class)
	public BaseResult save(@RequestBody User user) {
		if(null==user|| StrUtil.isBlank(user.getUserId())){
			logger.error("用户信息及userId不能为空：{}",user.toString());
			return BaseResult.error("用户信息不能为空");
		}
		User entry = userService.getOne(new QueryWrapper<User>()
			.eq("user_id",user.getUserId())
		);
		if(null == entry){
			entry = user;
			entry.setCreateTime(LocalDateTime.now());
		}else{
			int id = entry.getId();
			BeanUtil.copyProperties(user,entry);
			entry.setId(id);
			entry.setUpdateTime(LocalDateTime.now());
		}
		String gender = user.getGender();
		user.setGender(null==gender?"2":(gender.equalsIgnoreCase("m")?"1":"0"));
		userService.saveOrUpdate(entry);
		return BaseResult.ok();
	}

	@PostMapping("/update")
	@ApiOperation(value="设置/更新手机号", response=BaseResult.class)
	public BaseResult update(@RequestBody UserParamVo vo) {
		if(null == vo ||StrUtil.isBlank(vo.getUserId())||StrUtil.isBlank(vo.getMobile())){
			return BaseResult.error("用户id或手机号不能为空");
		}
		User user = userService.getOne(new QueryWrapper<User>().eq("user_id",vo.getUserId()));
		if(user==null){
			BaseResult.error("没有找到此用户");
		}
		user.setMobile(vo.getMobile());
		userService.updateById(user);
		return BaseResult.ok();
	}


	@PostMapping("/get")
	@ApiOperation(value="详情", response=User.class)
	public BaseResult get(@RequestBody UserParamVo vo) {
		if(null == vo ||StrUtil.isBlank(vo.getUserId())){
			return BaseResult.error("用户id不能为空");
		}
		User result = userService.getOne(new QueryWrapper<User>().eq("user_id",vo.getUserId()));
		return BaseResult.ok(result);
	}
	
	@PostMapping("/delete")
	@ApiOperation(value="删除", response=BaseResult.class)
	public BaseResult delete(@RequestParam("id")Serializable id) {
		
		userService.removeById(id);
		
		return BaseResult.ok();
	}
}
