package backbone.config;

import backbone.security.AuthenticationFilter;
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

    @Autowired
    private SessionManager sessionManager;

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilter(){

        FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AuthenticationFilter(authenticationCookie, sessionManager));
        registrationBean.addUrlPatterns("/api/*");

        return registrationBean;
    }
}
