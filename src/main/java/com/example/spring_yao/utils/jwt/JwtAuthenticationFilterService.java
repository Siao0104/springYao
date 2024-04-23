package com.example.spring_yao.utils.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Component
public class JwtAuthenticationFilterService extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 取得 request header 的值
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null) {
            String accessToken = bearerToken.replace("Bearer ", "");

            // 解析 token
            Map<String, Object> tokenPayload = tokenService.parseToken(accessToken);
            String account = (String) tokenPayload.get("account");

            // 查詢使用者
            UserBasicDetails userDetails = userDetailsService.loadUserByUsername(account);
            //存在DB的token需與當前的比對，非當前的代表已登出
            if(!accessToken.equals(userDetails.getToken())){
                sendErrorResponse(response,HttpServletResponse.SC_FORBIDDEN,"該Token已過期");
                return;
            }

            // 將使用者身份與權限傳遞給 Spring Security
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    userDetails.getPassword(),
                    userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // 將 request 送往 controller 或下一個 filter
        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response,int status,String errMsg) throws IOException{
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(status);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), Collections.singletonMap("msg",errMsg));
    }
}
