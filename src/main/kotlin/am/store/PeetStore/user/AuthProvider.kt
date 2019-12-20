package am.store.PeetStore.user


import am.store.PeetStore.user.entity.UserEntity
import am.store.PeetStore.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component


@Component
class AuthProvider @Autowired constructor(private var bCryptPasswordEncoder: BCryptPasswordEncoder?, private var userService: UserService?) : AuthenticationProvider {

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication? {
        val phone = authentication.name
        val password = authentication.credentials as String
        val userModel: UserEntity = userService?.loadUserByUsername(phone) as UserEntity
        return if (userModel != null && userModel.phone.equals(phone)) {
            if (!bCryptPasswordEncoder!!.matches(password, userModel.password)) {
                throw BadCredentialsException("Wrong password")
            }
            val authorities: Collection<GrantedAuthority?> = userModel.authorities
            UsernamePasswordAuthenticationToken(userModel, password, authorities)
        } else throw BadCredentialsException("User" +
                " not found")
    }

    override fun supports(aClass: Class<*>?): Boolean {
        return false
    }
}