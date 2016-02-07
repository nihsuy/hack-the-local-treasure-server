package com.taikenfactory.htlt;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);
	
    @Bean
    public Filter corsFilter() {
        return new Filter() {
            @Override
            public void init(FilterConfig filterConfig) throws ServletException {}

            @Override
            public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
				HttpServletResponse response = (HttpServletResponse) res;
				response.setHeader("Access-Control-Allow-Origin", "*");
				response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
				response.setHeader("Access-Control-Max-Age", "3600");
				response.setHeader("Access-Control-Allow-Headers", "origin, x-requested-with, content-type, accept");
				chain.doFilter(req, res);
            }

            @Override
            public void destroy() {}
        };
    }

}
