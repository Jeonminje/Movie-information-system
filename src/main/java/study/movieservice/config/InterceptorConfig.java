package study.movieservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import study.movieservice.controller.interceptor.LoginCheckInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        String[] excludeUrl = {"/", "/members/*", "/log-in", "/log-out"};

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .excludePathPatterns(excludeUrl);
    }
}
