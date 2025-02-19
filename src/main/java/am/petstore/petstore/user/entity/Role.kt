package am.petstore.petstore.user.entity

import org.springframework.security.core.GrantedAuthority

enum class Role : GrantedAuthority {
    USER, ADMIN, EDITOR;

    override fun getAuthority(): String {
        return name
    }
}