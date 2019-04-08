package com.nongyeos.loan.admin.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.nongyeos.loan.admin.entity.Result;
import com.nongyeos.loan.base.util.ResultUtil;
/**
 * 拦截异常类
 * @author ThinkPad
 *
 */
@ControllerAdvice
public class ExceptionHandle {

	private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
        if (e instanceof CustomException) {
        	CustomException customException = (CustomException) e;
            return ResultUtil.error(customException.getCode(), customException.getMessage());
        }else {
            logger.error("【系统异常】{}", e);
            return ResultUtil.error(-1, "未知错误");
        }
    }
	
}
