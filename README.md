## 1. 统一响应状态：状态接口（WrapperStatus）和通用状态枚举类（CommonStatus）

## 2. 统一返回格式（WrapperResult）

## 3. 统一返回格式的高级实现：自定义注解（NoResponseWrapper）+ 控制器增强（ResponseAdvice）

只要没有 NoResponseWrapper 注解，就会对响应进行封装

## 4. Controller 全局异常处理：自定义业务异常（BusinessException）+ 全局异常处理（@ExceptionAdvice+@RestControllerAdvice）

## 5. 处理404错误

404错误并不属于异常，全局异常处理自然不会去捕获并处理。

因此我们的解决方法是当出现4xx错误时，让 springboot 直接报异常，在 application.yml 配置文件增加以下配置项即可：

```spring:mvc:throw-exception-if-no-handler-found: true```

接下来，再处理 NoHandlerFoundException 异常即可。

## 6. 封装 String 类型时报错

使用 @ResponseBody 注解或者 @RestController 注解时，Spring 会自动使用 HttpMessageConverter 来将返回值转换为客户端可以接收的格式：

* 对于 String 类型，Spring 默认使用 StringHttpMessageConverter 来处理 String 类型的返回值。如果控制器方法返回了一个封装后的对象，而不是一个简单的 String，那么在转换过程中就会出现 ClassCastException。

* 对于其他类型，Spring 提供了默认的消息转换器（例如MappingJackson2HttpMessageConverter用于JSON），这些转换器能够处理对象到字符串的转换。

解决方法就是删除 String 默认的转换器：
   ```
   @Override
   public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
   //删除springboot默认的StringHttpMessageConverter解析器
   converters.removeIf(x -> x instanceof StringHttpMessageConverter);
   }
   ```
但是这样的话，String 类型的数据就只能经过封装了，否则无法解析，所以最优的方法是自定义一个消息转换器。

## 7. Interceptor 中的异常

@RestControllerAdvice（或@ControllerAdvice）+ @ExceptionAdvice 虽然是处理 Controller 层的异常，但是也能处理 Interceptor 中的异常。

因为 Interceptor 是基于 SpringMVC 框架的，有时候我们需要拦截器中进行逻辑处理时（比如token登陆验证）需要抛出异常，那么这里抛出的异常是可以被（4）中的全局异常处理捕获的。

## 8. Filter 中的异常：转发给一个 controller 处理

Filter 是基于 Servlet 框架编写的，在 Spring MVC 的 DispatcherServlet 之前执行，所以抛出的异常是不可以被（4）中的全局异常处理捕获的，除非：

1. ~~请求转发到特定的 controller 中，该 controller 专门处理 Filter 中的异常~~

2. 在 Filter 中通过 HandlerExceptionResolver 的 resolveException 方法主动对异常进行捕获