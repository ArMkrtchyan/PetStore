package am.petstore.PetStore.user.service;

import am.petstore.PetStore.user.entity.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class AuthProvider implements AuthenticationProvider {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserService userService;

    @Autowired
    public AuthProvider(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String phone = authentication.getName();
        String password = (String) authentication.getCredentials();
        UserModel userModel = (UserModel) userService.loadUserByUsername(phone);
        if (userModel != null && userModel.getPhone().equals(phone)) {
            if (!bCryptPasswordEncoder.matches(password, userModel.getPassword())) {
                throw new BadCredentialsException("Wrong password");
            }
            Collection<? extends GrantedAuthority> authorities = userModel.getAuthorities();
            return new UsernamePasswordAuthenticationToken(userModel, password, authorities);
        } else
            throw new BadCredentialsException("User" +
                    " not found");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
