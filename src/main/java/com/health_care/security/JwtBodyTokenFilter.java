package com.health_care.security;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class JwtBodyTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private CustomUserDetailsService userDetailsService;



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {

	    String requestURI = request.getRequestURI();

	    // Bypass token validation for login endpoint
	    if (requestURI.equals("/auth/login")) {
	        filterChain.doFilter(request, response); // Proceed without token check
	        return;
	    }

	    if ("POST".equalsIgnoreCase(request.getMethod()) &&
	        request.getContentType() != null &&
	        request.getContentType().contains("application/json")) {

	        StringBuilder stringBuilder = new StringBuilder();
	        BufferedReader reader = request.getReader();
	        String line;
	        while ((line = reader.readLine()) != null) {
	            stringBuilder.append(line);
	        }

	        String body = stringBuilder.toString();

	        try {
	            ObjectMapper mapper = new ObjectMapper();
	            JsonNode jsonNode = mapper.readTree(body);
	            String token = jsonNode.has("token") ? jsonNode.get("token").asText() : null;

	            if (token != null && jwtTokenUtil.validateToken(token)) {
	                String username = jwtTokenUtil.getUsernameFromToken(token);
	                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
	                UsernamePasswordAuthenticationToken authentication =
	                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	                SecurityContextHolder.getContext().setAuthentication(authentication);
	            } else {
	                response.setStatus(HttpServletResponse.SC_OK);
	                response.setContentType("application/json");
	                response.getWriter().write("{\"message\": \"Kindly Re-Login\", \"errorCode\": \"2\"}");
	                return;
	            }

	            HttpServletRequest wrappedRequest = new CustomHttpServletRequestWrapper(request, body);
	            filterChain.doFilter(wrappedRequest, response);

	        } catch (Exception e) {
	            response.setStatus(HttpServletResponse.SC_OK);
	            response.setContentType("application/json");
	            response.getWriter().write("{\"message\": \"Invalid token or request body\"}");
	        }

	    } else {
	        filterChain.doFilter(request, response); // For non-JSON or non-POST requests
	    }
	}

}
