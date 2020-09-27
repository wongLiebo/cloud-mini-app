package com.mini.cloud.app.modules.app.controller;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.FileItem;
import com.alipay.api.domain.RegionInfo;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mini.cloud.app.constants.AlipayConstants;
import com.mini.cloud.app.modules.base.entity.PrizeRel;
import com.mini.cloud.app.modules.base.entity.TicketInfo;
import com.mini.cloud.app.modules.base.entity.TicketPrizeRel;
import com.mini.cloud.app.modules.base.entity.User;
import com.mini.cloud.app.modules.base.service.PrizeRelService;
import com.mini.cloud.app.modules.base.service.TicketInfoService;
import com.mini.cloud.app.modules.base.service.TicketPrizeRelService;
import com.mini.cloud.app.modules.base.service.UserService;
import com.mini.cloud.app.vo.req.VersionAuditApplyVo;
import com.mini.cloud.common.bean.BaseResult;
import com.mini.cloud.common.controller.AbstractController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/aliapi")
@Api(tags="支付宝小程序第三方接口")
public class AliApiController extends AbstractController {

    @Autowired
    private UserService userService;
    @Autowired
    private TicketInfoService ticketInfoService;
    @Autowired
    private PrizeRelService prizeRelService;
    @Autowired
    private TicketPrizeRelService ticketPrizeRelService;

    @Value("${zfb.check.sucai.rootPath}")
    private String rootPath;

    public static AlipayClient alipayClient = null;

    /**
     * 获取客户端实例
     * @return
     */
    public AlipayClient getAlipayClient(){
        if(null == alipayClient){
            alipayClient =  new DefaultAlipayClient(
                    AlipayConstants.SERVER_URL,
                    AlipayConstants.APP_ID,
                    AlipayConstants.PRIVATE_KEY,
                    AlipayConstants.FORMAT_TYPE,
                    AlipayConstants.CHARSET_TYPE,
                    AlipayConstants.ALIPAY_PUBLIC_KEY,
                    AlipayConstants.SIGN_TYPE
            );
        }
        return alipayClient ;
    }


    /**
     * code换取授权访问令牌
     * @throws AlipayApiException
     */
    @PostMapping("/getOauthToken")
    @ApiOperation(value="code换取授权访问令牌",response = User.class)
    public BaseResult getOauthToken(@RequestParam("code")String code) throws AlipayApiException {

        AlipayClient alipayClient = this.getAlipayClient();

        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setGrantType(AlipayConstants.AUTHORIZATION_CODE);
        request.setCode(code);
        // 注:三方代小程序调用接口必须传入app_auth_token
        request.putOtherTextParam("app_auth_token", AlipayConstants.APP_AUTH_TOKEN);
        AlipaySystemOauthTokenResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            logger.info("AliApiController:getOauthToken:调用成功,{}",JSON.toJSONString(response));
            return BaseResult.ok(response);
        } else {
            logger.info("AliApiController:getOauthToken:调用失败,{}",JSON.toJSONString(response));
            return BaseResult.error("AliApiController:getOauthToken:调用失败");
        }
//
//        AlipayUserInfoShareResponse userResponse = this.getUserInfoShare(response.getAccessToken());
//        if(response.isSuccess()){
//            logger.info("AliApiController:getUserInfoShare:调用成功,{}",response.toString());
//            User user = new User();
//            user.setUserId(userResponse.getUserId());
//            user.setAvatar(userResponse.getAvatar());
//            user.setProvince(userResponse.getProvince());
//            user.setCity(userResponse.getCity());
//            user.setNickName(userResponse.getNickName());
//            user.setGender(userResponse.getGender()==null?"2":userResponse.getGender().equalsIgnoreCase("m")?"1":"0");
//            user.setMobile(userResponse.getMobile());
//            user.setCreateTime(LocalDateTime.now());
//            userService.save(user);
//            logger.info("userInfo:{}",user.toString());
//            return BaseResult.ok(user);
//        } else {
//            logger.info("AliApiController:getUserInfoShare:调用失败,{}",response.getBody());
//            return BaseResult.error("AliApiController:getUserInfoShare:调用失败");
//        }

    }

    /**
     * code换取应用授权令牌
     * @throws AlipayApiException
     */
    @PostMapping("/getAuthTokenApp")
    @ApiOperation(value="code换取应用授权令牌",response = AlipayOpenAuthTokenAppResponse.class)
    public BaseResult getAuthTokenApp(@RequestParam("code")String code) throws AlipayApiException {

        AlipayClient alipayClient = this.getAlipayClient();

        AlipayOpenAuthTokenAppRequest request = new AlipayOpenAuthTokenAppRequest();
        Map<String,String> dataMap = new HashMap<String,String>();
        dataMap.put("grant_type",AlipayConstants.AUTHORIZATION_CODE);
        dataMap.put("code",code);

        String bizConCent = JSONObject.toJSONString(dataMap);
        request.setBizContent(bizConCent);

        AlipayOpenAuthTokenAppResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            logger.info("AliApiController:getAuthTokenApp:调用成功,{}",JSON.toJSONString(response));
            return BaseResult.ok(response);
        } else {
            logger.info("AliApiController:getAuthTokenApp:调用失败,{}",JSON.toJSONString(response));
            return BaseResult.error("AliApiController:getAuthTokenApp:调用失败");
        }
    }


    /**
     * 卡券实例发放接口
     *  1.发券授权
     *  2.发券
     * @throws AlipayApiException
     */
    @PostMapping("/getInstanceAdd")
    @ApiOperation(value="卡券实例发放接口",response = AlipayPassInstanceAddResponse.class)
    public BaseResult getInstanceAdd(@RequestBody String tplMark,String userId) throws AlipayApiException {
        //参数校验
        User user = userService.getOne(new QueryWrapper<User>()
                .eq("user_id",userId)
        );
        if(null == user){
            return BaseResult.error("未找到此用户，请先用户授权获取用户信息");
        }
        TicketInfo ticketInfo = ticketInfoService.getOne(new QueryWrapper<TicketInfo>()
            .eq("tpl_mark",tplMark)
        );
        if(null == ticketInfo){
            return BaseResult.error("未找到此券数据，请检查数据库发券信息配置是否正确");
        }


        TicketPrizeRel ticketPrizeRel =  ticketPrizeRelService.getOne(new QueryWrapper<TicketPrizeRel>()
                .eq("tpl_id",ticketInfo.getTplId())
                .eq("status","0")
                .orderByAsc("id")
                .last(" limit 1")
        );
        if(null == ticketPrizeRel){
            return BaseResult.error("券已发完");
        }


        //获取token
        KoubeiMarketingToolPrizesendAuthResponse response = getPrizesendAuth(user.getUserId(),ticketPrizeRel.getPrizeId());
        if(response.isSuccess()){
            logger.info("AliApiController:getPrizesendAuth:调用成功,{}",JSON.toJSONString(response));
        } else {
            logger.info("AliApiController:getPrizesendAuth:调用失败,{}",JSON.toJSONString(response));
            return BaseResult.error("AliApiController:getPrizesendAuth:调用失败");
        }

        //发券
        AlipayClient alipayClient = this.getAlipayClient();
        AlipayPassInstanceAddRequest request = new AlipayPassInstanceAddRequest();

        //模板中定义的占位动态参数
//        Map<String,Object> tplParams = new HashMap<String,Object>();
//        tplParams.put("title",title);
        JSONObject tplParams = JSON.parseObject(ticketInfo.getTplParams());

        //tplParams.put("serialNumer","abc_test_201807171156");
        Map<String,Object> recognitionInfo = new HashMap<String,Object>();
        recognitionInfo.put("user_id",userId);
        recognitionInfo.put("user_token",response.getToken());

        //bizContent参数组装
        Map<String,Object> dataMap = new HashMap<String,Object>();
        //支付宝pass模版ID，即调用模板创建接口时返回的tpl_id
        dataMap.put("tpl_id",ticketInfo.getTplId());
        //模版动态参数信息：对应模板中$变量名$的动态参数，见模板创建接口返回值中的tpl_params字段
        dataMap.put("tpl_params",tplParams);
        //发券（用户信息识别）类型，1–订单信息  2-基于用户信息识别
        dataMap.put("recognition_type","2");
        //支付宝用户识别信息：uid发券组件。对接文档：https://opendocs.alipay.com/open/199/sy3hs4
        dataMap.put("recognition_info",recognitionInfo);

        String bizConCent = JSONObject.toJSONString(dataMap);
        logger.info("卡券实例发放接口请求参数：{}",bizConCent);
        request.setBizContent(bizConCent);

        AlipayPassInstanceAddResponse addResponse = alipayClient.execute(request);

        if(response.isSuccess()){
            PrizeRel prizeRel = new PrizeRel();
            prizeRel.setUserId(user.getUserId());
            prizeRel.setPrizeId(ticketPrizeRel.getPrizeId());
            prizeRel.setCreateTime(LocalDateTime.now());
            prizeRelService.save(prizeRel);
            logger.info("AliApiController:getInstanceAdd:调用成功,{}",addResponse.toString());
            return BaseResult.ok(addResponse);
        } else {
            logger.info("AliApiController:getInstanceAdd:调用失败,{}",addResponse.getBody());
            return BaseResult.error("AliApiController:getInstanceAdd:调用失败");
        }
    }


    /**
     * 支付宝会员授权信息查询接口
     * @param accessToken
     * @return
     * @throws AlipayApiException
     */
    public AlipayUserInfoShareResponse getUserInfoShare(String accessToken) throws AlipayApiException {
        AlipayClient alipayClient = this.getAlipayClient();
        AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest();
        return alipayClient.execute(request,accessToken);
    }


    /**
     * 发券授权 返回token授权商户给用户发奖
     * @throws AlipayApiException
     */
    public KoubeiMarketingToolPrizesendAuthResponse getPrizesendAuth(String userId, String prizeId) throws AlipayApiException {

        AlipayClient alipayClient = this.getAlipayClient();
        KoubeiMarketingToolPrizesendAuthRequest request = new KoubeiMarketingToolPrizesendAuthRequest();

        Map<String,Object> dataMap = new HashMap<String,Object>(3);
        //外部流水号，保证业务幂等性
        dataMap.put("req_id", UUID.fastUUID());
        //奖品ID
        dataMap.put("prize_id",prizeId);
        //发奖用户ID
        dataMap.put("user_id",userId);

        String bizConCent = JSONObject.toJSONString(dataMap);
        logger.info("发券授权接口请求参数：{}",bizConCent);
        request.setBizContent(bizConCent);

        return alipayClient.execute(request);
    }

    /**
     * 券实例发放接口
     * @throws AlipayApiException
     */
    @PostMapping("/getInstanceAddList")
//    @ApiOperation(value="支付宝会员授权信息查询接口",response = BaseResult.class)
    public BaseResult getInstanceAddList(@RequestBody String tplId,List<String> userIds, String userToken) throws AlipayApiException {

        AlipayClient alipayClient = this.getAlipayClient();
        AlipayUserPassInstancebatchAddRequest request = new AlipayUserPassInstancebatchAddRequest();
        if(null ==userIds || userIds.size()==0 ){
            return BaseResult.error("用户标识不能为空");
        }

        //bizContent参数组装
        List<String> dataList = new ArrayList<>(userIds.size());
        for(String userId:userIds){

            //模板中定义的占位动态参数
            Map<String,Object> tplParams = new HashMap<String,Object>();
            tplParams.put("title","券标题");
            tplParams.put("serialNumer","abc_test_201807171156");
            Map<String,Object> recognitionInfo = new HashMap<String,Object>();
            recognitionInfo.put("user_id",userId);
            recognitionInfo.put("user_token",userToken);

            //bizContent参数组装
            Map<String,Object> dataMap = new HashMap<String,Object>();
            //支付宝pass模版ID，即调用模板创建接口时返回的tpl_id
            dataMap.put("tpl_id",tplId);
            //模版动态参数信息：对应模板中$变量名$的动态参数，见模板创建接口返回值中的tpl_params字段
            dataMap.put("tpl_params",tplParams);
            //发券（用户信息识别）类型，1–订单信息  2-基于用户信息识别
            dataMap.put("recognition_type","2");
            //支付宝用户识别信息：uid发券组件。对接文档：https://opendocs.alipay.com/open/199/sy3hs4
            dataMap.put("recognition_info",recognitionInfo);

            String bizConCent = JSONObject.toJSONString(dataMap);
            dataList.add(bizConCent);
        }

        logger.info("卡券实例批量发放接口请求参数：{}",dataList.toString());
        request.setBizContent(dataList.toString());

        AlipayUserPassInstancebatchAddResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
            return BaseResult.ok(response);
        } else {
            System.out.println("调用失败");
            return BaseResult.error("调用失败");
        }
    }


    /**
     * code换取应用授权令牌
     * @throws AlipayApiException
     */
    @PostMapping("/getDemo")
    @ApiOperation(value="code换取应用授权令牌",response = AlipayOpenAuthTokenAppResponse.class)
    public BaseResult getDemo(@RequestParam("code")String code) throws AlipayApiException {

        AlipayClient alipayClient = new DefaultAlipayClient(
                "https://openapi.alipay.com/gateway.do",
                "2021001192632171",
                "MIIEuwIBADANBgkqhkiG9w0BAQEFAASCBKUwggShAgEAAoIBAQCcMb4W2uGJDp5vjKmI18bt35rFBLboZmnP5LA95kMZQO51uZcHQlB6E3U16NImwglMz/+0ZNLgM247df6TrejY6kqMPD/TSsvk30ZfT4CqxpQYozwc8q/oXmb1LZedRFlkFaLiq1dt93V447wOmmeM3oB+DdGxeJk/+3LsIp0VmqF8JHpxwPdNT7Q8ye1PTdLMX5SQfuGFxto4vgLBJi2D1a9NiY1ll+odGWquJEQr7NWwdVsiXKQS/rBxuKTkCFE4OZ1UK2mbACimna2rYYZ0cZ0EbW1IOepOyBFsqwjFB+71WYB5y+KWEfDRGfBWBNTg9OI/xPxuPSSCTxrOLDojAgMBAAECgf8g8l0mdQUd4/eHkCQyuVE82+3JoTTz0KaBM7v5UpjUut2R95Pj9BkFpxUSSgoKIuPTUAygAKR6pl6aLEmG3ShAmSsgaZUM/LoFjRNLB3oci8axa71CXe69FSyiivtwMPqqIeY56eqHR6t/1InWirtM+6Ie9MtGpCmvVVrodGSuKZkmaTTp1uPN0YpjS3VwsboXgDD9zkqNOfYGddA+xQ3GVJl0XcSJbOVoX+ymF+kE2acOI6AWHKzvMCmWF/6Ky6ZQ6LVx4U3AJ6WLAG21MpQHYlCbIjO5LCoQeZmt7fvS8TiUcLeGlTW/QW7pntPF8/YBwFAFDV143i8HD3hihXECgYEA/ZLd3PhdbaGMV+lGGPTJL4iQL5zZBAK+QWL3/W7bLN+CjiUN0ESjgkpYKtc8tg5UzELynT1ObCfamZaBlCWNhSTqPvx0ikR8mc0Xh+cv/rLnoGNIN9CAi8k/dBfdJ37PzcmIU/Sf1RAnIMOli6zh2Xu3JUC+2Jq++iM5dr7ZXy8CgYEAnbBX4wwiGZVxInV7Ez1ZLcK7FsRA+ELzNBYlma5Xzqc+ZIwzkuvkIYDg3CwnRnjNvgYzqdzRt7LmADjQweCpS4vrsf+W4Ra10qcLBfVflOekzUTx+IQrrf3DF9vF/uMDxJ8zUpt0asYUn9kNVJJ5k0opQtKgwoaJR0+0d0hAt00CgYBgQtPEoehdyPkNGnpI2QxS5iXOXV2o/x2YluiLpfzAQS0/puNij9v+hxiHUuQyPKiGTlfpxy8xMcPzkEYqpkLeK5BdTtsy7iLbBBeNuP7oUqRJp8bvoVBbQI+9E54Z8zMR7RK6xF+0s4gXEQ6UQpIOTL/O/ZA1jDE5pguXf4XncwKBgG2epEojKiVs3PwefVOPp0oHgDXAcoXjTNxdtP/T0hjH7LjLOiMdtzaPOEH2s8rqueQqvYmNB4FTbU6kMING4YmyGERiMIoDVpy9IQ2gWTTTn55PGoMvponKye/xCQA64miyX2RCyzhawQWqRvkO5lM9D9fzQmxYSGB5iTQO12ZVAoGBAKv1iFffcb/SbIH1M0AgX88MNUYSC/BedkKQ/qwcIyKWMGGLWevpTH6i40ryiod3ugf7QXh+wJUzc5p2FDInJILSrUPQevHOAFyqFspwarYOZfqkU0lqdN7xg4lCscnDQIn72iTnzUuDDSJj2LOy+N24Kz9VTjhjEMXfj1EOdij8",
                "json",
                "GBK",
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0BmV5jGkzkzTSjN1Tg+jqBrIVi67+wftHblgLv6P+t9qfJlXZsoR+mWaCaXRd44TgETn0WjyNKVCAS1uCk9pLmvSWABs4lSCzkOHgGUDEeJUMigy07Dic4RT+M5cH1p4RYRFo1RJzrFI+sI7BAxmp/aHgO9qxBhWEoamu3Nnu7qTHPX5gf6l1NQOujcUqvBBe+La9Qv/jCVSchDb5rv7LXyakN2xG1B4PbniDMRe2czPO/IFAUeKUMU8w+mvVmMOcsV0YSD/FeUpGsr7o+Cgl227GbImcGVBhyFnK6mMZn3IzOvBHukbBfEA9dOO9CjjA2fFjxmwcRCcxuG8ajFKkQIDAQAB",
                "RSA2"
        );
        AlipayOpenAuthTokenAppRequest request = new AlipayOpenAuthTokenAppRequest();
        request.setBizContent("{" +
                "\"grant_type\":\"authorization_code\"," +
                "\"code\":\""+code+
                "\",\"refresh_token\":\""+
                "201208134b203fe6c11548bcabd8da5bb087a83b\"}");
//        request.setBizContent("{" +
//                "\"grant_type\":\"refresh_token\"," +
//                "\"code\":\"5cfbcebe956c43acbf4df765499dOX31\"," +
//                "\"refresh_token\":\"201208134b203fe6c11548bcabd8da5bb087a83b\"" +
//                "  }");
        AlipayOpenAuthTokenAppResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
            return BaseResult.ok();
        } else {
            System.out.println("调用失败");
            return BaseResult.error();

        }
    }



    /**
     * 添加开发者
     * @throws AlipayApiException
     */
    @PostMapping("/openAppMembersCreate")
    @ApiOperation(value="添加开发者",response = AlipayOpenAuthTokenAppResponse.class)
    public BaseResult openAppMembersCreate(@RequestParam("mobile")String mobile) throws AlipayApiException {
        AlipayClient alipayClient = this.getAlipayClient();
        AlipayOpenAppMembersCreateRequest request = new AlipayOpenAppMembersCreateRequest();
        // 注:三方代小程序调用接口必须传入app_auth_token
        request.putOtherTextParam("app_auth_token", AlipayConstants.APP_AUTH_TOKEN);
        request.setBizContent("{" +
                "\"logon_id\":\""+mobile+"\"," +
                "\"role\":\"DEVELOPER\"" +
                "  }");
        AlipayOpenAppMembersCreateResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
            return BaseResult.ok(response);
        } else {
            System.out.println("调用失败");
            return BaseResult.error();
        }
    }


    /**
     * 用第三方token获取调用第三方接口的app_auth_token
     * @throws AlipayApiException
     */
    @PostMapping("/getAuthToken")
    @ApiOperation(value="用第三方token获取调用第三方接口的app_auth_token",response = AlipayOpenAuthTokenAppResponse.class)
    public BaseResult getAuthToken(@RequestParam("mobile")String mobile) throws AlipayApiException {
        AlipayClient alipayClient = this.getAlipayClient();
        AlipayOpenAuthTokenAppRequest request = new AlipayOpenAuthTokenAppRequest();
        // 注:三方代小程序调用接口必须传入app_auth_token
        request.putOtherTextParam("app_auth_token", AlipayConstants.APP_AUTH_TOKEN);

        request.setBizContent("{" +
                "\"grant_type\":\"authorization_code\"," +
                "\"code\":\"704b125679ad4b4da3b3c5ba55686X43\"" +
//                "\"refresh_token\":\"202008BBb4a8771dbb9848069addcbf62fca1X72\"" +
                "  }");

    AlipayOpenAuthTokenAppResponse response =alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
            return BaseResult.ok(response);
        } else {
            System.out.println("调用失败");
            return BaseResult.error();
        }
    }


    /**
     * 小程序生成体验版接口
     * @throws AlipayApiException
     */
    @PostMapping("/openMiniExperienceCreate")
    @ApiOperation(value="小程序生成体验版接口",response = AlipayOpenAuthTokenAppResponse.class)
    public BaseResult openMiniExperienceCreate(@RequestParam("version")String version) throws AlipayApiException {
        AlipayClient alipayClient = this.getAlipayClient();
        AlipayOpenMiniExperienceCreateRequest request = new AlipayOpenMiniExperienceCreateRequest();
        request.setBizContent("{" +
                "\"app_version\":\""+version+"\"," +
                "\"bundle_id\":\"com.alipay.alipaywallet\"" +
                "  }");
        // 注:三方代小程序调用接口必须传入app_auth_token
        request.putOtherTextParam("app_auth_token", AlipayConstants.APP_AUTH_TOKEN);

        AlipayOpenMiniExperienceCreateResponse response = alipayClient.execute(request);

        if(response.isSuccess()){
            System.out.println("调用成功");
            return BaseResult.ok(JSON.toJSONString(response));
        } else {
            System.out.println("调用失败");
            return BaseResult.error();
        }
    }


    /**
     * 小程序体验版状态查询接口
     * @throws AlipayApiException
     */
    @PostMapping("/openMiniExperienceQuery")
    @ApiOperation(value="小程序体验版状态查询接口",response = AlipayOpenAuthTokenAppResponse.class)
    public BaseResult openMiniExperienceQuery(@RequestParam("version")String version) throws AlipayApiException {
        AlipayClient alipayClient = this.getAlipayClient();
        AlipayOpenMiniExperienceQueryRequest request = new AlipayOpenMiniExperienceQueryRequest();
        request.setBizContent("{" +
                "\"app_version\":\""+version+"\"," +
                "\"bundle_id\":\"com.alipay.alipaywallet\"" +
                "  }");
        // 注:三方代小程序调用接口必须传入app_auth_token
        request.putOtherTextParam("app_auth_token", AlipayConstants.APP_AUTH_TOKEN);

        AlipayOpenMiniExperienceQueryResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
            return BaseResult.ok(JSON.toJSONString(response));
        } else {
            System.out.println("调用失败");
            return BaseResult.error();
        }
    }

    /**
     * 小程序类目树查询
     * @throws AlipayApiException
     */
    @PostMapping("/openMiniCategoryQuery")
    @ApiOperation(value="小程序类目树查询",response = AlipayOpenAuthTokenAppResponse.class)
    public BaseResult openMiniCategoryQuery(@RequestParam("version")String version) throws AlipayApiException {
        AlipayClient alipayClient = this.getAlipayClient();

        AlipayOpenMiniCategoryQueryRequest request = new AlipayOpenMiniCategoryQueryRequest();
        request.setBizContent("{" +
                "\"is_filter\":true" +
                "  }");
        // 注:三方代小程序调用接口必须传入app_auth_token
        request.putOtherTextParam("app_auth_token", AlipayConstants.APP_AUTH_TOKEN);
        AlipayOpenMiniCategoryQueryResponse response = alipayClient.execute(request);

        if(response.isSuccess()){
            System.out.println("调用成功"+JSON.toJSONString(response));
            return BaseResult.ok(response);
        } else {
            System.out.println("调用失败");
            return BaseResult.error();
        }
    }

    /**
     * 小程序版本详情查询
     * @throws AlipayApiException
     */
    @PostMapping("/openMiniVersionDetailQuery")
    @ApiOperation(value="小程序版本详情查询",response = AlipayOpenAuthTokenAppResponse.class)
    public BaseResult openMiniVersionDetailQuery(@RequestParam("version")String version) throws AlipayApiException {
        AlipayClient alipayClient = this.getAlipayClient();
        AlipayOpenMiniVersionDetailQueryRequest request = new AlipayOpenMiniVersionDetailQueryRequest();
        request.setBizContent("{" +
                "\"app_version\":\""+version+"\"," +
                "\"bundle_id\":\"com.alipay.alipaywallet\"" +
                "  }");
        // 注:三方代小程序调用接口必须传入app_auth_token
        request.putOtherTextParam("app_auth_token", AlipayConstants.APP_AUTH_TOKEN);

        AlipayOpenMiniVersionDetailQueryResponse response = alipayClient.execute(request);

        if(response.isSuccess()){
            System.out.println("调用成功"+JSON.toJSONString(response));
            return BaseResult.ok(response);
        } else {
            System.out.println("调用失败");
            return BaseResult.error();
        }
    }


    /**
     * 小程序提交审核
     * @throws AlipayApiException
     */
    @PostMapping("/openMiniVersionAuditApply")
    @ApiOperation(value="小程序提交审核",response = AlipayOpenAuthTokenAppResponse.class)
    public BaseResult openMiniVersionAuditApply(@RequestBody VersionAuditApplyVo params) throws AlipayApiException {
        AlipayClient alipayClient = this.getAlipayClient();
        AlipayOpenMiniVersionAuditApplyRequest request = new AlipayOpenMiniVersionAuditApplyRequest();
        // 注:三方代小程序调用接口必须传入app_auth_token
        request.putOtherTextParam("app_auth_token", AlipayConstants.APP_AUTH_TOKEN);

//        request.setLicenseName("营业执照名称");
//        FileItem FirstLicensePic = new FileItem("C:/Downloads/ooopic_963991_7eea1f5426105f9e6069/16365_1271139700.jpg");
//        request.setFirstLicensePic(FirstLicensePic);
//        FileItem SecondLicensePic = new FileItem("C:/Downloads/ooopic_963991_7eea1f5426105f9e6069/16365_1271139700.jpg");
//        request.setSecondLicensePic(SecondLicensePic);
//        FileItem ThirdLicensePic = new FileItem("C:/Downloads/ooopic_963991_7eea1f5426105f9e6069/16365_1271139700.jpg");
//        request.setThirdLicensePic(ThirdLicensePic);
//        FileItem FourthLicensePic = new FileItem("C:/Downloads/ooopic_963991_7eea1f5426105f9e6069/16365_1271139700.jpg");
//        request.setFourthLicensePic(FourthLicensePic);
//        FileItem FifthLicensePic = new FileItem("C:/Downloads/ooopic_963991_7eea1f5426105f9e6069/16365_1271139700.jpg");
//        request.setFifthLicensePic(FifthLicensePic);
//        request.setLicenseValidDate("9999-12-31");
//        FileItem OutDoorPic = new FileItem("C:/Downloads/ooopic_963991_7eea1f5426105f9e6069/16365_1271139700.jpg");
//        request.setOutDoorPic(OutDoorPic);
        request.setAppVersion(params.getVersion());
//        request.setAppName("小程序示例");
//        request.setAppEnglishName("demoexample");
//        request.setAppSlogan("这是一个支付示例");
//        FileItem AppLogo = new FileItem("C:/Downloads/ooopic_963991_7eea1f5426105f9e6069/16365_1271139700.jpg");
//        request.setAppLogo(AppLogo);
//        request.setAppCategoryIds("11_12;12_13");
//        request.setAppDesc("这是一个小程序的描述这是一个小程序的描述这是一个小程序的描述这是一个小程序的描述");
        if(params.getServicePhone()==null){
            request.setServicePhone("13110101010");
        }else{
            request.setServicePhone(params.getServicePhone());
        }

//        request.setServiceEmail("example@mail.com");
        if(params.getVersionDesc()==null){
            request.setVersionDesc("小程序版本描述小程序版本描述小程序版本描述小程序版本描述小程序版本描述小程序版本描述");
        }else{
            request.setVersionDesc(params.getVersionDesc());
        }
        if(params.getMemo()==null){
            request.setMemo("底盘号:123456");
        }else{
            request.setMemo(params.getMemo());
        }
        request.setRegionType("CHINA");
//        List<RegionInfo> regionInfos = new ArrayList<>();
//        RegionInfo regionInfo = new RegionInfo();
//        regionInfo.setProvinceCode("310000");
//        regionInfo.setProvinceName("浙江省");
//        regionInfo.setCityCode("310000");
//        regionInfo.setCityName("杭州市");
//        regionInfo.setAreaCode("311100");
//        regionInfo.setAreaName("余杭区");
//        regionInfos.add(regionInfo);
//        request.setServiceRegionInfo(regionInfos);

//        FileItem FirstScreenShot = new FileItem("D:/work/IdeaProjects/zhifubao/sucaishenhe/1599392774(1).png");
        FileItem FirstScreenShot = new FileItem(rootPath+"/1599392774.png");
        request.setFirstScreenShot(FirstScreenShot);
//        FileItem SecondScreenShot = new FileItem("D:/work/IdeaProjects/zhifubao/sucaishenhe/1599392731(1).png");
        FileItem SecondScreenShot = new FileItem(rootPath+"/1599392731.png");
        request.setSecondScreenShot(SecondScreenShot);
//        FileItem ThirdScreenShot = new FileItem("D:/work/IdeaProjects/zhifubao/sucaishenhe/1599392668(1).png");
        FileItem ThirdScreenShot = new FileItem(rootPath+"/1599392668.png");
        request.setThirdScreenShot(ThirdScreenShot);
//        FileItem FourthScreenShot = new FileItem("C:/Downloads/ooopic_963991_7eea1f5426105f9e6069/16365_1271139700.jpg");
//        request.setFourthScreenShot(FourthScreenShot);
//        FileItem FifthScreenShot = new FileItem("C:/Downloads/ooopic_963991_7eea1f5426105f9e6069/16365_1271139700.jpg");
//        request.setFifthScreenShot(FifthScreenShot);
//        request.setLicenseNo("licenseNo");
        request.setMiniCategoryIds("XS1018_XS2155_XS3133");
//        FileItem FirstSpecialLicensePic = new FileItem("C:/Downloads/ooopic_963991_7eea1f5426105f9e6069/16365_1271139700.jpg");
//        request.setFirstSpecialLicensePic(FirstSpecialLicensePic);
//        FileItem SecondSpecialLicensePic = new FileItem("C:/Downloads/ooopic_963991_7eea1f5426105f9e6069/16365_1271139700.jpg");
//        request.setSecondSpecialLicensePic(SecondSpecialLicensePic);
//        FileItem ThirdSpecialLicensePic = new FileItem("C:/Downloads/ooopic_963991_7eea1f5426105f9e6069/16365_1271139700.jpg");
//        request.setThirdSpecialLicensePic(ThirdSpecialLicensePic);
//        request.setTestAccout("12122");
//        request.setTestPassword("12121");
//        FileItem TestFileName = new FileItem("C:/Downloads/ooopic_963991_7eea1f5426105f9e6069/16365_1271139700.jpg");
//        request.setTestFileName(TestFileName);
//        request.setBundleId("com.alipay.alipaywallet");
//        AlipayOpenMiniVersionAuditApplyResponse response = new AlipayOpenMiniVersionAuditApplyResponse();
          AlipayOpenMiniVersionAuditApplyResponse response =  alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功"+JSON.toJSONString(response));
            return BaseResult.ok(response);
        } else {
            System.out.println("调用失败"+JSON.toJSONString(response));
            return BaseResult.error(JSON.toJSONString(response));
        }
    }


    /**
     * 小程序退回开发
     * @throws AlipayApiException
     */
    @PostMapping("/openMiniVersionAuditedCancel")
    @ApiOperation(value="小程序退回开发",response = AlipayOpenAuthTokenAppResponse.class)
    public BaseResult openMiniVersionAuditedCancel(@RequestParam("version")String version) throws AlipayApiException {
        AlipayClient alipayClient = this.getAlipayClient();

        AlipayOpenMiniVersionAuditedCancelRequest request = new AlipayOpenMiniVersionAuditedCancelRequest();
        request.setBizContent("{" +
                "\"app_version\":\""+version+"\"," +
                "\"bundle_id\":\"com.alipay.alipaywallet\"" +
                "  }");
        // 注:三方代小程序调用接口必须传入app_auth_token
        request.putOtherTextParam("app_auth_token", AlipayConstants.APP_AUTH_TOKEN);

        AlipayOpenMiniVersionAuditedCancelResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功"+JSON.toJSONString(response));
            return BaseResult.ok(response);
        } else {
            System.out.println("调用失败"+JSON.toJSONString(response));
            return BaseResult.error();
        }
    }

    /**
     * 小程序上架
     * @throws AlipayApiException
     */
    @PostMapping("/openMiniVersionOnline")
    @ApiOperation(value="小程序上架",response = AlipayOpenAuthTokenAppResponse.class)
    public BaseResult openMiniVersionOnline(@RequestParam("version")String version) throws AlipayApiException {
        AlipayClient alipayClient = this.getAlipayClient();

        AlipayOpenMiniVersionOnlineRequest request = new AlipayOpenMiniVersionOnlineRequest();
        request.setBizContent("{" +
                "\"app_version\":\""+version+"\"," +
                "\"bundle_id\":\"com.alipay.alipaywallet\"" +
                "  }");
        request.putOtherTextParam("app_auth_token", AlipayConstants.APP_AUTH_TOKEN);

        AlipayOpenMiniVersionOnlineResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功"+JSON.toJSONString(response));
            return BaseResult.ok(response);
        } else {
            System.out.println("调用失败"+JSON.toJSONString(response));
            return BaseResult.error();
        }
    }

    /**
     * 小程序删除
     * @throws AlipayApiException
     */
    @PostMapping("/openMiniVersionDelete")
    @ApiOperation(value="小程序删除",response = AlipayOpenAuthTokenAppResponse.class)
    public BaseResult openMiniVersionDelete(@RequestParam("version")String version) throws AlipayApiException {
        AlipayClient alipayClient = this.getAlipayClient();

        AlipayOpenMiniVersionDeleteRequest request = new AlipayOpenMiniVersionDeleteRequest();
        request.setBizContent("{" +
                "\"app_version\":\""+version+"\"," +
                "\"bundle_id\":\"com.alipay.alipaywallet\"" +
                "  }");
        request.putOtherTextParam("app_auth_token", AlipayConstants.APP_AUTH_TOKEN);

        AlipayOpenMiniVersionDeleteResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功"+JSON.toJSONString(response));
            return BaseResult.ok(response);
        } else {
            System.out.println("调用失败"+JSON.toJSONString(response));
            return BaseResult.error();
        }
    }




}
