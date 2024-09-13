1. 状态接口（WrapperStatus）和通用状态枚举类（CommonStatus）
2. 统一返回格式类（WrapperResult）
3. 统一返回格式的高级实现：注解（NoResponseWrapper）+ 控制器增强（ResponseAdvice）
   只要没有NoResponseWrapper注解，就会对响应进行封装，
4. 全局异常处理：自定义业务异常（BusinessException）+ 全局异常处理（ExceptionAdvice）
5. 处理404错误
   404错误并不属于异常，全局异常处理自然不会去捕获并处理。
   因此我们的解决方法是当出现4xx错误时，让springboot直接报异常，在application.yml配置文件增加以下配置项即可：
   spring:mvc:throw-exception-if-no-handler-found: true
   接下来，再处理NoHandlerFoundException异常即可。
6. **封装 String 类型时报错**
   使用@ResponseBody注解或者@RestController注解时，Spring会自动使用HttpMessageConverter来将返回值转换为客户端可以接收的格式：
   * 对于String类型，Spring默认使用StringHttpMessageConverter来处理String类型的返回值。如果控制器方法返回了一个封装后的对象，而不是一个简单的String，那么在转换过程中就会出现ClassCastException。
   * 对于其他类型，Spring提供了默认的消息转换器（例如MappingJackson2HttpMessageConverter用于JSON），这些转换器能够处理对象到字符串的转换。
   
   解决方法就是删除String默认的转换器：
    ```
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    //删除springboot默认的StringHttpMessageConverter解析器
    converters.removeIf(x -> x instanceof StringHttpMessageConverter);
    }
    ```
    但是String类型的数据就只能经过封装了，否则无法解析，所以最优的方法是还是自定义一个消息转换器。
