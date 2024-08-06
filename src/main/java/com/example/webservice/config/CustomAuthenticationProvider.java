package com.example.webservice.config;
import com.example.webservice.dto.UserDTO;
import com.example.webservice.repository.UserFeignClient;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserFeignClient userFeignClient;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        try {
            ResponseEntity<UserDTO> response = userFeignClient.login(username, password);

            if (response.getStatusCode().is2xxSuccessful()) {
                UserDTO userDTO = response.getBody();
                if (userDTO != null) {
                    // Преобразуйте роль в authority
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + userDTO.getRole());
                    List<SimpleGrantedAuthority> authorities = Collections.singletonList(authority);

                    if (userDTO.getPassword() == null) {
                        throw new BadCredentialsException("Password is missing");
                    }

                    UserDetails userDetails = org.springframework.security.core.userdetails.User
                            .withUsername(userDTO.getUsername())
                            .password(userDTO.getPassword()) // Убедитесь, что пароль не null
                            .authorities(authorities)

                            .build();

                    return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
                } else {
                    throw new BadCredentialsException("User details are missing");
                }
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
