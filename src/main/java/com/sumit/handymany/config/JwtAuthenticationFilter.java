package com.sumit.handymany.config;

import com.sumit.handymany.auth.service.CustomUserDetails;
import com.sumit.handymany.auth.service.JwtService;
import com.sumit.handymany.user.model.User;
import com.sumit.handymany.user.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

         if(!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
             filterChain.doFilter(request, response);
             return;
         }

         String token = authorizationHeader.substring(7);
         if(jwtService.isTokenValid(token)) {
             String phone = jwtService.extractPhone(token);
             Long userId = jwtService.extractUserId(token);
             List<String> roles = jwtService.extractRoles(token);

             var authorities = roles.stream()
                     .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                     .toList();

             var authToken = new UsernamePasswordAuthenticationToken(userId, null, authorities);
             authToken.setDetails(userId);
             SecurityContextHolder.getContext().setAuthentication(authToken);


         }
         filterChain.doFilter(request, response);

    }
}
