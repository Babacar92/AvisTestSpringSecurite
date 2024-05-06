package com.example.avis.demo.securite;

import com.example.avis.demo.services.UtilisateurService;
import com.example.avis.demo.services.UtilisateurServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
public class JwtFilter extends OncePerRequestFilter {

    private UtilisateurServiceImpl utilisateurServiceImpl;
    private JwtService jwtService;

    public JwtFilter(UtilisateurServiceImpl utilisateurService ,JwtService jwtService){
        this.utilisateurServiceImpl = utilisateurService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String token = null;
    String username = null;
    boolean isTokenExpired = true;

    final String authorization = request.getHeader("Authorization");

    if (
            authorization !=null && authorization.startsWith("Bearer ")
    ) {
        token = authorization.substring(7);
        isTokenExpired = jwtService.isTokenExpired(token);
        username = jwtService.extractUsername(token);
    }
    if (!isTokenExpired && username != null && SecurityContextHolder.getContext().getAuthentication() == null){
        UserDetails userDetails = utilisateurServiceImpl.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    filterChain.doFilter(request,response);
    }
}
