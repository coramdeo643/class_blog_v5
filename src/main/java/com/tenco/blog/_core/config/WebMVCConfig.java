package com.tenco.blog._core.config;

import com.tenco.blog._core.interceptor.LoginInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// IoC - Singleton pattern
@RequiredArgsConstructor
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    // DI - Dependency Injection
    private final LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                // interceptor 가 동작할 URI pattern setting
                .addPathPatterns("/board/**", "/user/**")
                // interceptor 에서 제외할 URL pattern setting
                .excludePathPatterns("/board/{id:\\d+}");
                // \\d+ = 정규표현식, 1개 이상의 숫자를 의미 (/board/1, /board/2)

    }
}
