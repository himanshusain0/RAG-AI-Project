package com.aiplatform.security;


import com.aiplatform.model.User;
import com.aiplatform.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository  userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            System.out.println("❌ No Bearer token found");
            filterChain.doFilter(request,response);
            return;
        }
        String authToken = authHeader.substring(7);
        System.out.println("Token: " + authToken.substring(0, 20) + "...");

        try {
            String loginWay = jwtUtil.extractLoginWay((authToken));
            if(!"Admin".equals(loginWay)){
                System.out.println("❌ LoginWay is not Admin");
                filterChain.doFilter(request,response);
                return;
            }
            String email  = jwtUtil.extractEmail((authToken));
            Long userId = jwtUtil.extractUserId((authToken));
            System.out.println("UserId from token: " + userId);
            User user = userRepository.findById(userId).orElse(null);
            System.out.println("User from DB: " + (user != null ? user.getEmail() : "NULL"));
            if(user == null
                    || !user.getIsActive()
                    || !user.getIsLoggedIn()
                    || !authToken.equals(user.getLoggedInToken())){
                System.out.println("❌ User not found in DB");
                filterChain.doFilter(request,response);
                return;
            }
            if(!jwtUtil.isTokenValid(authToken, email)){
                filterChain.doFilter(request,response);
                return;
            }
            String role = user.getUserType() ==1?"ROLE_ADMIN":"ROLE_USER";

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    List.of(new SimpleGrantedAuthority(role))
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

        }catch (Exception e){
            System.out.println("❌ Exception: " + e.getMessage());
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        filterChain.doFilter(request,response);
    }
}
