package com.mini.cloud.app.modules.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mini.cloud.app.modules.base.entity.OperLog;
import com.mini.cloud.app.modules.base.service.OperLogService;
import com.mini.cloud.app.vo.req.LogQueryReqParamsVo;
import com.mini.cloud.common.auth.entity.LoginUserInfo;
import com.mini.cloud.common.auth.util.AuthContextUtils;
import com.mini.cloud.common.bean.BasePageParam;
import com.mini.cloud.common.bean.BaseResult;
import com.mini.cloud.common.controller.AbstractController;
import com.mini.cloud.common.util.ExcelUtil;
import com.mini.cloud.common.util.HttpRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


/**
 * <p>
 *  Controller
 * </p>
 *
 * @author twang
 * @since 2020-09-07
 */
@RestController
@RequestMapping("/operLog")
@Api(tags="系统操作记录")
public class OperLogController extends AbstractController {

	@Value("${spring.application.name}")
	private String projectRootPath;

	private String prefix = "/operLog";

	@Autowired
	private OperLogService operLogService;

	@GetMapping()
	public String operLog()
	{
		return projectRootPath+prefix + "/syslog";
	}

	
	@PostMapping("/query")
	@ApiOperation(value="列表查询", response=BaseResult.class)
	public BaseResult query(@RequestBody LogQueryReqParamsVo params) {
		IPage<OperLog> data = operLogService.page(new Page<>(params.getPage(), params.getRows()), 
			new QueryWrapper<OperLog>()
					.eq(StringUtils.hasText(params.getUserId()),"user_id",params.getUserId())
					.like(StringUtils.hasText(params.getOperMethod()),"oper_method",params.getOperMethod())
					.eq(StringUtils.hasText(params.getSessionId()),"session_id",params.getSessionId())
					.gt(StringUtils.hasText(params.getOperTimeStart()),"oper_time",params.getOperTimeStart())
					.le(StringUtils.hasText(params.getOperTimeEnd()),"oper_time",params.getOperTimeEnd())
					.orderByDesc("oper_time")
		);
		return BaseResult.ok(data);
	}
	
	@PostMapping("/save")
	@ApiOperation(value="新增")
	public BaseResult save(@RequestBody OperLog params, HttpServletRequest request ) {
		params.setOperTime(LocalDateTime.now());
		if(null!=request){
			params.setOperIp(getIpAddr(request));
			HttpSession httpSession = request.getSession();
			if(null !=httpSession){
				params.setSessionId(httpSession.getId());
			}
		}
		operLogService.save(params);
		return BaseResult.ok();
	}


	@PostMapping("/export")
	@ApiOperation(value="导出记录", response=BaseResult.class)
	public BaseResult export(@RequestBody LogQueryReqParamsVo params) {
		IPage<OperLog> data = operLogService.page(new Page<>(params.getPage(), params.getRows()),
				new QueryWrapper<OperLog>()
						.eq(StringUtils.hasText(params.getUserId()),"user_id",params.getUserId())
						.like(StringUtils.hasText(params.getOperMethod()),"oper_method",params.getOperMethod())
						.eq(StringUtils.hasText(params.getSessionId()),"session_id",params.getSessionId())
						.gt(StringUtils.hasText(params.getOperTimeStart()),"oper_time",params.getOperTimeStart())
						.le(StringUtils.hasText(params.getOperTimeEnd()),"oper_time",params.getOperTimeEnd())
						.orderByDesc("oper_time")
		);

		List<OperLog> list =  data.getRecords();
		ExcelUtil<OperLog> util = new ExcelUtil<OperLog>(OperLog.class);
		return BaseResult.ok("请到：https://dfrcfw.51bohservice.com/download 地址下载文件："+util.exportExcel(list, "operLog"));
	}




	/**
	 * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
	 * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
	 *
	 * @return ip
	 */
	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		System.out.println("x-forwarded-for ip: " + ip);
		if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			if( ip.indexOf(",")!=-1 ){
				ip = ip.split(",")[0];
			}
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			logger.info("Proxy-Client-IP ip: " + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			logger.info("WL-Proxy-Client-IP ip: " + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
			logger.info("HTTP_CLIENT_IP ip: " + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			logger.info("HTTP_X_FORWARDED_FOR ip: " + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
			logger.info("X-Real-IP ip: " + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			logger.info("getRemoteAddr ip: " + ip);
		}
		logger.info("获取客户端ip: " + ip);
		return ip;
	}
	
}
