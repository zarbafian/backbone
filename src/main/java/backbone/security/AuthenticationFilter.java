package backbone.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class AuthenticationFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);

    private String authenticationCookie;

    private SessionManager sessionManager;

    public AuthenticationFilter(String authenticationCookie, SessionManager sessionManager) {
        this.authenticationCookie = authenticationCookie;
        this.sessionManager = sessionManager;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Cookie[] cookies = request.getCookies();

        Optional<Cookie> optionalCookie = Arrays.stream(cookies).filter(cookie -> this.authenticationCookie.equals(cookie.getName())).findFirst();

        LOGGER.debug("🔒 Attempting authentication");

        if(optionalCookie.isPresent()) {

            // cookie is present
            Cookie cookie = optionalCookie.get();
            String token = cookie.getValue();

            if(token == null || token.isBlank()) {
                // empty authentication header
                LOGGER.debug("🚫 Authentication failure: empty authentication cookie");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            if(sessionManager.isValid(token)) {
                LOGGER.debug("🔑 Authentication success");
                filterChain.doFilter(servletRequest, servletResponse);
            }
            else {
                // invalid authentication header
                LOGGER.debug("🚫 Authentication failure: invalid authentication header");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        else {
            // no authentication header
            LOGGER.error("🚫 Authentication failure: no authentication cookie");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

    }
}
