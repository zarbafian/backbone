package backbone.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AuthenticationFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);

    private String authenticationHeader;

    public AuthenticationFilter(String authenticationHeader) {
        this.authenticationHeader = authenticationHeader;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

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

        if(token.equals("ABCD")) {
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
}
