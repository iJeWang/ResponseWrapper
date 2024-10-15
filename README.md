## 1. 统一返回结构：状态接口（IStatus）和通用状态枚举类（CommonStatus）

## 2. 统一返回格式（WrapperResult）

## 3. 统一包装处理：自定义注解（NoResponseWrapper）+ 控制器增强（ResponseAdvice）

如果每一个 Controller 方法内都封装一次，比较重复，所以还要继续统一包装处理： 只要没有 NoResponseWrapper 注解，就会对响应进行封装

## 4. Controller 全局异常处理：自定义业务异常（BusinessException）+ 全局异常处理（@ExceptionAdvice+@RestControllerAdvice）

## 5. 处理404错误

404错误并不属于异常，全局异常处理自然不会去捕获并处理。

因此我们的解决方法是当出现4xx错误时，让 springboot 直接报异常，在 application.yml 配置文件增加以下配置项即可：

```spring:mvc:throw-exception-if-no-handler-found: true```

接下来，再处理 NoHandlerFoundException 异常即可。

## 6. 封装 String 类型时报错

使用 @ResponseBody 注解或者 @RestController 注解时，Spring 会自动使用 HttpMessageConverter 来将返回值转换为 Json 字符串：

* 对于 String 类型的返回值，Spring 首先使用 StringHttpMessageConverter 来处理。经过封装后的 String 自然就不能使用该转换器了，那么在转换过程中就会出现 ClassCastException。

* 对于其他类型的返回值，Spring 首先使用 MappingJackson2HttpMessageConverter 来处理。这些类型即使经过封装也还是一个对象，该转换器自然还能够处理，不会有问题。

### ~~方法一：删除 String 默认的转换器~~
   ```
   @Override
   public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
   //删除springboot默认的StringHttpMessageConverter解析器
   converters.removeIf(x -> x instanceof StringHttpMessageConverter);
   }
   ```
但是这样的话，String 类型的数据就只能经过封装了，否则无法解析并报错

### 方法二：修改转换器的顺序

发生上述问题的根源所在是集合中 StringHttpMessageConverter 的顺序先于 MappingJackson2HttpMessageConverter 的，调整顺序后即可从根源上解决这个问题

    ```
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (int i = 0; i < converters.size(); i++) {
            if (converters.get(i) instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = (MappingJackson2HttpMessageConverter) converters.get(i);
                converters.set(i, converters.get(0));
                converters.set(0, mappingJackson2HttpMessageConverter);
                break;
            }
        }
    }
    ```

### 方法三：针对 String 类型，对封装对象手动转换成 JSON 字符串
    ```
    // 如果返回值是String类型，那就手动把封装对象转换成JSON字符串
    if (body instanceof String) {
        try {
            return new ObjectMapper().writeValueAsString(WrapperResult.success(body));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    ```
## 7. Interceptor 中的异常

@RestControllerAdvice（或@ControllerAdvice）+ @ExceptionAdvice 虽然是处理 Controller 层的异常，但是也能处理 Interceptor 中的异常。

因为 Interceptor 是基于 SpringMVC 框架的，有时候我们需要拦截器中进行逻辑处理时（比如token登陆验证）需要抛出异常，那么这里抛出的异常是可以被（4）中的全局异常处理捕获的。

## 8. Filter 中的异常：转发给一个 controller 处理

Filter 是基于 Servlet 框架编写的，在 Spring MVC 的 DispatcherServlet 之前执行，所以抛出的异常是不可以被（4）中的全局异常处理捕获的，除非：

1. ~~请求转发到特定的 controller 中，该 controller 专门处理 Filter 中的异常~~

2. 在 Filter 中通过 HandlerExceptionResolver 的 resolveException 方法主动对异常进行捕获