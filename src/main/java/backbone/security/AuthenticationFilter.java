package backbone.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AuthenticationFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);

    private String authenticationHeader;

    private SessionManager sessionManager;

    private String allowedOrigins;

    public AuthenticationFilter(String authenticationHeader, SessionManager sessionManager, String allowedOrigins) {
        this.authenticationHeader = authenticationHeader;
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
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, sid");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            return;
        }

        Map<String, List<String>> headersMap = Collections
                .list(request.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        h -> Collections.list(request.getHeaders(h))
                ));

        LOGGER.debug("🔒 Attempting authentication");

        if(!headersMap.containsKey(this.authenticationHeader)) {
            // no authentication header
            LOGGER.debug("🚫 Authentication failure: no authentication header");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        List<String> authHeaders = headersMap.get(this.authenticationHeader);

        if(authHeaders.isEmpty()) {
            // no authentication header
            LOGGER.debug("🚫 Authentication failure: empty authentication header");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        if(authHeaders.size() != 1) {
            // too many authentication headers
            LOGGER.debug("🚫 Authentication failure: too many authentication headers");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String token = authHeaders.get(0);

        if(token == null || token.isBlank()) {
            // no authentication header
            LOGGER.debug("🚫 Authentication failure: empty authentication header");
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
}
