package com.mini.cloud.common.controller;

import com.mini.cloud.common.auth.entity.LoginUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class AbstractController {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected LoginUserInfo getUser() {
		return null;
	}

}
