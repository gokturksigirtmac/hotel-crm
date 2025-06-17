package com.example.visitor_crm_be.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
    @Autowired
    private JwtHelper jwtHelper;


    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        //Authorization

        String requestHeader = request.getHeader("Authorization");
        //Bearer 2352345235sdfrsfgsdfsdf
        logger.info(" Header :  {}", request);
        String username = null;
        String token = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer")) {

            //looking good
            token = requestHeader.substring(7);

            try {
                username = this.jwtHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                logger.info("Illegal Argument while fetching the username !!");
                e.printStackTrace();
            } catch (ExpiredJwtException e) {
                logger.info("Given jwt token is expired !!");
                e.printStackTrace();
            } catch (MalformedJwtException e) {
                logger.info("Some changed has done in token !! Invalid Token");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            logger.info("Invalid Header Value !! ");
        }
        //
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);

            if (validateToken) {
                System.out.println("validate token");
                //set the authentication
                Claims claims = this.jwtHelper.getAllClaimsFromToken(token);
                String role = ((List<?>) claims.get("role")).get(0).toString(); // ROLE_ADMIN
                List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
                System.out.println("authorities : " + authorities);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                System.out.println("authentication : " + authentication.getName());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("Current authentication in context: " + SecurityContextHolder.getContext().getAuthentication());
                System.out.println("Authentication Success");

            } else {
                logger.info("Validation fails !!");
            }

        }
        filterChain.doFilter(request, response); //doubt hain
    }
}