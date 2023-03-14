package io.academy.backend.academy.security.jwt;

import io.academy.backend.academy.security.service.MyUserDetailService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JWTFilter extends OncePerRequestFilter {
    final MyUserDetailService userDetailService;
    final JWTUtils jwtUtils;

    @Value("${security.auth.secret}")
    private String secret;

    public JWTFilter(MyUserDetailService userDetailService, JWTUtils jwtUtils) {
        this.userDetailService = userDetailService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if (authorization != null &&
                !authorization.isBlank() &&
                authorization.startsWith("Bearer")) {

            String jwt = authorization.substring(7);

            if (jwt == null || jwt.isBlank() || jwt.split("\\.").length != 3) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token in Bearer");
            } else {
                try {
                    String username = jwtUtils.validateToken(jwt, secret);
                    UserDetails userDetails = userDetailService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                            username,
                            userDetails.getPassword(),
                            userDetails.getAuthorities());


                    SecurityContextHolder.getContext().setAuthentication(token);

                } catch (RuntimeException exception) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
                }
            }


        }
        filterChain.doFilter(request, response);
    }
}
