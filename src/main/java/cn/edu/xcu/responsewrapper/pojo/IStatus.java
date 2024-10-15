package cn.edu.xcu.responsewrapper.pojo;

/**
 * 后端响应结果的状态。
 * <p>
 * 每个枚举值都包含一个整型的代码和一个字符串类型的消息。
 * </p>
 * <p>
 * 目前实现类仅有 CommonStatus，他是一个通用的状态枚举类。
 * 对于业务异常，可以实现该接口，根据需要自定义枚举类。
 * </p>
 *
 * @author iWeJang
 * @version 2.0
 */
public interface IStatus {
    /**
     * 状态码
     */
    Integer getCode();

    /**
     * 状态的描述
     */
    String getMessage();
}
