package cn.edu.xcu.responsewrapper.pojo;

import lombok.Data;
import org.springframework.lang.NonNull;

import java.io.Serial;
import java.io.Serializable;

/**
 * 封装后端的响应
 *
 * @author iWeJang
 * @version 2.0
 */
@Data
public class WrapperResult<T> implements Serializable {

    /**
     * 序列化版本号
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态描述，如成功提示或错误详情
     */
    private String message;

    /**
     * 返回的数据，异常时返回null
     */
    private T data;

    /**
     * 私有构造函数，禁止外部实例化。
     */
    private WrapperResult(@NonNull int code, @NonNull String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 封装后端返回的数据
     *
     * @param data 后端返回的数据
     * @return 成功的响应结果
     */
    public static <T> WrapperResult<T> success(T data) {
        return new WrapperResult<>(CommonStatus.SUCCESS.getCode(),
                CommonStatus.SUCCESS.getMessage(),
                data);
    }

    /**
     * 封装内置的异常（内置的异常已经提供了对应的 IStatus）
     */
    public static <T> WrapperResult<T> fail(IStatus iStatus) {
        return new WrapperResult<>(iStatus.getCode(), iStatus.getMessage(), null);
    }

    /**
     * 封装自定义的业务异常
     */
    public static <T> WrapperResult<T> fail(BusinessException e) {
        return new WrapperResult<>(e.getCode(), e.getMessage(), null);
    }
}
