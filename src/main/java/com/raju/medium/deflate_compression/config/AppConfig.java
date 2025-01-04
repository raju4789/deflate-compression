package com.raju.medium.deflate_compression.config;


import com.raju.medium.deflate_compression.filter.DeflateCompressionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public FilterRegistrationBean<DeflateCompressionFilter> deflateCompressionFilter() {
        FilterRegistrationBean<DeflateCompressionFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new DeflateCompressionFilter());
        registrationBean.addUrlPatterns("/*"); // Apply to all endpoints
        registrationBean.setName("DeflateCompressionFilter");
        registrationBean.setOrder(1); // Set the order of the filter
        return registrationBean;
    }
}
