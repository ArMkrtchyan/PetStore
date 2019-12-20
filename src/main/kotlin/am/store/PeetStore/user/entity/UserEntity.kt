package am.store.PeetStore.user.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Entity
@Table(name = "users")
class UserEntity : UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column
    var email: String? = null
    @Column
    var mPassword: String? = null
    @Column
    var fullname: String? = null
    @Column
    var active: Boolean = false
    @Column
    var firebase_id: String? = null
    @Column
    var phone: String? = null

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    var createdAt: Date? = null
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    var updatedAt: Date? = null
    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = [JoinColumn(name = "user_id")])
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    var roles: Set<Role?>? = null
    @OneToMany
    @JoinColumn(name = "user_id")
    var devices: Set<DeviceEntity>? = null

    constructor()
    constructor(password: @Size(min = 6) String?, email: String?, fullname: String?, active: Boolean?, firebase_id: String?, phone: @Pattern(regexp = "[+0-9]*") @NotNull String?, createdAt: Date?, updatedAt: Date?, roles: Set<Role?>?, devices: Set<DeviceEntity>?) {
        this.mPassword = password!!
        this.email = email!!
        this.fullname = fullname!!
        this.active = active!!
        this.firebase_id = firebase_id!!
        this.phone = phone!!
        this.createdAt = createdAt!!
        this.updatedAt = updatedAt!!
        this.roles = roles!!
        this.devices = devices!!
    }


    override fun getAuthorities(): Collection<GrantedAuthority?> = roles!!

    override fun isEnabled(): Boolean = active

    override fun getUsername(): String = email!!

    override fun isCredentialsNonExpired(): Boolean = true

    override fun getPassword(): String = mPassword!!

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true
}