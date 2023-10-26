package com.usc.handler;

import com.usc.security.SecurityUtils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationSuccessHandlerImpl extends SimpleUrlAuthenticationSuccessHandler {
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
          throws IOException, ServletException {
    boolean isAdmin = false;
    for (GrantedAuthority profile : authentication.getAuthorities()) {
      if (profile.getAuthority().equals("ROLE_ADMIN")) {
        isAdmin = true;
      }
    }
    SecurityUtils.sendResponse(response, HttpServletResponse.SC_OK, String.valueOf(isAdmin), null);
  }
}
