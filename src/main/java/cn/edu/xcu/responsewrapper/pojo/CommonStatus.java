package cn.edu.xcu.responsewrapper.pojo;

import org.springframework.lang.NonNull;

/**
 * 通用的响应状态
 *
 * @author iWeJang
 * @version 2.0
 */
public enum CommonStatus implements IStatus {
    // 通用成功
    SUCCESS(200, "操作成功"),

    // 通用客户端错误
    BAD_REQUEST(400, "请求参数错误"),
    NOT_FOUND(404, "资源未找到"),
    METHOD_NOT_ALLOWED(405, "方法不被允许"),
    NOT_ACCEPTABLE(406, "不接受的内容类型"),
    PROXY_AUTHENTICATION_REQUIRED(407, "代理认证失败"),
    REQUEST_TIMEOUT(408, "请求超时"),
    CONFLICT(409, "请求冲突"),
    GONE(410, "资源已不可用"),
    LENGTH_REQUIRED(411, "缺少内容长度"),
    PRECONDITION_FAILED(412, "前提条件失败"),
    PAYLOAD_TOO_LARGE(413, "请求实体过大"),
    UNSUPPORTED_MEDIA_TYPE(415, "不支持的媒体类型"),
    TOO_MANY_REQUESTS(429, "请求次数过多"),

    // 通用服务器错误
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),  // RuntimeException
    NOT_IMPLEMENTED(501, "服务未实现"),
    BAD_GATEWAY(502, "网关错误"),
    SERVICE_UNAVAILABLE(503, "服务不可用"),
    GATEWAY_TIMEOUT(504, "网关超时"),
    HTTP_VERSION_NOT_SUPPORTED(505, "HTTP版本不支持"),

    //MVC 异常
    NO_HANDLER_FOUND(2003, "请求资源不存在"), // NoHandlerFoundException
    METHOD_ARGUMENT_NOT_VALID(2004, "请求参数校验失败"), // MethodArgumentNotValidException
    MISSING_SERVLET_REQUEST_PARAMETER(2005, "缺少请求参数"), // MissingServletRequestParameterException
    MISSING_PATH_VARIABLE(2006, "缺少路径变量"), // MissingPathVariableException
    MISSING_REQUEST_HEADER(2007, "缺少请求头"), // MissingRequestHeaderException
    HTTP_METHOD_NOT_SUPPORTED(405, "不支持的请求方法"), // HttpRequestMethodNotSupportedException

    //Security 异常
    ACCESS_DENIED(2008, "访问拒绝"), // AccessDeniedException
    BAD_CREDENTIALS(2009, "认证失败"),


    //通用的业务异常
    BUSINESS_ERROR(5000, "业务系统中错误");

    private final Integer code;
    private final String message;

    private CommonStatus(@NonNull int code, @NonNull String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
