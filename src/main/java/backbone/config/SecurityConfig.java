package backbone.config;

import backbone.security.CookieAuthenticationFilter;
import backbone.security.HeaderAuthenticationFilter;
import backbone.security.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    @Value("${security.cookie.name}")
    private String authenticationCookie;

    @Value("${security.cors.origins}")
    private String allowedOrigins;

    @Autowired
    private SessionManager sessionManager;

    @Bean
    public FilterRegistrationBean<CookieAuthenticationFilter> apiAuthenticationFilter(){

        FilterRegistrationBean<CookieAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new CookieAuthenticationFilter(authenticationCookie, sessionManager, allowedOrigins));
        registrationBean.addUrlPatterns("/api/*");

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<HeaderAuthenticationFilter> mobileAuthenticationFilter(){

        FilterRegistrationBean<HeaderAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new HeaderAuthenticationFilter(authenticationCookie, sessionManager, allowedOrigins));
        registrationBean.addUrlPatterns("/cmc/*");

        return registrationBean;
    }
}
