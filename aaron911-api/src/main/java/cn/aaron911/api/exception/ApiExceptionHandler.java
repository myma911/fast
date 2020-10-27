package cn.aaron911.api.exception;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.aaron911.api.constant.GlobalConstants;
import cn.aaron911.common.context.HttpRequestContext;
import cn.aaron911.common.exception.BaseException;
import cn.aaron911.common.exception.enums.StateCodeEnum;
import cn.aaron911.common.result.HttpRequestInfo;
import cn.aaron911.common.result.Result;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 异常处理
 */
@ControllerAdvice
public class ApiExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);
    /**
     * 用于处理自定义错误
     */
	@ExceptionHandler(value = BaseException.class)
	@ResponseBody
    public Result<StateCodeEnum> error(BaseException ex){
	    log.error(GlobalConstants.logPrefix(), ex);
        final Result<StateCodeEnum> result = new Result<>(ex.getStateCodeEnum());
        final HttpRequestInfo httpRequestInfo = HttpRequestContext.getHttpRequestInfo();
        httpRequestInfo.setRequestReturn(result);
        httpRequestInfo.setExceptionMessage(ex.getMessage());
        return result;
    }


    /**
     * 用来处理方法的参数校验异常
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
	@ResponseBody
    public Result<StateCodeEnum> error(ConstraintViolationException ex){
        log.error(GlobalConstants.logPrefix(), ex);
        final Result<StateCodeEnum> result = new Result<>(StateCodeEnum.CONSTRAINT_VIOLATION_ERROR.setMessage(ex.getMessage()));
        final HttpRequestInfo httpRequestInfo = HttpRequestContext.getHttpRequestInfo();
        httpRequestInfo.setRequestReturn(result);
        httpRequestInfo.setExceptionMessage(ex.getMessage());
        return result;
    }


    /**
     * 用来处理VO参数校验异常
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
	@ResponseBody
    public Result<StateCodeEnum> error(MethodArgumentNotValidException ex){
        log.error(GlobalConstants.logPrefix(), ex);
        List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
        Result<StateCodeEnum> result;
        if (CollUtil.isNotEmpty(objectErrors)){
            // 只取第一个错误信息
            ObjectError objectError = objectErrors.get(objectErrors.size() - 1);
            result = new Result<>(StateCodeEnum.METHOD_ARGUMENT_NOT_VALID_ERROR.setMessage(objectError.getDefaultMessage()));
        }else{
            result = new Result<>(StateCodeEnum.METHOD_ARGUMENT_NOT_VALID_ERROR.setMessage(ex.getMessage()));
        }
        final HttpRequestInfo httpRequestInfo = HttpRequestContext.getHttpRequestInfo();
        httpRequestInfo.setRequestReturn(result);
        httpRequestInfo.setExceptionMessage(ex.getMessage());
        return result;
    }



    /**
     * 参数绑定异常(大多是因为参数提交不正确)
     *
     */
    @ExceptionHandler(BindException.class)
	@ResponseBody
    public Result<StateCodeEnum> handlerBindException(BindException ex) {
        log.error(GlobalConstants.logPrefix(), ex);
        String message = createExceptionMessage(ex);
        Result<StateCodeEnum> result;
        if (StrUtil.isNotBlank(message)) {
            result = new Result<>(StateCodeEnum.METHOD_ARGUMENT_NOT_VALID_ERROR.setMessage(message));
        }else{
            result = new Result<>(StateCodeEnum.BIND_ERROR.setMessage(ex.getMessage()));
        }
        final HttpRequestInfo httpRequestInfo = HttpRequestContext.getHttpRequestInfo();
        httpRequestInfo.setRequestReturn(result);
        httpRequestInfo.setExceptionMessage(ex.getMessage());
        return result;
    }

    private String createExceptionMessage(BindException ex) {
        List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
        if(CollUtil.isNotEmpty(objectErrors)) {
            StringBuilder msgBuilder = new StringBuilder();
            // 只取第一个错误信息
            ObjectError objectError = objectErrors.get(objectErrors.size()-1);
            msgBuilder.append(objectError.getDefaultMessage());
            return msgBuilder.toString();
        }
        return null;
    }


    /**
     * 数据类型转换异常，大多因为参数没传对
     */
    @ExceptionHandler(value = TypeMismatchException.class)
	@ResponseBody
    public Result<StateCodeEnum> error(TypeMismatchException ex){
        log.error(GlobalConstants.logPrefix(), ex);
        final Result<StateCodeEnum> result = new Result<>(StateCodeEnum.TYPE_MISMATCH_ERROR.setMessage(ex.getMessage()));
        final HttpRequestInfo httpRequestInfo = HttpRequestContext.getHttpRequestInfo();
        httpRequestInfo.setRequestReturn(result);
        httpRequestInfo.setExceptionMessage(ex.getMessage());
        return result;
    }



    /**
     * 用于处理全局错误
     */
    @ExceptionHandler(value = Exception.class)
	@ResponseBody
    public Result<StateCodeEnum> error(Exception ex){
        log.error(GlobalConstants.logPrefix(), ex);
        final Result<StateCodeEnum> result = new Result<>(StateCodeEnum.SYSTEM_ERROR.setMessage(ex.getMessage()));
        final HttpRequestInfo httpRequestInfo = HttpRequestContext.getHttpRequestInfo();
        httpRequestInfo.setRequestReturn(result);
        httpRequestInfo.setExceptionMessage(ex.getMessage());
        return result;
    }
}
