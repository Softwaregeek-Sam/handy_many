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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.http.HttpHeaders;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
       String authHeader = request.getHeader("Authorization");
       String token = null;

       if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
       }

       if(token != null && SecurityContextHolder.getContext().getAuthentication() == null){
            String phone = jwtService.extractPhone(token);



           if (phone != null && jwtService.isTokenValid(token)) {
               User user = userDetailsService.findUserByPhone(phone); // ⬅ real User entity from DB
               var userDetails = new CustomUserDetails(user); // ⬅ your full CustomUserDetails object
               var authToken = new UsernamePasswordAuthenticationToken(
                       userDetails,
                       null,
                       userDetails.getAuthorities()
               );
               authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
               SecurityContextHolder.getContext().setAuthentication(authToken);
           }

       }
       filterChain.doFilter(request, response);

    }
}
