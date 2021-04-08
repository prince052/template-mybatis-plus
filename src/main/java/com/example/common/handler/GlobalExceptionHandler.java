package com.example.common.handler;


import com.example.common.exception.BusinessException;
import com.example.common.exception.ForbiddenException;
import com.example.common.exception.UnAuthorizeException;
import com.example.common.vo.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;

/**
 * 统一异常处理
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 未知异常
     *
     * @param e 相关异常
     * @return 无
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    JsonResult<Object>handleException(Exception e) {
        log.error(e.getMessage(), e);
        return new JsonResult<>(500, "服务器开小差了", null);
    }

    /**
     * 处理所有接口数据验证异常
     *
     * @param e 相关异常
     * @return 无
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    JsonResult<Object>handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);
        return new JsonResult<>(400, "Required request body is missing", null);
    }

    /**
     * 处理所有接口数据验证异常
     *
     * @param e 相关异常
     * @return 无
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    JsonResult<Object>handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        String errorMesssage = "参数校验失败: ";
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errorMesssage += fieldError.getDefaultMessage() + ". ";
        }
        return new JsonResult<>(400, errorMesssage, null);
    }

    /**
     * 参数类型不匹配
     *
     * @param e 相关异常
     * @return 无
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    JsonResult<Object>handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error(e.getMessage(), e);
        return new JsonResult<>(400, "参数类型不匹配", null);
    }


    /**
     * 处理所有接口数据验证异常
     *
     * @param e 相关异常
     * @return 无
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    JsonResult<Object>handleConstraintViolationException(ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        return new JsonResult<>(400, "参数类校验失败", null);
    }

    /**
     * 请求参数缺失
     *
     * @param e 相关异常
     * @return 无
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    JsonResult<Object>handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error(e.getMessage(), e);
        String errorMesssage = "请求参数缺失: (" + e.getParameterType() + ") " + e.getParameterName();
        return new JsonResult<>(400, errorMesssage, null);
    }

    /**
     * 未认证
     *
     * @param e 相关异常
     * @return 无
     */
    @ExceptionHandler(UnAuthorizeException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    JsonResult<Object>handleUnAuthorizeException(UnAuthorizeException e) {
        log.error(e.getMessage(), e);
        return new JsonResult<>(401, e.getMessage(), null);
    }

    /**
     * 权限不足
     *
     * @param e 相关异常
     * @return 无
     */
    @ExceptionHandler(ForbiddenException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    JsonResult<Object>handleForbiddenException(ForbiddenException e) {
        log.error(e.getMessage(), e);
        return new JsonResult<>(403, e.getMessage(), null);
    }

    /**
     * 请求资源不存在
     *
     * @param e 相关异常
     * @return 无
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    JsonResult<Object>handleNoHandlerFoundException(NoHandlerFoundException e) {
        log.error(e.getMessage(), e);
        return new JsonResult<>(404, "请求资源不存在", null);
    }

    /**
     * 请求方法不允许
     *
     * @param e 相关异常
     * @return 无
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    JsonResult<Object>handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);
        return new JsonResult<>(405, "请求方法不允许", null);
    }

    /**
     * 数据库连接异常
     *
     * @param e 相关异常
     * @return 无
     */
    @ExceptionHandler(DataAccessException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    JsonResult<Object>handleCommunicationsException(DataAccessException e) {
        log.error(e.getMessage(), e);
        return new JsonResult<>(500, "数据库访问异常", null);
    }

    /**
     * 通用业务异常
     *
     * @param e 相关异常
     * @return 无
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    ResponseEntity<JsonResult<Object>> handleBusinessException(BusinessException e) {
        log.error(e.getMessage(), e);
        JsonResult<Object>result = new JsonResult<>();
        result.setCode(e.getCode());
        result.setMessage(e.getMessage());
        result.setData(null);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}