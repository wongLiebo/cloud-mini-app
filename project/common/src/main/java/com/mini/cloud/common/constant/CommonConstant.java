package com.mini.cloud.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author:lta
 * @date:2019年10月15日下午3:21:14
 * @projectname:business
 * @description:
 *
 */
public class CommonConstant {

	/**
	 * 发薪描述
	 */
	public static final String PAYROLL_DESCRIBE="工资";

	// 客户类型
	public static class CustomerType {

		// 投保人
		public static long TBR = 0l;

		// 被保人
		public static long BBR = 1l;
	}

	// 收入状态
	public static class IncomeStatus {

		// 未结算
		public static int UNSETTLED = 0;

		// 已结算
		public static int SETTLED = 1;

		// 未支付
		public static int UNPAID = 2;
	}

	// 承保标识
	public static class OrderUnderwriteFlag {

		// 未承保
		public static long CHECK = 0l;

		// 已承保
		public static long ACCEPT = 1l;
	}

	// 收益类型
	public static class IncomeCostType {

		// 代理人
		public static int AGENT = 1;

		// 宇石
		public static int YUSHI = 2;

		// 太平金服
		public static int TAIPING = 3;

	}

	// 执业证状态
	public static class PracticeStatusType {
		// 0未认证
		public static String UNCERTIFIED = "0";

		// 1已认证
		public static String CERTIFIED = "1";

		// 2已认证未确认
		public static String UNCONFIRM = "2";

		// -1认证失败
		public static String FAILD = "-1";

	}
	
	// 保险类别
	public static Map<String, String> insuranceTypeMap = new HashMap<String, String>();
	static {
		insuranceTypeMap.put("1", "财产险");
		insuranceTypeMap.put("2", "健康险");
		insuranceTypeMap.put("3", "车险");
		insuranceTypeMap.put("4", "意外险");
	}
	
	

	public static Map<String, String> productTypeMap = new HashMap<String, String>();
	static {
		productTypeMap.put("1", "太平人寿");
		productTypeMap.put("2", "太平养老");
		productTypeMap.put("3", "太平财险");
		productTypeMap.put("4", "太平财险");
	}
	
	
	public static Map<String, String> nationTypeMap = new HashMap<String, String>();
	static {
		nationTypeMap.put("汉", "01");
		nationTypeMap.put("蒙古", "02");
		nationTypeMap.put("回", "03");
		nationTypeMap.put("藏", "04");
		nationTypeMap.put("维吾尔", "05");
		nationTypeMap.put("苗", "06");
		nationTypeMap.put("彝", "07");
		nationTypeMap.put("壮", "08");
		nationTypeMap.put("布依", "09");
		nationTypeMap.put("朝鲜", "10");
		nationTypeMap.put("满", "11");
		nationTypeMap.put("侗", "12");
		nationTypeMap.put("瑶", "13");
		nationTypeMap.put("白", "14");
		nationTypeMap.put("土家", "15");
		nationTypeMap.put("哈尼", "16");
		nationTypeMap.put("哈萨克", "17");
		nationTypeMap.put("傣", "18");
		nationTypeMap.put("黎", "19");
		nationTypeMap.put("傈傈", "20");
		nationTypeMap.put("佤", "21");
		nationTypeMap.put("畲", "22");
		nationTypeMap.put("高山", "23");
		nationTypeMap.put("拉祜", "24");
		nationTypeMap.put("水", "25");
		nationTypeMap.put("东乡", "26");
		nationTypeMap.put("纳西", "27");
		nationTypeMap.put("景颇", "28");
		nationTypeMap.put("柯尔克孜", "29");
		nationTypeMap.put("土", "30");
		nationTypeMap.put("达斡尔", "31");
		nationTypeMap.put("仫佬", "32");
		nationTypeMap.put("羌", "33");
		nationTypeMap.put("布朗", "34");
		nationTypeMap.put("撒拉", "35");
		nationTypeMap.put("毛南", "36");
		nationTypeMap.put("仡佬", "37");
		nationTypeMap.put("锡伯", "38");
		nationTypeMap.put("阿昌", "39");
		nationTypeMap.put("普米", "40");
		nationTypeMap.put("塔吉克", "41");
		nationTypeMap.put("怒", "42");
		nationTypeMap.put("乌孜别克", "43");
		nationTypeMap.put("俄罗斯", "44");
		nationTypeMap.put("鄂温克", "45");
		nationTypeMap.put("德昂", "46");
		nationTypeMap.put("保安", "47");
		nationTypeMap.put("裕固", "48");
		nationTypeMap.put("京", "49");
		nationTypeMap.put("塔塔尔", "50");
		nationTypeMap.put("独龙", "51");
		nationTypeMap.put("鄂伦春", "52");
		nationTypeMap.put("赫哲", "53");
		nationTypeMap.put("门巴", "54");
		nationTypeMap.put("珞巴", "55");
		nationTypeMap.put("基诺", "56");
		nationTypeMap.put("未知", "99");
	}
	
	
	public static Map<String, String> cultureTypeMap = new HashMap<String, String>();
	static {
		cultureTypeMap.put("11", "博士");
		cultureTypeMap.put("14", "硕士");
		cultureTypeMap.put("17", "研究生");
		cultureTypeMap.put("21", "本科");
		cultureTypeMap.put("31", "大专");
		cultureTypeMap.put("41", "中专");
		cultureTypeMap.put("61", "高中及同等学历");
		cultureTypeMap.put("71", "初中及同等学历");
		cultureTypeMap.put("81", "初中及以下学历");
		cultureTypeMap.put("90", "其他");
	}
	
	
	public static Map<String, String> politicsTypeMap = new HashMap<String, String>();
	static {
		politicsTypeMap.put("01", "中共党员");
		politicsTypeMap.put("02", "中共预备党员");
		politicsTypeMap.put("03", "共青团员");
		politicsTypeMap.put("04", "民革会员");
		politicsTypeMap.put("05", "民盟盟员");
		politicsTypeMap.put("06", "民建会员");
		politicsTypeMap.put("07", "民进会员");
		politicsTypeMap.put("08", "农工党党员");
		politicsTypeMap.put("09", "致公党党员");
		politicsTypeMap.put("10", "九三学社社员");
		politicsTypeMap.put("11", "台盟盟员");
		politicsTypeMap.put("12", "无党派人士");
		politicsTypeMap.put("13", "群众");
	}

}
