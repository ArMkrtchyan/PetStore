package am.petstore.PetStore.user.entity

import am.petstore.PetStore.order.entity.OrderEntity
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.io.Serializable
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener::class)
@JsonIgnoreProperties("hibernateLazyInitializer", "handler")
class UserModel : Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    @Column(name = "password")
    private var password: @Size(min = 6) String? = null
    @Column(name = "email", unique = true)
    var email: String? = null
    @Column(name = "fullname")
    var fullname: String? = null
    @Column(name = "username")
    @JsonIgnore
    private val username: String? = null
    @Column(name = "birthday")
    var birthday: String? = null
    @Column(name = "photo")
    private val photo: String? = null
    @Column(name = "gender")
    var gender: String? = null
    @Column(name = "active")
    @JsonIgnore
    private var active: Boolean? = null
    @Column(name = "firebase_id")
    var firebase_id: String? = null
    @Column(name = "phone", unique = true)
    var phone: @Pattern(regexp = "[+0-9]*") @NotNull String? = null
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
    var roles: Set<Role>? = null
    @OneToMany
    @JoinColumn(name = "user_id")
    var devices: MutableSet<Device?>? = null
    @OneToMany
    @JoinColumn(name = "user_id")
    var orders: Set<OrderEntity>? = null

    constructor() {}
    constructor(password: @Size(min = 6) String?, email: String?, fullname: String?, birthday: String?, gender: String?, active: Boolean?, firebase_id: String?, phone: @Pattern(regexp = "[+0-9]*") @NotNull String?, createdAt: Date?, updatedAt: Date?, roles: Set<Role>?, devices: MutableSet<Device?>?, orders: Set<OrderEntity>?) {
        this.password = password
        this.email = email
        this.fullname = fullname
        this.birthday = birthday
        this.gender = gender
        this.active = active
        this.firebase_id = firebase_id
        this.phone = phone
        this.createdAt = createdAt
        this.updatedAt = updatedAt
        this.roles = roles
        this.devices = devices
        this.orders = orders
    }

    @JsonIgnore
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return roles!!
    }

    override fun getPassword(): String {
        return password!!
    }

    @JsonIgnore
    override fun getUsername(): String {
        return phone!!
    }

    @JsonIgnore
    override fun isAccountNonExpired(): Boolean {
        return true
    }

    @JsonIgnore
    override fun isAccountNonLocked(): Boolean {
        return true
    }

    @JsonIgnore
    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    @JsonIgnore
    override fun isEnabled(): Boolean {
        return active!!
    }

    fun setPassword(password: String?) {
        this.password = password
    }

    override fun toString(): String {
        return "UserModel{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", fullname='" + fullname + '\'' +
                ", birthday='" + birthday + '\'' +
                ", gender='" + gender + '\'' +
                ", firebase_id='" + firebase_id + '\'' +
                ", phone='" + phone + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", roles=" + roles +
                ", devices=" + devices +
                ", orders=" + orders +
                '}'
    }

}