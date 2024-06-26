package org.onyx.showcasebackend.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class SameSiteCookieConfig {

    @Value("${cookie.secure}")
    private boolean secureCookie;

    @Value("${cookie.same-site}")
    private String sameSite;

    @Bean
    public FilterRegistrationBean<Filter> sameSiteFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new Filter() {
            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
                if (response instanceof HttpServletResponse) {
                    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                    httpServletResponse.setHeader("Set-Cookie", "JSESSIONID=" + ((HttpServletRequest) request).getSession().getId()
                            + "; SameSite=" + sameSite + (secureCookie ? "; Secure" : ""));
                }
                chain.doFilter(request, response);
            }
        });
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
