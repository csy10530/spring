package com.usc.handler;

import com.usc.security.SecurityUtils;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception)
          throws IOException, ServletException {
    SecurityUtils.sendResponse(response, HttpServletResponse.SC_UNAUTHORIZED,
            "Not authorized resources", exception);
  }
}
