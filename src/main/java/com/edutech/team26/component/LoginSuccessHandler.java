package com.edutech.team26.component;

import com.edutech.team26.biz.MemberService;
import com.edutech.team26.biz.MemberServiceImpl;
import com.edutech.team26.dto.MemberSecurityDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import java.io.IOException;

@Log4j2
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        MemberSecurityDTO member = (MemberSecurityDTO) authentication.getPrincipal();

        response.sendRedirect("/loginPro");

        /*RequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if(savedRequest != null) {
            String uri = savedRequest.getRedirectUrl();
            requestCache.removeRequest(request, response);
            log.info(">>>>>>>" + uri);
            //response.sendRedirect(uri);
        }
        //response.sendRedirect("/");*/

    }

}