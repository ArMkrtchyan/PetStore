package am.petstore.PetStore.user.jwt;

import org.springframework.security.access.AuthorizationServiceException;

public class InvalidJwtAuthenticationException extends AuthorizationServiceException {
    /**
     *
     */
    private static final long serialVersionUID = -761503632186596342L;

    public InvalidJwtAuthenticationException(String e) {
        super(e);
    }
}
