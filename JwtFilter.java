package com.abhi.SpringSecEx.Filter;

import com.abhi.SpringSecEx.Services.JWTService;
import com.abhi.SpringSecEx.Services.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JWTService jwt_serv;

    @Autowired
    ApplicationContext context;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //we get Bearer sjkadfjadfsdkf........asfadgfh -> this is the token now we want tonly token part to validate not beare so we need to code fdor that

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String  username = null;

        if(authHeader != null && authHeader.startsWith("Bearer ")){ // check if it is valid token
            token=authHeader.substring(7);
            username = jwt_serv.extractUserName(token);
        }

        if (username!= null && SecurityContextHolder.getContext().getAuthentication() == null){

            UserDetails userDet = context.getBean(MyUserDetailsService.class).loadUserByUsername(username);
            if (jwt_serv.validateToken(token,userDet)){
                UsernamePasswordAuthenticationToken  token1 =
                        new UsernamePasswordAuthenticationToken(userDet,null,userDet.getAuthorities());
                token1.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // now token1 is ready now set in context
                SecurityContextHolder.getContext().setAuthentication(token1); // ading token in the chain
            }
        }
        filterChain.doFilter(request,response);
    }
}
