package com.company.cloud.generator.util;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

public class CodeGeneratorUtils {

    public static void generator(CodeGeneratorModel parameter){
        String jdbcDriver = "com.mysql.cj.jdbc.Driver";
        boolean override = true;  // 是否覆盖文件

        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(parameter.getSrcPath());
        gc.setFileOverride(override);
        gc.setActiveRecord(false);
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        gc.setAuthor(parameter.getAuthor());
        gc.setOpen(false);
        gc.setSwagger2(true);
        gc.setIdType(IdType.INPUT);
        gc.setServiceName("%sService");
//		gc.setXmlName("%Mapper");
        mpg.setGlobalConfig(gc);

        //数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName(jdbcDriver);
        dsc.setUrl(parameter.getJdbcUrl());
        dsc.setUsername(parameter.getJdbcUser());
        dsc.setPassword(parameter.getJdbcPassword());
        mpg.setDataSource(dsc);


        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(parameter.getPackageDir());
        pc.setModuleName(parameter.getPackageModuleName());
        mpg.setPackageInfo(pc);

        //自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return parameter.getXmlPath() + "/" + (parameter.getPackageModuleName()!=null&&parameter.getPackageModuleName()!=""?parameter.getPackageModuleName()+"/":"")
                        + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);

        strategy.setSuperEntityClass("com.mini.cloud.common.bean.BaseEntity");

        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
//	    strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");
        strategy.setInclude(parameter.getTables());
//	    strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(parameter.getTablePrefix());
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        // 执行生成
        mpg.execute();

    }

}
