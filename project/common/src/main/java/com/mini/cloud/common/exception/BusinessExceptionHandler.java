package com.mini.cloud.common.exception;

import com.mini.cloud.common.bean.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;


/**
 * 异常处理类
 */
@RestControllerAdvice
public class BusinessExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(BusinessException.class)
	public BaseResult handleRRException(BusinessException e) {
		logger.error(e.getMessage(), e);
		return BaseResult.error(e.getCode(), e.getMessage());
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public BaseResult handlerNoFoundException(Exception e) {
		logger.error(e.getMessage(), e);
		return BaseResult.error("404", "路径不存在，请检查路径是否正确");
	}


	@ExceptionHandler(Exception.class)
	public BaseResult handleException(Exception e) {
		logger.error(e.getMessage(), e);
		return BaseResult.error();
	}
	
}
