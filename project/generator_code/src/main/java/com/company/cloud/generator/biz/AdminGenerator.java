package com.company.cloud.generator.biz;

import com.company.cloud.generator.util.CodeGeneratorModel;
import com.company.cloud.generator.util.CodeGeneratorUtils;

import java.util.ArrayList;
import java.util.List;

public class AdminGenerator {

    public static void main(String[] args) {
        System.out.println("-------------------------->>>>> init ");
        CodeGeneratorModel parameter=new CodeGeneratorModel();
        parameter.setAuthor("twang");
        parameter.setOutPath("D:\\work\\IdeaProjects\\zhifubao\\cloud-mini-app-20200917\\project\\mini-app\\src\\main\\java", "D:\\work\\IdeaProjects\\zhifubao\\cloud-mini-app-20200917\\project\\mini-app\\src\\main\\resources\\mappers");
        parameter.setJdbc("jdbc:mysql://127.0.0.1:3306/cloud_server_db?serverTimezone=UTC", "root", "root");
        parameter.setPackage("com.mini.cloud.app.modules", "base");

        List<String> tables=new ArrayList<String>();
//        tables.add("custom_car_info");
//        tables.add("custom_prize_rel");
//        tables.add("custom_user");
//        tables.add("shop_ticket_info");
//        tables.add("shop_ticket_prize_rel");
//        tables.add("sys_oper_log");
        tables.add("sys_menu");

        List<String> tablePrefix=new ArrayList<String>();
        tablePrefix.add("custom_");
        tablePrefix.add("shop_");
        tablePrefix.add("sys_");
        parameter.setTable(tables, tablePrefix);
        CodeGeneratorUtils.generator(parameter);

    }
}
