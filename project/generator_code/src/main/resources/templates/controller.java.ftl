package ${package.Controller};

import java.io.Serializable;

<#if restControllerStyle>
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};

import com.mini.cloud.common.auth.entity.LoginUserInfo;
import com.mini.cloud.common.auth.util.AuthContextUtils;
import com.mini.cloud.common.bean.BasePageParam;
import com.mini.cloud.common.bean.BaseResult;



<#if swagger2>

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;
</#if>
<#else>
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};

import com.company.common.auth.entity.LoginUserInfo;
import com.company.common.auth.util.AuthContextUtils;
import com.company.common.log.annotation.SysLog;
import com.company.common.bean.BasePageParam;
import com.company.common.bean.BaseResult;

<#if swagger2>
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;
</#if>
</#if>

<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * ${table.comment!} Controller
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if swagger2>
@Api(tags="${table.comment!}")
</#if>
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

	@Autowired
	private ${table.serviceName} ${table.serviceName?uncap_first};
	
	@PostMapping("/query")
	<#if swagger2>
	@ApiOperation(value="${table.comment!}列表查询", response=BaseResult.class)
	</#if>
	public BaseResult query(@RequestBody BasePageParam params) {
		IPage<${entity}> data = ${table.serviceName?uncap_first}.page(new Page<>(params.getPage(), params.getRows()), 
																		new QueryWrapper<${entity}>() );
		
		return BaseResult.ok(data);
	}
	
	@GetMapping("/get")
	<#if swagger2>
	@ApiOperation(value="${table.comment!}详情")
	</#if>
	public BaseResult get(@RequestParam("id")Serializable id) {
		${entity} result = ${table.serviceName?uncap_first}.getById(id);
		return BaseResult.ok(result);
	}
	
	@PostMapping("/save")
	<#if swagger2>
	@ApiOperation(value="${table.comment!}新增")
	</#if>
	public BaseResult save(@RequestBody ${entity} params) {
		// TODO	
		LoginUserInfo userInfo= AuthContextUtils.getUser();
		params.addCreateParam(userInfo);
		params.addEditParam(userInfo);	
		${table.serviceName?uncap_first}.save(params);
		
		return BaseResult.ok();
	}
	
	@PostMapping("/update")
	<#if swagger2>
	@ApiOperation(value="${table.comment!}修改")
	</#if>
	public BaseResult update(@RequestBody ${entity} params) {
		//TODO
		LoginUserInfo userInfo= AuthContextUtils.getUser();
		params.addEditParam(userInfo);
		${table.serviceName?uncap_first}.updateById(params);
		
		return BaseResult.ok();
	}
	
	@GetMapping("/delete")
	<#if swagger2>
	@ApiOperation(value="${table.comment!}删除")
	</#if>
	public BaseResult delete(@RequestParam("id")Serializable id) {
		
		${table.serviceName?uncap_first}.removeById(id);
		
		return BaseResult.ok();
	}
}
</#if>
