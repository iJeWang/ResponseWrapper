package cn.edu.xcu.responsewrapper.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class ResponseWrapperWebConfig implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //删除springboot默认的StringHttpMessageConverter解析器
        converters.removeIf(x -> x instanceof StringHttpMessageConverter);
    }
}