package com.farmdora.farmdoraactivity.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        // Actuator 헬스 체크 경로는 필터를 타지 않도록 처리
        if (path.equals("/actuator/health")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = jwtUtil.extractTokenFromCookie(request);
        log.info("token: {}", token);

        // 토큰 유효성 검사
        if (token != null && jwtUtil.validateToken(token)) {
            String username = jwtUtil.getUsername(token);
            log.debug("username : {}", username);

            // Spring Security Context에 사용자 정보와 권한 추가
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    username, null, jwtUtil.getAuthorities(token));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            // 토큰이 유효하지 않거나 없는 경우
            log.debug("Invalid or missing token");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }
}