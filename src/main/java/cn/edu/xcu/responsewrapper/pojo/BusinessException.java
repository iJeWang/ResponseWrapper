package cn.edu.xcu.responsewrapper.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务异常类
 *
 * @author iWeJang
 * @version 2.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {

    /**
     * 状态码
     */
    private int code;

    /**
     * 状态描述
     */
    private String message;

    /**
     * 构造函数
     *
     * @param code    状态码
     * @param message 状态信息
     */
    private BusinessException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(WrapperStatus wrapperStatus) {
        this(wrapperStatus.code(), wrapperStatus.message());
    }

    /**
     * 使用默认的业务异常码来创建业务异常
     */
    public BusinessException(String message) {
        this(CommonStatus.BUSINESS_ERROR.code(), message);
    }

    /**
     * 使用默认的业务异常枚举来创建业务异常
     */
    public BusinessException() {
        this(CommonStatus.BUSINESS_ERROR.code(), CommonStatus.BUSINESS_ERROR.message());
    }

    /**
     * 重写 fillInStackTrace 方法，不填充堆栈信息，提高性能.
     *
     * @return this
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
