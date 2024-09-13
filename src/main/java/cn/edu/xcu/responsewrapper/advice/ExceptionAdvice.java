package cn.edu.xcu.responsewrapper.advice;

import cn.edu.xcu.responsewrapper.pojo.BusinessException;
import cn.edu.xcu.responsewrapper.pojo.CommonStatus;
import cn.edu.xcu.responsewrapper.pojo.WrapperResult;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;

/**
 * 全局处理各种类型的异常，并返回适当的错误响应。
 *
 * @author iWeJang
 * @version 2.0
 */
@RestControllerAdvice
public class ExceptionAdvice {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    /**
     * 处理自定义的业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public WrapperResult serviceExceptionHandler(BusinessException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        logger.error("发生业务异常！请求地址'{}'，错误信息：'", requestURI, e);
        return WrapperResult.fail(e);
    }

    /**
     * 404 异常
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public WrapperResult handleNoHandlerFoundException(NoHandlerFoundException noHandlerFoundException, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        logger.error("请求资源不存在！请求地址'{}'，错误信息：", requestURI, noHandlerFoundException);
        return WrapperResult.fail(CommonStatus.NO_HANDLER_FOUND);
    }

    /**
     * 权限校验异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public WrapperResult handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        logger.error("发生权限校验异常！请求地址'{}'，错误信息：", requestURI, e);
        return WrapperResult.fail(CommonStatus.ACCESS_DENIED);
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public WrapperResult handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        logger.error("Http请求方式不支持！请求地址'{}'，不支持'{}'请求", requestURI, e.getMethod());
        return WrapperResult.fail(CommonStatus.HTTP_METHOD_NOT_SUPPORTED);
    }

    /**
     * 处理参数校验异常
     */
    @ExceptionHandler(BindException.class)
    public WrapperResult handleBindException(BindException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        logger.error("发生参数校验异常！请求地址'{}'，错误信息：", requestURI, e);
        return WrapperResult.fail(CommonStatus.BAD_REQUEST);
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public WrapperResult handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        logger.error("发生未知异常！请求地址'{}'，错误信息：", requestURI, e);
        return WrapperResult.fail(CommonStatus.INTERNAL_SERVER_ERROR);
    }
}
