package com.example.demo.security;

import com.example.demo.entity.User;
import com.example.demo.services.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;


public class JWTAuthenticationFilter extends OncePerRequestFilter {
    public static final Logger LOG = LoggerFactory.getLogger(JWTTokenProvider.class);
    @Autowired
    private JWTTokenProvider jwtTokenProvider;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
         try{
             String jwt = getJWTFromRequest(request);
             if(StringUtils.hasText(jwt)&&jwtTokenProvider.validateToken(jwt)){
                 Long id = jwtTokenProvider.getUserIdFromToken(jwt);
                 User userDetails = customUserDetailsService.loadUserById(id);

                 UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                         userDetails,null, Collections.emptyList()
                 );

                 authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                 SecurityContextHolder.getContext().setAuthentication(authentication);
             }
         }catch (Exception e){
             LOG.error("Could not set user authentication");
         }
         filterChain.doFilter(request,response);
    }
    private String getJWTFromRequest(HttpServletRequest httpServletRequest){
        String bearToken = httpServletRequest.getHeader(SecurityConstants.HEADER_STRING);
        if(StringUtils.hasText(bearToken)&&bearToken.startsWith(SecurityConstants.TOKEN_PREFIX)){
            return bearToken.split(" ")[1];
        }
        return null;
    }
}
