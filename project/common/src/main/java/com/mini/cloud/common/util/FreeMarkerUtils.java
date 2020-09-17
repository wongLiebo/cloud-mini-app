package com.mini.cloud.common.util;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkerUtils {

	/**
	 * 渲染模板字符串
	 * @param templateString:模板内容
	 * @param model:数据
	 */
	public static String renderString(String templateString ,Map<String, ?> model){
		if(templateString==null || "".equals(templateString) || model==null){
			return null;
		}
		try {
			StringWriter result=new StringWriter();
			Template template=new Template("default", new StringReader(templateString), new Configuration());
			template.process(model, result);
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * 渲染Template文件
	 * @param  template:模板
	 * @param  model:数据
	 */
	public static String renderTemplate(Template template, Object model) {
		if(template==null || model==null){
			return null;
		}
		try {
			StringWriter result = new StringWriter();
			template.process(model, result);
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * 渲染Template文件
	 * @param directory:文件目录
	 * @param fileName:文件名
	 * @param model:数据
	 * @throws IOException 
	 * */
	public static String renderTemplate(String  directory ,String fileName, Object model) throws IOException {
		Configuration cfg=buildConfiguration(directory);
		return renderTemplate(cfg, fileName, model);
	}
	
	
	/**
	 * 渲染Template文件.
	 * @param cfg:模板目录
	 * @param fileName:文件名
	 * @param model:数据
	 * @throws IOException 
	 * */
	public static String renderTemplate(Configuration cfg ,String fileName, Object model) throws IOException {
		if(cfg==null){
			return null;
		}
		Template template=null;
		try {
			template = cfg.getTemplate(fileName);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		if(template==null || model==null){
			return null;
		}
		try {
			StringWriter result = new StringWriter();
			template.process(model, result);
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 取模板目录，根据文件目录
	 * @param directory:文件目录
	 */
	public static Configuration buildConfiguration(String directory) throws IOException {
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(directory));
		cfg.setDefaultEncoding("UTF-8");  
		return cfg;
	}
	
	/**
	 * 取项目Resource模板目录，根据文件目录
	 * @param directory:文件目录
	 */
	public static Configuration buildConfiguration2Resource(String directory) throws IOException {
		Configuration cfg = new Configuration();
		Resource path = new DefaultResourceLoader().getResource(directory);
		cfg.setDirectoryForTemplateLoading(path.getFile());
		cfg.setDefaultEncoding("UTF-8");  
		return cfg;
	}
	
	/**
	 * 取模板目录，根据Class和后缀
	 * @param clazz: 类
	 * @param pathPrefix: 后缀名
	 * */
	public static Configuration buildConfiguration(Class clazz,String pathPrefix) throws IOException {
		Configuration cfg = new Configuration();
		cfg.setClassForTemplateLoading(clazz, pathPrefix);
		cfg.setDefaultEncoding("UTF-8");  
		return cfg;
	}
	
	
	
	public static void main(String[] args) throws IOException {
//		Map<String, Object> paraMap=new HashMap<String, Object>();
//		paraMap.put("id", "anros");
//		try {
//			Configuration c=FreeMarkers.buildConfiguration(FreeMarkers.class, "/sql_template");
//			System.out.println(FreeMarkers.renderTemplate(c, "t.ftl", paraMap));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
		
		
//		Configuration c=FreeMarkers.buildConfiguration(FreeMarkers.class, "/sql_template");
//		table.setId("1111");
//		try {
//			GenerateStringUtil.generateScript(table, "select_table.ftl");
//		} catch (BusinessException e) {
//			e.printStackTrace();
//		}
		
//		Configuration c=FreeMarkers.buildConfiguration(FreeMarkers.class, "/sql_template");
//		return FreeMarkers.renderTemplate(c, fileName, model);
//		
//		String path = FreeMarkers.class.getResource("/").toString();   
//		String directory=path+"sql_template";
//		System.out.println(directory);
		
		
//		setClassForTemplateLoading
		
		
	}
	
}
