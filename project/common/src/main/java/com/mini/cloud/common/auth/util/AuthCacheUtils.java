package com.mini.cloud.common.auth.util;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.mini.cloud.common.auth.entity.PermRoleInfo;
import com.mini.cloud.common.bean.DataMapEntity;
import com.mini.cloud.common.cache.CacheService;
import com.mini.cloud.common.util.RegexUtils;
import com.mini.cloud.common.util.StrSplitUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class AuthCacheUtils {


    /**
     *公司：Map(角色:权限码)(URL:角色号)(:)
     * */
    public static String getCompanyAuthKey(String companyNo) {
        if(StringUtils.isEmpty(companyNo)){
            return "Com_R_P_PT";
        }else{
            return StrUtil.format("Com_R_P_{}", companyNo);
        }
    }

    /**
     * 正则权限KEY
     * */
    private  static String CompanyAuth_PermRegex="PermRegex_Role";



    /**
     * 验证
     * */
    private  static boolean authRole(Set<String> userRole,Set<String> permRole) {
        if(CollectionUtils.isEmpty(userRole)){
            return false;
        }
        if(CollectionUtils.isEmpty(permRole)){
            return false;
        }
        List<String> authRole= userRole.stream().filter(item -> permRole.contains(item)).collect(Collectors.toList());
        if(authRole!=null && authRole.size()>0) {
            return true;
        }else {
            return false;
        }
    }


    public static boolean checkAuthc(String companyNo, String url, Set<String> roles, CacheService cacheService){
        String key=AuthCacheUtils.getCompanyAuthKey(companyNo);
        //URL
        Object cacheVal1= cacheService.mget(key,url);
        if(cacheVal1!=null && StringUtils.hasText(cacheVal1.toString())){
            Set<String> permRoles= StrSplitUtils.parse2set(cacheVal1.toString(),String.class);
            if (AuthCacheUtils.authRole(roles,permRoles)){
                return true;
            }
        }
        //URL Regex
        Object cacheVal2= cacheService.mget(key,CompanyAuth_PermRegex);
        if(cacheVal2!=null && StringUtils.hasText(cacheVal2.toString())){
            DataMapEntity permRegex2Roles=JSON.parseObject(cacheVal2.toString(),DataMapEntity.class);
            for(String regexUrl:permRegex2Roles.keySet()){
                if(RegexUtils.isAuthc(url, regexUrl)){
                    Object regexVal= permRegex2Roles.get(regexUrl);
                    if(regexVal!=null){
                        Set<String> permRoles= StrSplitUtils.parse2set(regexVal.toString(),String.class);
                        if (AuthCacheUtils.authRole(roles,permRoles)){
                            return true;
                        }
                    }
                }
            }
        }

        return  false;
    }

    public static void addCompanyAuthCache(CacheService cacheService , String companyNo, List<PermRoleInfo> permRoleList){
        String key=AuthCacheUtils.getCompanyAuthKey(companyNo);
        //Map(角色号:权限码)
        Map<String, Set<String>> role_PermCode_map=new HashMap<String, Set<String>>();

        //Map(权限URL:角色号)
        Map<String, Set<String>> permUrlRoleMap=new HashMap<String, Set<String>>();
        Map<String, Set<String>> permRegexRoleMap=new HashMap<String, Set<String>>();


        permRoleList.forEach(item->{
            AuthCacheUtils.addRole2PermCode(role_PermCode_map, item.getRoleCode(), item.getPermCode());
            AuthCacheUtils.addPermUrl2Role(permUrlRoleMap,permRegexRoleMap, item.getPermUrl(), item.getRoleCode());
        });

        Map<String, String> valMap=new HashMap<String, String>();
        //Map(角色号:权限码)
        role_PermCode_map.forEach((k,v)->{
            valMap.put(k, StrSplitUtils.list2string(v));
        });
        //Map(权限URL:角色号)
        if(!CollectionUtils.isEmpty(permUrlRoleMap)) {
            permUrlRoleMap.forEach((k,v)->{
                valMap.put(k, StrSplitUtils.list2string(v));
            });
        }
        //Map(权限URL:角色号)
        if(!CollectionUtils.isEmpty(permRegexRoleMap)) {
            DataMapEntity permRegex2Role=new DataMapEntity();
            permRegexRoleMap.forEach((url,codes)->{
                permRegex2Role.put(url,StrSplitUtils.list2string(codes));
            });
            valMap.put(AuthCacheUtils.CompanyAuth_PermRegex, JSON.toJSONString(permRegex2Role));
        }
//        System.out.println(JSON.toJSONString(valMap));
        //公司号:Map
        cacheService.mput(key, valMap);

    }

    /**角色号:权限码*/
    private static void addRole2PermCode(Map<String, Set<String>> map,String roleCode,String permCode) {
        Set<String> val= map.get(roleCode);
        if(val==null) {
            val=new HashSet<String>();
            map.put(roleCode, val);
        }
        val.add(permCode);
    }


    /**权限URL:角色号*/
    private static void addPermUrl2Role(Map<String, Set<String>> map,
                                      Map<String, Set<String>> regexMap,String permUrl,String role) {
        if(permUrl.indexOf(",")!=-1) {
            String[] perms= permUrl.split(",");
            for(String perm:perms) {
                if(perm!=null && !"".equals(perm)) {
                    AuthCacheUtils.addPermUrlRegex2Role(map,regexMap,perm,role);
                }
            }
        }else {
            AuthCacheUtils.addPermUrlRegex2Role(map,regexMap,permUrl,role);
        }
    }
    /**权限URL(正则):角色号*/
    private static void addPermUrlRegex2Role(Map<String, Set<String>> map,
                                       Map<String, Set<String>> regexMap,String permUrl,String roleCode) {
        if(permUrl.indexOf("*")==-1) {
            Set<String> val= map.get(permUrl);
            if(val==null) {
                val=new HashSet<String>();
                map.put(permUrl, val);
            }
            val.add(roleCode);
        }else {
            Set<String> val= regexMap.get(permUrl);
            if(val==null) {
                val=new HashSet<String>();
                regexMap.put(permUrl, val);
            }
            val.add(roleCode);
        }
    }

    public static void main(String[] args) {

//        List<PermRoleInfo> permRoleList=new ArrayList<>();
//        permRoleList.add(new PermRoleInfo("Role_admin","Per_admin","/**"));
//
//
//        permRoleList.add(new PermRoleInfo("Role_wjj","Per_1","/user/*"));
//        permRoleList.add(new PermRoleInfo("Role_wjj","Per_2","/role/save,/role/edit"));
//
//        AuthCacheUtils.addCompanyAuthCache(null,null,permRoleList);

    }

}
