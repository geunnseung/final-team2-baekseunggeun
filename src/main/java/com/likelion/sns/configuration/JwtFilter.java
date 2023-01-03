package com.likelion.sns.configuration;

import com.likelion.sns.service.UserService;
import com.likelion.sns.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authorization: {}", authorization);

        if(authorization == null || !authorization.startsWith("Bearer ")) {
            log.error("토큰이 없거나 잘못되었습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.split(" ")[1];
        try{
            token = authorization.split(" ")[1];
        } catch (Exception e) {
            log.error(("토큰 발급 실패"));
            filterChain.doFilter(request, response);
            return;
        }

        // 만료 체크
        if(JwtUtil.isExpired(token, secretKey)) {
            log.error(("토큰이 만료되었습니다."));
            filterChain.doFilter(request, response);
            return;
        }

        // Token 꺼내기
        String userName = JwtUtil.getUserName(token, secretKey);
        log.info("UserName : {} ", userName);

        // 권한 부여
        UsernamePasswordAuthenticationToken authenticToken = new UsernamePasswordAuthenticationToken(
                userName, null, List.of(new SimpleGrantedAuthority("USER")));

        authenticToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticToken);
        filterChain.doFilter(request, response);

    }
}
