package dev.example.api.exceptionhandler;

import dev.example.api.common.error.ErrorCode;
import dev.example.api.common.exception.ApiException;
import org.apache.coyote.Response;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(ErrorCode.INVALID_TOKEN.getHttpStatusCode());
        response.getWriter().write(new ApiException(ErrorCode.INVALID_TOKEN).getErrorDescription());
    }
}
