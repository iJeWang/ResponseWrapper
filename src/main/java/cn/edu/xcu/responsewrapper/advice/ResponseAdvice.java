package cn.edu.xcu.responsewrapper.advice;

import cn.edu.xcu.responsewrapper.annotation.NoResponseWrapper;
import cn.edu.xcu.responsewrapper.pojo.WrapperResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 拦截 Controller 方法返回值并进行封装
 *
 * @author iWeJang
 * @version 2.0
 */
@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 判断是否要执行 beforeBodyWrite 方法。
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 检查方法或方法所在的类上是否有NoResponseWrapper注解
        // 如果在方法和类上都没有，则处理
        return !returnType.hasMethodAnnotation(NoResponseWrapper.class) &&
                !returnType.getContainingClass().isAnnotationPresent(NoResponseWrapper.class);

    }

    /**
     * 对返回值进行包装处理。
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof WrapperResult) {
            return body;
        }
        return WrapperResult.success(body);
    }
}