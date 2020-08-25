package cn.aaron911.common.validator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import cn.aaron911.common.exception.AException;

import java.util.Set;

/**
 * hibernate-validator校验工具类
 *
 * 参考文档：https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/?v=6.1#validator-gettingstarted-createproject
 *
 */
public class ValidatorUtils {
    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 校验对象
     * @param object        待校验对象
     * @param groups        待校验的组
     * @throws RRException  校验不通过，则报RRException异常
     */
    public static void validateEntity(Object object, Class<?>... groups)
            throws AException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
        	ConstraintViolation<Object> constraint = (ConstraintViolation<Object>)constraintViolations.iterator().next();
            throw new AException(constraint.getMessage());
        }
    }
}
