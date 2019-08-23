package backbone.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class CookieAuthenticationFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CookieAuthenticationFilter.class);

    private String authenticationCookie;

    private SessionManager sessionManager;

    private String allowedOrigins;

    public CookieAuthenticationFilter(String authenticationCookie, SessionManager sessionManager, String allowedOrigins) {
        this.authenticationCookie = authenticationCookie;
        this.sessionManager = sessionManager;
        this.allowedOrigins = allowedOrigins;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // Allow OPTIONS
        if("OPTIONS".equals(request.getMethod())) {
            LOGGER.debug("✈ Allowing OPTIONS");
            response.setStatus(HttpServletResponse.SC_OK);
            response.setHeader("Access-Control-Allow-Origin", allowedOrigins);
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            return;
        }

        Cookie[] cookies = request.getCookies();

        if(cookies == null) {
            // no authentication header
            LOGGER.error("🚫 Authentication failure: no authentication cookie");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

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

            if(sessionManager.getSession(token) != null) {
                LOGGER.debug("🔑 Authentication success");
                filterChain.doFilter(servletRequest, servletResponse);
                return;
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
