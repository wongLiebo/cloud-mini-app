package com.mini.cloud.common.asserts;

import com.mini.cloud.common.constant.BaseReturnEnum;
import com.mini.cloud.common.exception.BusinessException;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;


/**
 * hibernate-validator校验工具类
 */
public class ValidatorUtils {
	private static Validator validator;
	static {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	/**
	 * 校验对象
	 * @param object 待校验对象
	 * @throws BusinessException 校验不通过，则报异常
	 */
	public static void validate(Object object) {
		_validate(object);
	}

	/**
	 * 校验对象
	 * 
	 * @param object
	 *            待校验对象
	 * @param groups
	 *            待校验的组
	 * @throws BusinessException
	 *             校验不通过，则报异常
	 */
	private static void _validate(Object object, Class<?>... groups) throws BusinessException {
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
		if (!constraintViolations.isEmpty()) {
			ConstraintViolation<Object> constraint = (ConstraintViolation<Object>) constraintViolations.iterator()
					.next();
			
			throw new BusinessException(BaseReturnEnum.PARAM_NULL.getCode(), constraint.getMessage());
		}
	}
	/**
	 * 校验对象
	 * 
	 * @param object 待校验对象
	 * @param groups 待校验的组
	 * @throws BusinessException 校验不通过，则报异常
	 */
	public static void validate(Object object, Class<?>... groups) {
		_validate(object, groups);
	}
}
