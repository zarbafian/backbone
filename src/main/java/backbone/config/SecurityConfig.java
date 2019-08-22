package backbone.config;

import backbone.security.AuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    @Value("${security.authentication.cookie}")
    private String authenticationCookie;

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilter(){

        FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AuthenticationFilter(authenticationCookie));
        registrationBean.addUrlPatterns("/auth/*");

        return registrationBean;
    }
}
