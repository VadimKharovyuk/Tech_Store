package com.example.webservice.config;

import com.example.webservice.repository.UserFeignClient;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserFeignClient userFeignClient;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        try {
            ResponseEntity<String> response = userFeignClient.login(username, password);

            if (response.getStatusCode().is2xxSuccessful()) {
                UserDetails userDetails = User.withUsername(username).password(password).roles("USER").build();
                return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
            } else {
                throw new BadCredentialsException("Invalid credentials");
            }
        } catch (FeignException e) {
            throw new BadCredentialsException("Authentication failed", e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
