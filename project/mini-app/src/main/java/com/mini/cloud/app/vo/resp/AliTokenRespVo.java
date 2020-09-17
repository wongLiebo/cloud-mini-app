package com.mini.cloud.app.vo.resp;


import lombok.Getter;
import lombok.Setter;

/**
 *     "alipay_system_oauth_token_response": {
 *         "user_id": "2088102150477652",
 *         "access_token": "20120823ac6ffaa4d2d84e7384bf983531473993",
 *         "expires_in": "3600",
 *         "refresh_token": "20120823ac6ffdsdf2d84e7384bf983531473993",
 *         "re_expires_in": "3600"
 *     }
 */
@Setter
@Getter
public class AliTokenRespVo {

    /**
     * 支付宝用户的唯一userId
     */
    private String user_id;
    /**
     * 访问令牌。通过该令牌调用需要授权类接口
     */
    private String access_token;
    /**
     * 访问令牌的有效时间，单位是秒
     */
    private String expires_in;
    /**
     * 刷新令牌。通过该令牌可以刷新access_token
     */
    private String refresh_token;
    /**
     * 刷新令牌的有效时间，单位是秒
     */
    private String re_expires_in;



}
