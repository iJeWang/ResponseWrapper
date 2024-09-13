package cn.edu.xcu.responsewrapper.annotation;

import java.lang.annotation.*;

/**
 * {@code WrapperResult} 是一个标识性注解，用于标记控制器类或控制器方法的返回结果需要进行统一处理。
 * <p>
 * 使用此注解的类或方法将会被拦截器识别，从而对返回的结果进行统一的封装处理（使用 {@code WrapperResult} ）类进行封装。
 * 该注解不包含任何属性，但其存在能够帮助开发者明确哪些返回结果需要特别处理。
 * </p>
 *
 * @author iWeJang
 * @version 2.0
 */
@Retention(RetentionPolicy.RUNTIME) // 注解在运行时可用
@Target({ElementType.TYPE, ElementType.METHOD}) // 可以应用于类和方法
@Documented // 生成文档时包含该注解信息
public @interface NoResponseWrapper {
}
