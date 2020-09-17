package com.company.cloud.generator.util;

import lombok.Data;

import java.util.List;

@Data
public class CodeGeneratorModel {

    private String author = "anros";

    //file Out
    private String srcPath ;
    private String xmlPath ;


    //JDBC
    private String jdbcUrl;
    private String jdbcUser;
    private String jdbcPassword;

    //包路径配置
    private String packageDir;
    private String packageModuleName;

    //表名
    private String[] tablePrefix;
    private String[] tables;


    //输出文件路径
    public void setOutPath(String srcPath,String xmlPath) {
        this.srcPath=srcPath;
        this.xmlPath=xmlPath;
    }
    //数据库连接
    public void setJdbc(String jdbcUrl,String jdbcUser,String jdbcPassword) {
        this.jdbcUrl=jdbcUrl;
        this.jdbcUser=jdbcUser;
        this.jdbcPassword=jdbcPassword;
    }
    //包名
    public void setPackage(String packageDir,String packageModuleName) {
        this.packageDir=packageDir;
        this.packageModuleName=packageModuleName;
    }
    //表名
    public void setTable(String[] tables,String[] tablePrefix) {
        this.tables=tables;
        if(tablePrefix==null || tablePrefix.length<=0) {
            this.tablePrefix=new String[] {};
        }else {
            this.tablePrefix=tablePrefix;
        }
    }
    public void setTable(String[] tables) {
        this.tables=tables;
        this.tablePrefix=new String[] {};
    }

    public void setTable(List<String> tables, List<String> tablePrefix) {
        String[] tbs = new String[tables.size()];
        tables.toArray(tbs);
        this.tables=tbs;
        if(tablePrefix==null || tablePrefix.size()<=0) {
            this.tablePrefix=new String[] {};
        }else {
            String[] tps = new String[tablePrefix.size()];
            tablePrefix.toArray(tps);
            this.tablePrefix=tps;
        }
    }

    public void setTable(List<String> tables) {
        this.setTable(tables, null);
    }


    //
    public CodeGeneratorModel() {

    }

    public CodeGeneratorModel(String packageDir,String packageModuleName,List<String> tables) {
        this(packageDir,packageModuleName,tables,null);
    }

    public CodeGeneratorModel(String packageDir,String packageModuleName,List<String> tables,List<String> tablePrefix) {
        String[] tbs = new String[tables.size()];
        tables.toArray(tbs);
        this.packageDir=packageDir;
        this.packageModuleName=packageModuleName;
        this.tables=tbs;

        if(tablePrefix==null || tablePrefix.size()<=0) {
            this.tablePrefix=new String[] {};
        }else {
            String[] tps = new String[tablePrefix.size()];
            tablePrefix.toArray(tps);
            this.tablePrefix=tps;
        }
    }

    public CodeGeneratorModel(String packageDir,String packageModuleName,String[] tables) {
        this(packageDir,packageModuleName,tables,new String[] {});
    }

    public CodeGeneratorModel(String packageDir,String packageModuleName,String[] tables ,String[] tablePrefix) {
        this.packageDir=packageDir;
        this.packageModuleName=packageModuleName;
        this.tables=tables;
        this.tablePrefix=tablePrefix;
    }

}
