package ru.leonvsg.education.timesheet.controllers.filters;

import org.apache.log4j.Logger;
import ru.leonvsg.education.timesheet.services.entity.UserService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(AuthFilter.class);
    private final UserService userService = new UserService();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest)request).getSession();
        LOGGER.info("Enter to " + ((HttpServletRequest) request).getRequestURI());
        Object token = session.getAttribute("token");
        if (token != null && userService.authenticate(token.toString()) != null) {
            LOGGER.info("Token is valid. Access confirmed.");
            chain.doFilter(request, response);
        } else {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/auth?errorMessage=authenticateReq");
            LOGGER.warn("Invalid token. Redirect to: " + httpServletRequest.getContextPath() + "/auth?errorMessage=authenticateReq");
        }
    }

    @Override
    public void destroy() {

    }
}
