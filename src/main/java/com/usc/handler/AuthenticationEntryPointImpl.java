package com.usc.handler;

import com.usc.security.SecurityUtils;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
          throws IOException, ServletException {
    SecurityUtils.sendResponse(response, HttpServletResponse.SC_FORBIDDEN,
            "Authentication failed", exception);
  }
}
