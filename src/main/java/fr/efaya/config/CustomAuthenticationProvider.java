package fr.efaya.config;

import fr.efaya.database.UsersRepository;
import fr.efaya.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Created by KTIFA FAMILY on 13/05/2016.
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String name = authentication.getName();
        final String password = authentication.getCredentials().toString();

        //On effectue ici les étapes d'authentification
        User user = usersRepository.findByUsername(name);
        if (user == null || !password.equals(user.getPassword())) {
            throw new BadCredentialsException("Username is unknown or password is incorrect");
        }
        // Pour finir on retourne un objet de type Authentication
        return new UsernamePasswordAuthenticationToken(name, password, Collections.singletonList(new SimpleGrantedAuthority("COMMON_USER")));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
