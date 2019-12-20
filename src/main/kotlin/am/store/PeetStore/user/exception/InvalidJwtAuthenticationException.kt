package am.store.PeetStore.user.exception

import org.springframework.security.access.AuthorizationServiceException

class InvalidJwtAuthenticationException : AuthorizationServiceException {
    constructor(message: String) : super(message)
}