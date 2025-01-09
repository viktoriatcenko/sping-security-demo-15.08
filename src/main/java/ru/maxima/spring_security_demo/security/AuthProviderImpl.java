package ru.maxima.spring_security_demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthProviderImpl implements AuthenticationProvider {

    private final PersonDetailsService personDetailsService;

    @Autowired
    public AuthProviderImpl(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String name = authentication.getName(); // получаем имя пользователя

        UserDetails personDetails = personDetailsService.loadUserByUsername(name);

        String password = authentication.getCredentials().toString(); // получаем пароль пользователя

        if (!personDetails.getPassword().equals(password)) {
            throw new BadCredentialsException("AuthProviderImpl said, that password is wrong");
        }

        return new UsernamePasswordAuthenticationToken(personDetails, password, personDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
