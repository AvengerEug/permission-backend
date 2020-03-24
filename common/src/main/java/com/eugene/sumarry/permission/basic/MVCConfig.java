package com.eugene.sumarry.permission.basic;

import com.eugene.sumarry.permission.filter.JWTAuthFilter;
import com.eugene.sumarry.permission.filter.ContextFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class MVCConfig {

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }

    @Bean
    public FilterRegistrationBean contextFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean(new ContextFilter());
        bean.setOrder(10);
        return bean;
    }


    @Bean
    public FilterRegistrationBean jwtAuthFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean(new JWTAuthFilter());
        bean.setOrder(20);
        return bean;
    }

}
